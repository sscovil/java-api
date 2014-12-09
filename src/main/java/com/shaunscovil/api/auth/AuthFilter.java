package com.shaunscovil.api.auth;

import com.shaunscovil.api.data.DAO;
import com.shaunscovil.api.data.mongodb.MongoDAO;
import com.shaunscovil.api.exception.ApiException;
import com.sun.xml.internal.messaging.saaj.util.Base64;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AuthFilter implements ContainerRequestFilter {

    protected static final String COLLECTION_NAME = "users";

    protected static final Set<String> PUBLIC_RESOURCES = new HashSet<>();

    public AuthFilter() {
        PUBLIC_RESOURCES.add("GET.status");
    }

    @Override
    public void filter(ContainerRequestContext containerRequest) throws ApiException {
        String method = containerRequest.getMethod();
        String path = containerRequest.getUriInfo().getPath(true);
        String resource = String.format("%s.%s", method, path);
        if (PUBLIC_RESOURCES.contains(resource))
            return;

        String basicAuthCredentials = containerRequest.getHeaderString("authorization");
        if (basicAuthSucceeds(basicAuthCredentials))
            return;

        throw new ApiException("Login required", Response.Status.UNAUTHORIZED);
    }

    private boolean basicAuthSucceeds(String credentials) {
        if (credentials == null)
            return false;

        credentials = Base64.base64Decode(credentials.replaceFirst("[Bb]asic ", ""));
        String[] credentialParts = credentials.split(":");
        if (credentialParts.length != 2)
            return false;

        Map<String, Object> query = new HashMap<>();
        query.put("username", credentialParts[0]);
        query.put("password", credentialParts[1]);
        DAO<Map> dao = new MongoDAO<>(COLLECTION_NAME);

        return !dao.search(query).isEmpty();

    }

}
