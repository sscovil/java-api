package com.shaunscovil.api.data;

import com.mongodb.*;
import com.shaunscovil.api.common.ResourceUrl;
import com.shaunscovil.api.exception.ApiException;
import org.bson.types.ObjectId;

import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MongoDAO implements DAO {

    protected DBCollection collection;

    public MongoDAO(String collectionName) {
        DB database = MongoDB.getDatabase();
        this.collection = database.getCollection(collectionName);
    }

    public Map<String, Object> create(Map<String, Object> model) {
        DBObject entity = new BasicDBObject(model);
        collection.insert(entity);

        return mapModel(entity);
    }

    public Map<String, Object> update(Map<String, Object> model) {
        String uid = model.get("uid").toString();
        ObjectId objectId = mapObjectId(uid);
        DBObject query = collection.findOne(objectId);
        handleNotFound(query);
        DBObject entity = new BasicDBObject("_id", objectId);
        entity.putAll(model);
        entity.removeField("uid");
        collection.update(query, entity);

        return mapModel(entity);
    }

    public Map<String, Object> read(String uid) {
        ObjectId objectId = mapObjectId(uid);
        DBObject entity = collection.findOne(objectId);
        handleNotFound(entity);

        return mapModel(entity);
    }

    public List<ResourceUrl> readResourceUrls(String resourcePath) {
        DBObject query = new BasicDBObject();
        DBObject fields = new BasicDBObject("_id", 1);
        DBCursor cursor = collection.find(query, fields);
        handleNotFound(cursor);

        List<ResourceUrl> response = new ArrayList<>();

        while (cursor.hasNext()) {
            DBObject entity = cursor.next();
            String resourceId = entity.get("_id").toString();
            ResourceUrl resourceUrl = new ResourceUrl(resourceId, resourcePath);
            response.add(resourceUrl);
        }

        return response;
    }

    public void delete(String uid) {
        ObjectId objectId = mapObjectId(uid);
        DBObject query = new BasicDBObject("_id", objectId);
        collection.remove(query);
    }

    protected void handleNotFound(DBObject entity) {
        if (entity == null)
            throw new ApiException("Record not found", Response.Status.NOT_FOUND);
    }

    protected void handleNotFound(DBCursor cursor) {
        if (!cursor.hasNext())
            throw new ApiException("No records found", Response.Status.NOT_FOUND);
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> mapModel(DBObject entity) {
        String uid = entity.get("_id").toString();
        Map<String, Object> response = new HashMap<>();
        response.put("uid", uid);
        response.putAll(entity.toMap());
        response.remove("_id");

        return response;
    }

    protected ObjectId mapObjectId(String uid) {
        try {
            return new ObjectId(uid);
        }
        catch (IllegalArgumentException e) {
            throw new ApiException(String.format("'%s' is not a valid UID", uid), Response.Status.BAD_REQUEST);
        }
    }

}
