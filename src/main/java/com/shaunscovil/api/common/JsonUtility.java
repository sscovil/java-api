package com.shaunscovil.api.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaunscovil.api.exception.ApiException;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;

public class JsonUtility {

    protected static final ObjectMapper MAPPER = new ObjectMapper();

    public static final String SERIALIZATION_ERROR_MESSAGE = "Unable to serialize object to JSON";

    public static final String DESERIALIZATION_ERROR_MESSAGE = "Not a JSON object";

    public static String serializeJson(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ApiException(SERIALIZATION_ERROR_MESSAGE, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static <T> T deserializeJson(String json) {
        try {
            return MAPPER.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            throw new ApiException(DESERIALIZATION_ERROR_MESSAGE, Response.Status.BAD_REQUEST);
        }
    }

    public static <T> T deserializeJson(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new ApiException(DESERIALIZATION_ERROR_MESSAGE, Response.Status.BAD_REQUEST);
        }
    }

    public static <T> T deserializeJson(String json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            throw new ApiException(DESERIALIZATION_ERROR_MESSAGE, Response.Status.BAD_REQUEST);
        }
    }

}
