package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SecretManager {

    private static final Properties secrets = new Properties();

    static {
        try (InputStream input = SecretManager.class
                .getClassLoader()
                .getResourceAsStream("secret/secrets.properties")) {

            if (input != null) {
                secrets.load(input);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load secrets.properties", e);
        }
    }

    private SecretManager() {
    }

    public static String get(String key) {
        String envValue = System.getenv(key);

        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        String fileValue = secrets.getProperty(key);

        if (fileValue != null && !fileValue.isBlank()) {
            return fileValue;
        }

        throw new IllegalStateException(
                key + " is not set. Provide it as an environment variable or in secrets.properties"
        );
    }

}


