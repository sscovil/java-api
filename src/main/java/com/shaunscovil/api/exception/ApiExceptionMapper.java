package com.shaunscovil.api.exception;

import com.shaunscovil.api.Main;
import com.shaunscovil.api.Property;

import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {

    @Context
    protected ResourceContext resourceContext;

    @Override
    public Response toResponse(ApiException e) {
        Response.Status status    = e.getStatus();

        switch (status) {
            case UNAUTHORIZED:
                String header = String.format("Basic realm=\"%s\"", Main.PROPERTIES.getProperty(Property.BASE_URI));
                return Response.status(status)
                        .header(HttpHeaders.WWW_AUTHENTICATE, header)
                        .entity("<html><body><h1>401 Unauthorized</h1></body></html>")
                        .type(MediaType.TEXT_HTML)
                        .build();

            default:
                ApiExceptionEntity entity = new ApiExceptionEntity(resourceContext, e.getMessage());
                return Response.status(e.getStatus())
                        .entity(entity.toJson())
                        .type(MediaType.APPLICATION_JSON)
                        .build();
        }
    }

}
