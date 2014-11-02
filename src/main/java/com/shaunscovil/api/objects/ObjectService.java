package com.shaunscovil.api.objects;

import com.shaunscovil.api.common.ResourceUrl;
import com.shaunscovil.api.data.DAO;
import com.shaunscovil.api.data.mongodb.MongoDAO;

import java.util.List;
import java.util.Map;

public class ObjectService {

    protected static final String COLLECTION_NAME = "objects";

    protected DAO<Map> dao;

    public ObjectService() {
        this.dao = new MongoDAO<>(COLLECTION_NAME);
    }

    public ObjectService(DAO<Map> dao) {
        this.dao = dao;
    }

    public Map<String, Object> create(Map model) {
        return dao.create(model);
    }

    public Map<String, Object> update(String uid, Map model) {
        return dao.update(uid, model);
    }

    public Map<String, Object> read(String uid) {
        return dao.read(uid);
    }

    public List<ResourceUrl> readResourceUrls(String resourcePath) {
        return dao.readResourceUrls(resourcePath);
    }

    public void delete(String uid) {
        dao.delete(uid);
    }

}
