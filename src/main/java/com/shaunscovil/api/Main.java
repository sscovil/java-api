package com.shaunscovil.api;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {

    public static final String BASE_URI = "http://api.shaunscovil.com:8080/";

    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("com.shaunscovil.api");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.in.read();
        server.shutdownNow();
    }

}