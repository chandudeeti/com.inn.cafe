package com.inn.cafe.utils;

import com.google.common.base.Strings;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class CafeUtils {

    private CafeUtils() {
    }

    /**
     * Create a ResponseEntity containing the specified response message and HTTP status.
     *
     * @param responseMessage The message to include in the response.
     * @param httpStatus      The HTTP status code for the response.
     * @return A ResponseEntity containing the response message and HTTP status.
     */
    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus) {
        return new ResponseEntity<>("{\"message\":\"" + responseMessage + "\"}", httpStatus);
    }

    /**
     * Generate a unique identifier based on the current timestamp.
     *
     * @return A unique identifier in the format "BILL-{timestamp}".
     */
    public static String getUUID() {
        Date date = new Date();
        long time = date.getTime();
        return "BILL-" + time;
    }

    /**
     * Convert a JSON string into a JSONArray.
     *
     * @param data The JSON string to convert.
     * @return A JSONArray containing the parsed JSON data.
     * @throws JSONException if there is an issue parsing the JSON data.
     */
    public static JSONArray getJSONArrayFromString(String data) throws JSONException {
        JSONArray jsonArray = new JSONArray(data);
        return jsonArray;
    }

    /**
     * Convert a JSON string into a Map of key-value pairs.
     *
     * @param data The JSON string to convert.
     * @return A Map containing the key-value pairs from the JSON data.
     */
    public static Map<String, Object> getMapFromJSON(String data) {
        if (!Strings.isNullOrEmpty(data)) {
            return new Gson().fromJson(data, new TypeToken<Map<String, Object>>() {
            }.getType());
        }
        return new HashMap<>();
    }

    /**
     * Check if a file exists at the specified path.
     *
     * @param path The path to the file.
     * @return true if the file exists, false otherwise.
     */
    public static boolean isFileExists(String path) {
        log.info("Inside isFileExists" + path);
        try {
            File file = new File(path);
            return (file != null && file.exists()) ? Boolean.TRUE : Boolean.FALSE;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
