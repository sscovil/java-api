package com.shaunscovil.api.exception;

import javax.ws.rs.core.Response;

public class APIException extends RuntimeException {

    private Response.Status status;

    public APIException(String message, Response.Status status) {
        super(message);
        this.status = status;
    }

    public Response.Status getStatus() {
        return status;
    }

}
