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

public class MongoDAO {

    protected DBCollection collection;

    public MongoDAO(String collectionName) {
        DB database = MongoDB.getDatabase();
        this.collection = database.getCollection(collectionName);
    }

    public Map<String, Object> create(Map<String, Object> model) {
        DBObject entity = new BasicDBObject(model);
        collection.insert(entity);

        return buildResponse(entity);
    }

    public Map<String, Object> update(Map<String, Object> model) {
        String uid = model.get("uid").toString();
        ObjectId objectId = stringToObjectId(uid);
        DBObject query = collection.findOne(objectId);
        checkNotFound(query);
        DBObject entity = new BasicDBObject("_id", objectId);
        model.remove("uid");
        entity.putAll(model);
        collection.update(query, entity);

        return buildResponse(entity);
    }

    public Map<String, Object> read(String uid) {
        ObjectId objectId = stringToObjectId(uid);
        DBObject entity = collection.findOne(objectId);
        checkNotFound(entity);

        return buildResponse(entity);
    }

    public List<ResourceUrl> readResourceUrls(String resourcePath) {
        DBObject query = new BasicDBObject();
        DBObject fields = new BasicDBObject("_id", 1);
        DBCursor cursor = collection.find(query, fields);
        checkNotFound(cursor);

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
        ObjectId objectId = stringToObjectId(uid);
        DBObject query = new BasicDBObject("_id", objectId);
        collection.remove(query);
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> buildResponse(DBObject entity) {
        String uid = entity.get("_id").toString();
        Map<String, Object> response = new HashMap<>();
        response.put("uid", uid);
        response.putAll(entity.toMap());
        response.remove("_id");

        return response;
    }

    protected void checkNotFound(DBObject entity) {
        if (entity == null)
            throw new ApiException("Record not found", Response.Status.NOT_FOUND);
    }

    protected void checkNotFound(DBCursor cursor) {
        if (!cursor.hasNext())
            throw new ApiException("No records found", Response.Status.NOT_FOUND);
    }

    protected ObjectId stringToObjectId(String uid) {
        try {
            return new ObjectId(uid);
        }
        catch (IllegalArgumentException e) {
            throw new ApiException(String.format("'%s' is not a valid UID", uid), Response.Status.BAD_REQUEST);
        }
    }

}
