package com.roomelephant.moodlescrapper.model;

import java.net.URL;
import java.time.LocalDateTime;

public class Gradable {
    private URL link;
    private String name;
    private LocalDateTime date;
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

    public void setLink(URL link) {
        this.link = link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    @Override
    public String toString() {
        return "\t" + fillSpaces(link.toString(), 75) + "\t" + fillSpaces(name, 40) + "\t" + fillSpaces(exercise, 30);
    }

    private String fillSpaces(String name, int num) {
        StringBuilder sb = new StringBuilder(name);
        for (int i = name.length(); i < num; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}
