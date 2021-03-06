package com.shaunscovil.api.exception;

import com.shaunscovil.api.resource.ResourceIOMapper;
import org.glassfish.grizzly.http.server.Request;

import javax.ws.rs.container.ResourceContext;

public class APIExceptionResponse {

    protected String verb;

    protected String url;

    protected String message;

    public APIExceptionResponse(ResourceContext resourceContext, String message) {
        Request request = resourceContext.getResource(Request.class);
        this.verb = request.getMethod().toString();
        this.url = request.getRequestURL().toString();
        this.message = message;
    }

    public String getVerb() {
        return verb;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }

    public String toJson() {
        return ResourceIOMapper.serializeJson(this);
    }

}
