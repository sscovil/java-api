package com.shaunscovil.api.objects;

import com.shaunscovil.api.common.JsonBean;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("objects")
public class ObjectsResource {

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public JsonBean create(JsonBean body) {
        body.setId("1234567890");
        return body;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> read() {
        return Arrays.asList("1234567890", "1234567890");
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonBean readOne(@PathParam("id") String id) {
        JsonBean result = new JsonBean();
        result.setId("1234567890");
        return result;
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public JsonBean update(@PathParam("id") String id, JsonBean body) {
        body.setId(id);
        return body;
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") String id) {

    }

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

}
