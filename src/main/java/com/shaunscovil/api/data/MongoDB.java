package com.shaunscovil.api.data;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.shaunscovil.api.Main;
import com.shaunscovil.api.Property;
import com.shaunscovil.api.exception.ApiException;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class MongoDB {

    private DB database;

    public static DB getDatabase() {
        return MongoDB.getInstance().database;
    }

    public synchronized static MongoDB getInstance() {
        return MongoDBHolder.INSTANCE;
    }

    private static class MongoDBHolder {
        private static final MongoDB INSTANCE = new MongoDB();
    }

    private MongoDB() {
        ServerAddress serverAddress = getServerAddress();
        List<MongoCredential> credentials = getCredentials();

        MongoClient mongoClient = credentials.isEmpty() ?
                new MongoClient(serverAddress) :
                new MongoClient(serverAddress, credentials);

        final String dbname = Main.PROPERTIES.getProperty(Property.MONGODB_DB_NAME.key(), "test");
        this.database = mongoClient.getDB(dbname);
    }

    private ServerAddress getServerAddress() {
        final String host = Main.PROPERTIES.getProperty(Property.MONGODB_HOST.key(), "localhost");
        final Integer port = Integer.parseInt(Main.PROPERTIES.getProperty(Property.MONGODB_PORT.key(), "27017"));

        try {
            return new ServerAddress(host, port);
        } catch (UnknownHostException e) {
            String message = String.format("Unknown host %s:%s", host, port);
            throw new ApiException(message, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    private List<MongoCredential> getCredentials() {
        final String username = Main.PROPERTIES.getProperty(Property.MONGODB_USERNAME.key(), "root");
        final String password = Main.PROPERTIES.getProperty(Property.MONGODB_PASSWORD.key(), "");
        final String authdb = Main.PROPERTIES.getProperty(Property.MONGODB_AUTH_DB.key(), "admin");

        List<MongoCredential> credentials = new ArrayList<>();
        if (!password.isEmpty())
            credentials.add(MongoCredential.createMongoCRCredential(username, authdb, password.toCharArray()));

        return credentials;
    }

}
