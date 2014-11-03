package com.shaunscovil.api;

import java.io.IOException;
import java.io.InputStream;

public class Configuration {

    public static Properties getProperties(Environment env) {
        Properties properties = new Properties();

        try {
            InputStream inputStream = Configuration.class.getClassLoader().getResourceAsStream(env.filename());
            properties.load(inputStream);
        }
        catch (IOException e) {
            String message = String.format("Could not load configuration file: %s", env.filename());
            throw new RuntimeException(message, e);
        }

        return properties;
    }

}
