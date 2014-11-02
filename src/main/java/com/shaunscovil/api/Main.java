package com.shaunscovil.api;

import com.shaunscovil.api.data.mongodb.MongoDB;
import org.glassfish.grizzly.http.server.HttpServer;

import java.io.IOException;

public class Main {

    public static final Properties PROPERTIES = Configuration.getProperties(Environment.DEVELOPMENT);

    public static void main(String[] args) throws IOException {
        try {
            initializeDB();
            HttpServer server = Server.start();

            try {
                Object lock = new Object();
                synchronized (lock) {
                    while (true) {
                        lock.wait();
                    }
                }
            }
            catch (InterruptedException e) {
                System.out.print("Service interrupted, shutting down now");
                server.shutdownNow();
            }
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
