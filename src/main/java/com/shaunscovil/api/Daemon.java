package com.shaunscovil.api;

import org.glassfish.grizzly.http.server.HttpServer;

public class Daemon {

    public static void run(HttpServer server) {
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

}
