package com.shaunscovil.api.data.mongodb;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.shaunscovil.api.Main;

import javax.inject.Singleton;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class MongoDB {

    private DB database;

    public static DB getDatabase() {
        return MongoDB.getInstance().database;
    }

    public static MongoDB getInstance() {
        return MongoDBHolder.INSTANCE;
    }

    private static class MongoDBHolder {
        private static final MongoDB INSTANCE = new MongoDB();
    }

    private MongoDB() {
        List<MongoCredential> credentials = getCredentials();
        List<ServerAddress> serverAddress = getServerAddress();

        MongoClient mongoClient = Boolean.valueOf(Main.apiConfig.getProperty("data.mongoDB.standalone")) ?
                new MongoClient(serverAddress.get(0), credentials):
                new MongoClient(serverAddress, credentials);

        this.database = mongoClient.getDB(Main.apiConfig.getProperty("data.mongoDB.dbname"));
    }

    private List<MongoCredential> getCredentials() {
        TypeReference<List<Map<String, String>>> typeReference = new TypeReference<List<Map<String, String>>>() {};
        List<Map<String, String>> properties = Main.apiConfig.getProperty("data.mongoDB.credentials", typeReference);
        List<MongoCredential> credentials = new ArrayList<>();

        for (Map<String, String> property : properties) {
            String username = property.get("username");
            String password = property.get("password");
            String authDB = property.get("authDB");
            credentials.add(MongoCredential.createMongoCRCredential(username, authDB, password.toCharArray()));
        }

        return credentials;
    }

    private List<ServerAddress> getServerAddress() {
        TypeReference<List<Map<String, String>>> typeReference = new TypeReference<List<Map<String, String>>>() {};
        List<Map<String, String>> properties = Main.apiConfig.getProperty("data.mongoDB.servers", typeReference);
        List<ServerAddress> serverAddresses = new ArrayList<>();

        for (Map<String, String> property : properties) {
            String host = property.get("host");
            Integer port = Integer.parseInt(property.get("port"));

            try {
                serverAddresses.add(new ServerAddress(host, port));
            }
            catch (UnknownHostException e) {
                System.err.println(e.getMessage());
            }
        }

        return serverAddresses;
    }

}
