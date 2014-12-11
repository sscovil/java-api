package com.shaunscovil.api.http;

import com.shaunscovil.api.Main;
import com.shaunscovil.api.authentication.AuthenticationFilter;
import com.shaunscovil.api.resource.DynamicResourceConfig;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.container.ContainerRequestFilter;
import java.net.URI;

public class Server {

    public static final String BASE_URI = Main.apiConfig.getProperty("baseURI");

    public static final ResourceConfig CONFIG = new DynamicResourceConfig().packages("com.shaunscovil.api");

    public static HttpServer start() {
        CONFIG.register(AuthenticationFilter.class, ContainerRequestFilter.class);

        try {
            return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), CONFIG);
        }
        catch (Exception e) {
            throw new RuntimeException("Could not start server", e);
        }
    }

}
