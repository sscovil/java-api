package com.shaunscovil.api;

import com.shaunscovil.api.configuration.Configuration;
import com.shaunscovil.api.configuration.ConfigurationFactory;
import com.shaunscovil.api.configuration.RuntimeConfiguration;
import com.shaunscovil.api.data.mongodb.MongoDB;
import com.shaunscovil.api.http.Server;
import org.glassfish.grizzly.http.server.HttpServer;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import javax.naming.ConfigurationException;
import java.io.IOException;

public class Main {

    public static RuntimeConfiguration runtimeConfig;

    public static Configuration apiConfig;

    public static void main(String[] args) throws IOException, ConfigurationException {
        setRuntimeConfig(args);
        setApiConfig();
        initializeDB();
        startServer();
    }

    private static void setRuntimeConfig(String[] args) {
        runtimeConfig = new RuntimeConfiguration();
        CmdLineParser parser = new CmdLineParser(runtimeConfig);

        try {
            parser.parseArgument(args);
        }
        catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        }
    }

    private static void setApiConfig() {
        try {
            apiConfig = ConfigurationFactory.buildAPIConfig(runtimeConfig.apiConfigFilePath);
        }
        catch (ConfigurationException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void initializeDB() {
        try {
            MongoDB.getInstance();
        }
        catch (Exception e) {
            System.err.println("Unable to initialize database");
            System.err.println(e.getMessage());
        }
    }

    private static void startServer() {
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

}
