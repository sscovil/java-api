package com.shaunscovil.api;

public class Properties extends java.util.Properties {

    public String getProperty(Property property) {
        return getProperty(property.key());
    }

    public String getProperty(Property property, String defaultValue) {
        return getProperty(property.key(), defaultValue);
    }

}
