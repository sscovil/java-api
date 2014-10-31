package com.shaunscovil.api.objects;

import com.shaunscovil.api.common.ResourceUrl;
import com.shaunscovil.api.data.MongoDAO;

import java.util.List;
import java.util.Map;

public class ObjectService {

    protected static final String COLLECTION_NAME = "objects";

    protected MongoDAO dao;

    public ObjectService() {
        this.dao = new MongoDAO(COLLECTION_NAME);
    }

    public ObjectService(MongoDAO dao) {
        this.dao = dao;
    }

    public Map<String, Object> create(Map<String, Object> model) {
        model.remove("uid");
        return dao.create(model);
    }

    public Map<String, Object> update(String uid, Map<String, Object> model) {
        model.put("uid", uid);
        return dao.update(model);
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
