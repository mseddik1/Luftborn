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


    public static String getApiKey() {
        String sysKey = System.getenv("REQRES_API_KEY");
        String configKey = ConfigManager.get("REQRES_API_KEY");


        if (sysKey != null && !sysKey.isBlank()) return sysKey;
        else if (configKey != null && !configKey.isBlank()) return configKey;
        else throw new IllegalStateException(
                "API key is not set. Provide REQRES_API_KEY as env variable or API_KEY in config."
            );

    }



}
