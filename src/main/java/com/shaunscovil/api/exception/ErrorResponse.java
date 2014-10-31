package com.shaunscovil.api.exception;

import com.shaunscovil.api.common.JsonUtility;
import org.glassfish.grizzly.http.server.Request;

import javax.ws.rs.container.ResourceContext;

public class ErrorResponse {

    protected String verb;

    protected String url;

    protected String message;

    public ErrorResponse(ResourceContext resourceContext, String message) {
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
        return JsonUtility.serializeJson(this);
    }

    @Override
    public String toString() {
        return String.format("ErrorResponse{verb='%s', url='%s', message='%s'}", verb, url, message);
    }

}
