package com.roomelephant.moopper.model;

import java.net.URL;
import java.time.LocalDateTime;

public class Gradable {
    private final URL link;
    private final String name;
    private final LocalDateTime date;
    private String exercise;

    public Gradable(URL link, String name, LocalDateTime date, String exercise) {
        this.link = link;
        this.name = name;
        this.date = date;
        this.exercise = exercise;
    }

    public URL link() {
        return link;
    }

    public String name() {
        return name;
    }

    public LocalDateTime date() {
        return date;
    }

    public String exercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

}
