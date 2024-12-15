package com.roomelephant.moodlescrapper.configuration;

public class EnvVariables {
    String baseUrl = System.getenv("BASE_URL");
    String username = System.getenv("USERNAME");
    String password = System.getenv("PASSWORD");
    String course = System.getenv("COURSE");

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

