package com.shaunscovil.api;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Server {

    public static final String BASE_URI = Main.PROPERTIES.getProperty(Property.BASE_URI.key());

    public static HttpServer start() {
        final ResourceConfig rc = new ResourceConfig().packages("com.shaunscovil.api");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

}
