package com.shaunscovil.api;

import com.shaunscovil.api.auth.AuthFilter;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.container.ContainerRequestFilter;
import java.net.URI;

public class Server {

    public static final String BASE_URI = Main.PROPERTIES.getProperty(Property.BASE_URI);

    public static final ResourceConfig CONFIG = new ResourceConfig().packages("com.shaunscovil.api");

    public static HttpServer start() {
        CONFIG.register(AuthFilter.class, ContainerRequestFilter.class);

        try {
            return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), CONFIG);
        }
        catch (Exception e) {
            throw new RuntimeException("Could not start server", e);
        }
    }

}
