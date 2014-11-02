package com.shaunscovil.api.data;

import com.shaunscovil.api.common.ResourceUrl;

import java.util.List;
import java.util.Map;

public interface DAO {

    Map<String, Object> create(Map<String, Object> model);
    Map<String, Object> update(Map<String, Object> model);
    Map<String, Object> read(String uid);
    List<ResourceUrl> readResourceUrls(String resourcePath);
    void delete(String uid);

}
