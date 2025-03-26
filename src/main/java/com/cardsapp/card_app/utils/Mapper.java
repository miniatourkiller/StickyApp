package com.cardsapp.card_app.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class Mapper {
     private static Logger log = LoggerFactory.getLogger(Mapper.class);
    private static JsonMapper jsonMapper = new JsonMapper();

    public static <T> T objectToClass(Object obj, Class<T> classValue){
        T t = null;
        try {
            String objStr = jsonMapper.writeValueAsString(obj);
            t = jsonMapper.readValue(objStr, classValue);
        } catch (Exception e) {
            log.warn("Mapping error: {}", e.getLocalizedMessage());
        }
        return t;
    }

    public static <T> T objectToClassLoose(Object obj, Class<T> classValue){
        T t = null;
        try {
            jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String objStr = jsonMapper.writeValueAsString(obj);
            t = jsonMapper.readValue(objStr, classValue);
        } catch (Exception e) {
            log.warn("Mapping error: {}", e.getLocalizedMessage());
        }
        return t;
    }
    
    public static <T> T stringToClass(String obj, Class<T> classValue){
        T t = null;
        try {
            t = jsonMapper.readValue(obj, classValue);
        } catch (Exception e) {
            log.warn("Mapping error: {}", e.getLocalizedMessage());
        }
        return t;
    }

    public static <T> T stringToClassLoose(String obj, Class<T> classValue){
        T t = null;
        try {
            jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            t = jsonMapper.readValue(obj, classValue);
        } catch (Exception e) {
            log.warn("Mapping error: {}", e.getLocalizedMessage());
        }
        return t;
    }

    /**
     * The function `classToString` converts an object to a JSON string using a JSON mapper and handles
     * any mapping errors by logging a warning.
     * 
     * @param obj The `obj` parameter in the `classToString` method is an `Object` type, which means it
     * can accept any Java object as input. The method attempts to convert this object into a JSON
     * string using a `jsonMapper` object and returns the resulting JSON string. If an exception occurs
     * during
     * @return The method `classToString` returns a String representation of the given object `obj`
     * using a JSON mapper. If an exception occurs during the mapping process, a warning message is
     * logged and `null` is returned.
     */
    public static String classToString(Object obj){
        String str = null;
        try {
            str = jsonMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Mapping error: {}", e.getLocalizedMessage());
        }
        return str;
    }
}
