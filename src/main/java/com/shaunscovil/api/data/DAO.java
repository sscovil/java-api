package com.shaunscovil.api.data;

import com.shaunscovil.api.common.ResourceUrl;

import java.util.List;
import java.util.Map;

public interface DAO<Type> {

    Map<String, Object> create(Type model);
    Map<String, Object> update(Object uid, Type model);
    Map<String, Object> read(Object uid);
    List<ResourceUrl> readResourceUrls(String resourcePath);
    List<Map<String, Object>> search(Map<String, Object> queryDictionary);
    void delete(Object uid);

}
