package com.netty.rpc.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author otfot
 * @date
 */
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();


    public static <T> T jsonToObject(String json, Class<?> clazz) {
        T obj = null;
        JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
        try {
            obj = objectMapper.readValue(json, javaType);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

        return obj;
    }

    public static String objectToJson(Object o) {
        String json = "";

        try {
            json = objectMapper.writeValueAsString(o);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

        return json;
    }
}
