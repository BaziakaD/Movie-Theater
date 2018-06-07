package com.baziaka.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.util.Set;

public class JacksonHelper {


    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static String objectToJson(Object objectForSerialization) {

        String json = null;

        try {
            json = mapper.writeValueAsString(objectForSerialization);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static <T> T getObjectFormJson(String json, TypeReference<T> typeReference) {
        T object = null;
        try {
            object = mapper.readValue(json, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static  <T> Set<T> getSetOfObjectsFromJson(String json, TypeReference<Set<T>> typeReference) {
        Set<T> object = null;
        try {
            object = mapper.readValue(json, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static  <T> String SetOfObjectsToJson(Set<T> objectsToSerialization, boolean isPrettyPrint) {
        ObjectWriter writer;
        if (isPrettyPrint) {
            writer = mapper.writerWithDefaultPrettyPrinter();
        } else {
            writer = mapper.writer();
        }

        String json = null;
        try {
            json = writer.writeValueAsString(objectsToSerialization);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static <T> Set<T> getSetOfObjectsFromJson(byte[] bytes, TypeReference<Set<T>> typeRef) {
        return getSetOfObjectsFromJson(new String(bytes), typeRef);
    }
}
