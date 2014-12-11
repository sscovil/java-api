package com.shaunscovil.api.resource;

import com.shaunscovil.api.data.DAO;
import com.shaunscovil.api.data.mongodb.MongoDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;

@Consumes({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
@Produces(MediaType.APPLICATION_JSON)
public class DynamicResource {

    protected UriInfo uriInfo;

    protected String baseResourcePath;

    protected DAO<Map> dao;

    public DynamicResource(@Context UriInfo uriInfo) {
        this.uriInfo = uriInfo;
        this.baseResourcePath = uriInfo.getPathSegments().get(0).getPath();
        this.dao = new MongoDAO<>(baseResourcePath);
    }

    public DynamicResource(UriInfo uriInfo, String baseResourcePath, DAO<Map> dao) {
        this.uriInfo = uriInfo;
        this.baseResourcePath = baseResourcePath;
        this.dao = dao;
    }

    @POST
    public String create(String body) {
        Map<String, Object> model = ResourceIOMapper.deserializeJson(body);
        Map<String, Object> response = dao.create(model);

        return ResourceIOMapper.serializeJson(response);
    }

    @PUT
    @Path("{uid}")
    public String update(@PathParam("uid") String uid, String body) {
        Map<String, Object> model = ResourceIOMapper.deserializeJson(body);
        Map<String, Object> response = dao.update(uid, model);;

        return ResourceIOMapper.serializeJson(response);
    }

    @GET
    @Path("{uid}")
    public String read(@PathParam("uid") String uid) {
        Map<String, Object> response = dao.read(uid);;

        return ResourceIOMapper.serializeJson(response);
    }

    @GET
    public String readResourceUrls() {
        List<ResourceUrl> response = dao.readResourceUrls(baseResourcePath);

        return ResourceIOMapper.serializeJson(response);
    }

    @DELETE
    @Path("{uid}")
    public void delete(@PathParam("uid") String uid) {
        dao.delete(uid);
    }

}
