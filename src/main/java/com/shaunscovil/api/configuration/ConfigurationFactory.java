package com.shaunscovil.api.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import javax.naming.ConfigurationException;
import java.io.*;
import java.util.Map;

public class ConfigurationFactory {

    public static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    public static Configuration buildAPIConfig(String configFilePath) throws ConfigurationException {
        InputStream inputStream = loadConfigFile(configFilePath);

        try {
            TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {};
            Map<String, Object> properties = mapper.readValue(inputStream, typeReference);

            return new Configuration(properties);
        }
        catch (IOException e) {
            String message = String.format("Unable to parse configuration file: %s", configFilePath);
            throw new ConfigurationException(message);
        }
    }

    private static InputStream loadConfigFile(String configFilePath) throws ConfigurationException {
        File file = new File(configFilePath);

        try {
            return new FileInputStream(file);
        }
        catch (FileNotFoundException e) {
            return loadConfigFileFromClassPath(configFilePath);
        }
        catch (SecurityException e) {
            String message = String.format("Access denied to configuration file: %s", file.getAbsolutePath());
            throw new ConfigurationException(message);
        }
    }

    private static InputStream loadConfigFileFromClassPath(String configFilePath) throws ConfigurationException {
        ClassLoader classLoader = ConfigurationFactory.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(configFilePath);

        if (inputStream == null) {
            String message = String.format("Unable to load configuration file: %s", configFilePath);
            throw new ConfigurationException(message);
        }

        return inputStream;
    }

}
