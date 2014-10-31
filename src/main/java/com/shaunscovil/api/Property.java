package com.shaunscovil.api;

public enum Property {

    BASE_URI("api.baseURI"),
    MONGODB_HOST("api.data.mongodb.host"),
    MONGODB_PORT("api.data.mongodb.port"),
    MONGODB_USERNAME("api.data.mongodb.username"),
    MONGODB_AUTH_DB("api.data.mongodb.authDB"),
    MONGODB_PASSWORD("api.data.mongodb.password"),
    MONGODB_DB_NAME("api.data.mongodb.dbname");

    protected String key;

    Property(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
