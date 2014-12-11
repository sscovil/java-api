package com.shaunscovil.api.resource;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;

public class DynamicResourceConfig extends ResourceConfig {

    public DynamicResourceConfig() {
        String[] resourcePaths = new String[]{
                "objects", "books", "movies", "posts", "test"
        };

        for (String resourcePath : resourcePaths) {
            final Resource.Builder resourceBuilder = Resource.builder(DynamicResource.class);
            resourceBuilder.path(resourcePath);

            final Resource resource = resourceBuilder.build();
            registerResources(resource);
        }
    }

}
