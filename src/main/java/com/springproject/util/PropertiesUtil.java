package com.springproject.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    public static Properties load(String fileName) {

        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            throw new RuntimeException("properties load failed ", e);
        }
        return properties;
    }

}
