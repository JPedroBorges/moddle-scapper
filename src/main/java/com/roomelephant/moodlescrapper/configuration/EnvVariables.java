package com.roomelephant.moodlescrapper.configuration;

public class EnvVariables {
    private final String  baseUrl = System.getenv("BASE_URL");
    private final String username = System.getenv("USERNAME");
    private final String password = System.getenv("PASSWORD");
    private final String course = System.getenv("COURSE");

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
}

