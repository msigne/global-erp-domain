package com.camlait.global.erp.domain.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Several Utilities to serialize/deserialize from/to JSON format.
 */
public final class JSONUtils {
    private static final Logger LOG = LoggerFactory.getLogger(JSONUtils.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JSONUtils() {
    }

    /**
     * Serializes this Object structure to JSON format.
     *
     * @return object in JSON format (String).
     */
    public static String serializeToJson(Object toSerialize) {
        try {
            return OBJECT_MAPPER.writeValueAsString(toSerialize);
        } catch (JsonProcessingException e) {
            LOG.error("Unable to serialize tagging to JSON. Moderation={}", toSerialize.toString());
            throw new RuntimeException("Unable to serialize the provided object", e);
        }
    }

    /**
     *
     * De-Serializes the JSON message to an Object of the specified clazz.
     *
     * @param message Serialized version of the JSON object
     * @param clazz Target class to deserialize to
     *
     * @return Deserialized message into object
     *
     */
    public static <T> T parseMessage(String message, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(message.getBytes(), clazz);
        } catch (IOException e) {
            LOG.error("Unable to parse message=[{}]", message, e);
            throw new RuntimeException("Unable to parse the provided message", e);
        }
    }

    /**
     * Makes a deep copy of the provided object by serializing to a JSON form and the deserializing into
     * a new object.
     *
     * @param from
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T copy(T from) {
        String serialized = JSONUtils.serializeToJson(from);
        return parseMessage(serialized, (Class<T>) from.getClass());
    }
}