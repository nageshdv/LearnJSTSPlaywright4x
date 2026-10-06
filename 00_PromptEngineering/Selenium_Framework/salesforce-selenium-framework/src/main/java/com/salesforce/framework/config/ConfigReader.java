package com.salesforce.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream stream = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (stream == null) {
                throw new ExceptionInInitializerError(
                        "config.properties not found on the classpath");
            }
            PROPERTIES.load(stream);

        } catch (IOException e) {
            throw new ExceptionInInitializerError(
                    "Failed to load config.properties: " + e.getMessage());
        }
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Required property '" + key + "' is missing or empty in config.properties");
        }
        return value.trim();
    }
}
