package com.shaunscovil.api.data.mongodb;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.shaunscovil.api.exception.ApiException;
import org.bson.types.ObjectId;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class MongoMapper<Type> {

    protected static final ObjectMapper MAPPER = new ObjectMapper();

    protected interface BasicDBObjectMixin {
        @JsonAnySetter
        void put(String key, Object value);
    }

    protected DBObject mapEntity(Type model) {
        if (model instanceof Map)
            return new BasicDBObject((Map) model);

        MAPPER.addMixInAnnotations(BasicDBObject.class, BasicDBObjectMixin.class);
        return MAPPER.convertValue(model, BasicDBObject.class);
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> mapResponse(DBObject entity) {
        String uid = entity.get("_id").toString();
        Map<String, Object> response = new HashMap<>();
        response.put("uid", uid);
        response.putAll(entity.toMap());
        response.remove("_id");

        return response;
    }

    protected ObjectId mapObjectId(Object uid) {
        try {
            return new ObjectId(uid.toString());
        }
        catch (IllegalArgumentException e) {
            throw new ApiException(String.format("'%s' is not a valid UID", uid), Response.Status.BAD_REQUEST);
        }
    }

}
