package com.shaunscovil.api.data.mongodb;

import com.mongodb.*;
import com.shaunscovil.api.exception.APIException;
import com.shaunscovil.api.resource.ResourceUrl;
import com.shaunscovil.api.data.DAO;
import org.bson.types.ObjectId;

import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MongoDAO<Type> implements DAO<Type> {

    protected DBCollection collection;

    protected MongoMapper<Type> mapper;

    public MongoDAO(String collectionName) {
        DB database = MongoDB.getDatabase();
        this.collection = database.getCollection(collectionName);
        this.mapper = new MongoMapper<>();
    }

    public MongoDAO(DBCollection collection, MongoMapper<Type> mapper) {
        this.collection = collection;
        this.mapper = mapper;
    }

    public Map<String, Object> create(Type model) {
        DBObject entity = mapper.mapEntity(model);
        entity.removeField("_id");
        collection.insert(entity);

        return mapper.mapResponse(entity);
    }

    public Map<String, Object> update(Object uid, Type model) {
        ObjectId objectId = mapper.mapObjectId(uid);
        DBObject query = collection.findOne(objectId);
        handleNotFound(query);
        DBObject entity = mapper.mapEntity(model);
        entity.put("_id", objectId);
        entity.removeField("uid");
        collection.update(query, entity);

        return mapper.mapResponse(entity);
    }

    public Map<String, Object> read(Object uid) {
        ObjectId objectId = mapper.mapObjectId(uid);
        DBObject entity = collection.findOne(objectId);
        handleNotFound(entity);

        return mapper.mapResponse(entity);
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

    public List<Map<String, Object>> search(Map<String, Object> queryDictionary) {
        DBObject query = BasicDBObjectBuilder.start(queryDictionary).get();
        DBCursor cursor = collection.find(query);

        List<Map<String, Object>> response = new ArrayList<>();

        while (cursor.hasNext()) {
            DBObject entity = cursor.next();
            response.add(mapper.mapResponse(entity));
        }

        return response;
    }

    public void delete(Object uid) {
        ObjectId objectId = mapper.mapObjectId(uid);
        DBObject query = new BasicDBObject("_id", objectId);
        collection.remove(query);
    }

    protected void handleNotFound(DBObject entity) {
        if (entity == null)
            throw new APIException("Record not found", Response.Status.NOT_FOUND);
    }

    protected void handleNotFound(DBCursor cursor) {
        if (!cursor.hasNext())
            throw new APIException("No records found", Response.Status.NOT_FOUND);
    }

}
