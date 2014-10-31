package com.shaunscovil.api.status;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("status")
public class StatusResource {

    public static final String STATUS_OK_MESSAGE = "Purring like a kitten!";

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String checkStatus() {
        return STATUS_OK_MESSAGE;
    }

}
