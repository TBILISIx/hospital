package com.solvd.hospital.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum Config {

    DRIVER,
    URL,
    USERNAME,
    PASSWORD,
    POOL_SIZE;

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = Config.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("config.properties not found");
            }
            PROPERTIES.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public String getValue() {
        return PROPERTIES.getProperty("db." + this.name().toLowerCase());
    }
}