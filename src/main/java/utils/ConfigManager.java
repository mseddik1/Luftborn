package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config/config.properties")) {
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Configuration file not found");
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);

    }






}
