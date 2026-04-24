package utils;


import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class Utils {

    private static ObjectMapper objectMapper;


    public static JsonNode readAsJson(String stringData) {
        objectMapper = new ObjectMapper();
        JsonNode data = null;
        try {
            data = objectMapper.readTree(stringData);

        } catch (IOException e) {
            System.out.println(e);
        }
        return data;
    }

    public static JsonNode readAsJsonResource(String resourcePath) {

        objectMapper = new ObjectMapper();

        try (InputStream is = Utils.class
                .getClassLoader()
                .getResourceAsStream(resourcePath)) {

            if (is == null) {
                throw new RuntimeException("File not found in resources: " + resourcePath);
            }

            return objectMapper.readTree(is);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + resourcePath, e);
        }
    }



}
