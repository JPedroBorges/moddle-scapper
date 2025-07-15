package com.roomelephant.moopper.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvVariables {
    private static final Logger log = LoggerFactory.getLogger(EnvVariables.class);

    private final String baseUrl = System.getenv("BASE_URL");
    private final String username = System.getenv("MUSERNAME");
    private final String password = System.getenv("MPASSWORD");
    private final String course = System.getenv("COURSE");
    private final String interactive = System.getProperty("interactive");
    private final Properties properties = new Properties();


    public void load() {
        boolean printErrors = true;
        String filePath = System.getenv("CONFIG_PATH");
        if (filePath == null || filePath.isEmpty()) {
            filePath = "moopper.properties";
            printErrors = false;
        }

        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        } catch (IOException ex) {
            if (printErrors) {
                log.error("Could not load properties from {}", filePath, ex);
            }
        }
    }

    public String baseUrl() {
        return baseUrl == null ? properties.getProperty("moodle.baseurl") : baseUrl;
    }

    public String username() {
        return username == null ? properties.getProperty("moodle.username") : username;
    }

    public String password() {
        return password == null ? properties.getProperty("moodle.password") : password;
    }

    public String course() {
        return course == null ? properties.getProperty("moodle.course") : course;
    }

    public String interactive() {
        return interactive == null ? properties.getProperty("config.interactive") : interactive;
    }
}

