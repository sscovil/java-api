package com.shaunscovil.api;

public enum Environment {

    DEVELOPMENT,
    STAGING,
    PRODUCTION;

    public String filename() {
        return String.format("%s.properties", this.name().toLowerCase());
    }

}
