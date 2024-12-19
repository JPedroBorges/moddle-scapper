package com.roomelephant.moopper.services.courses;

import com.roomelephant.moopper.configuration.EnvVariables;
import com.roomelephant.moopper.scrapper.converter.Converter;
import com.roomelephant.moopper.scrapper.converter.gradable.GradableConverter;
import com.roomelephant.moopper.model.Gradable;
import com.roomelephant.moopper.scrapper.GradableDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CourseManagement {
    private final EnvVariables env;
    private final Converter<GradableDTO, Gradable> converter;

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
