package com.shaunscovil.api.objects;

import com.shaunscovil.api.common.JsonUtility;
import com.shaunscovil.api.common.ResourceUrl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("objects")
public class ObjectResource {

    protected ObjectService service;

    public ObjectResource() {
        this.service = new ObjectService();
    }

    public ObjectResource(ObjectService service) {
        this.service = service;
    }

    @POST
    @Consumes({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public String create(String body) {
        Map<String, Object> model = JsonUtility.deserializeJson(body);
        Map<String, Object> response = service.create(model);

        return JsonUtility.serializeJson(response);
    }

    @PUT
    @Path("{uid}")
    @Consumes({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@PathParam("uid") String uid, String body) {
        Map<String, Object> model = JsonUtility.deserializeJson(body);
        Map<String, Object> response = service.update(uid, model);

        return JsonUtility.serializeJson(response);
    }

    @GET
    @Path("{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String read(@PathParam("uid") String uid) {
        Map<String, Object> response = service.read(uid);

        return JsonUtility.serializeJson(response);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String readResourceUrls() {
        String resourcePath = "objects";
        List<ResourceUrl> response = service.readResourceUrls(resourcePath);

        return JsonUtility.serializeJson(response);
    }

    @DELETE
    @Path("{uid}")
    public void delete(@PathParam("uid") String uid) {
        service.delete(uid);
    }

}
