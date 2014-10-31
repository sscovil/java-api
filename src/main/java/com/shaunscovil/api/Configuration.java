package com.shaunscovil.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    public static Properties getProperties(Environment env) {
        Properties properties = new Properties();

        try {
            InputStream inputStream = Configuration.class.getClassLoader().getResourceAsStream(env.filename());
            properties.load(inputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

}
