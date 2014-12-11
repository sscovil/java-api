package com.shaunscovil.api.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class Configuration {

    protected Map<String, Object> properties;

    public Configuration(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

    public String getProperty(String property) {
        return traverseProperties(this.properties, property).toString();
    }

    public <T> T getProperty(String property, TypeReference<T> typeReference) {
        Object properties = traverseProperties(this.properties, property);

        try {
            ObjectMapper mapper = ConfigurationFactory.mapper;
            String yaml = mapper.writeValueAsString(properties);

            return mapper.readValue(yaml, typeReference);
        }
        catch (IOException e) {
            return null;
        }
    }

    private Object traverseProperties(Map properties, String property) {
        String[] parts = property.split("\\.");

        if (parts.length > 1) {
            String key = parts[0];

            if (properties.containsKey(key)) {
                Map nestedProperties = (Map) properties.get(key);
                String nestedProperty = property.replaceFirst(key + ".", "");

                return traverseProperties(nestedProperties, nestedProperty);
            }

            return traverseProperties(properties, key);
        }
        else {
            Object value = properties.containsKey(property) ? properties.get(property) : null;

            return (value == null) ? "" : value;
        }
    }

}
