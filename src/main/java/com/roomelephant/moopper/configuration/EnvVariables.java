package com.roomelephant.moopper.configuration;

public class EnvVariables {
    private final String baseUrl = System.getenv("BASE_URL");
    private final String username = System.getenv("MUSERNAME");
    private final String password = System.getenv("MPASSWORD");
    private final String course = System.getenv("COURSE");
    private final String interactive = System.getProperty("interactive");

    public String baseUrl() {
        return baseUrl;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String course() {
        return course;
    }

    public String interactive() {
        return interactive;
    }
}

