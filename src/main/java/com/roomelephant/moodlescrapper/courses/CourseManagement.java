package com.roomelephant.moodlescrapper.courses;

import com.roomelephant.moodlescrapper.configuration.EnvVariables;
import com.roomelephant.moodlescrapper.converter.gradable.GradableConverter;
import com.roomelephant.moodlescrapper.model.Gradable;
import com.roomelephant.moodlescrapper.scrapper.GradableDTO;
import com.roomelephant.moodlescrapper.scrapper.Moodle;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

public class CourseManagement {
    private final Moodle moodle;
    private final EnvVariables env;
    private final GradableConverter converter;

    public CourseManagement(Moodle moodle, EnvVariables env, GradableConverter converter) {
        this.moodle = moodle;
        this.env = env;
        this.converter = converter;
    }

    public TreeMap<LocalDate, List<Gradable>> getGradables() {
        List<GradableDTO> reviews = moodle.getGradables(env.course());
        Course course = switch (env.course()) {
            case "12" -> new JavaCourse(converter, reviews);
            default -> new NoOPCourse();
        };

        TreeMap<LocalDate, List<Gradable>> gradablesByDate = course.getReviews();

        moodle.close();

        return gradablesByDate;
    }
}
