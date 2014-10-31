package com.shaunscovil.api.exception;

import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
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
        ErrorResponse response = new ErrorResponse(resourceContext, e.getMessage());
        return Response.status(e.getStatus())
                .entity(response.toJson())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
