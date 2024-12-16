package com.roomelephant.moodlescrapper.scrapper.courses;

import com.roomelephant.moodlescrapper.configuration.EnvVariables;
import com.roomelephant.moodlescrapper.scrapper.converter.gradable.GradableConverter;
import com.roomelephant.moodlescrapper.model.Gradable;
import com.roomelephant.moodlescrapper.scrapper.GradableDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CourseManagement {
    private final EnvVariables env;
    private final GradableConverter converter;

    public CourseManagement(EnvVariables env, GradableConverter converter) {
        this.env = env;
        this.converter = converter;
    }

    public Map<LocalDate, List<Gradable>> getGradables(List<GradableDTO> reviews) {
        Course course = switch (env.course()) {
            case "12" -> new JavaCourse(converter, reviews);
            default -> new NoOPCourse();
        };

        return course.getReviews();
    }
}
