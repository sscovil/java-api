package com.shaunscovil.api;

import com.shaunscovil.api.data.MongoDB;
import org.glassfish.grizzly.http.server.HttpServer;

import java.io.IOException;
import java.util.Properties;

public class Main {

    public static final Properties PROPERTIES = Configuration.getProperties(Environment.DEVELOPMENT);

    public static void main(String[] args) throws IOException {
        try {
            final HttpServer server = APIServer.startServer();
            initializeDB();
            Daemon.run(server);
        }
        catch (Exception e) {
            throw new RuntimeException("Could not start server", e);
        }
    }

    public static void initializeDB() {
        try {
            MongoDB.getInstance();
        }
        catch (Exception e) {
            throw new RuntimeException("Could not initialize MongoDB singleton", e);
        }
    }

}
