package com.roomelephant.moopper.services.courses;

import com.roomelephant.moopper.adapter.Moodle;
import com.roomelephant.moopper.adapter.scrapper.GradableDTO;
import com.roomelephant.moopper.adapter.scrapper.converter.Converter;
import com.roomelephant.moopper.adapter.scrapper.converter.gradable.GradableConverter;
import com.roomelephant.moopper.configuration.EnvVariables;
import com.roomelephant.moopper.model.Gradable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CourseService {
    private final Moodle moodle;
    private final EnvVariables env;
    private final Converter<GradableDTO, Gradable> converter;

    public CourseService(Moodle moodle, EnvVariables env, GradableConverter converter) {
        this.moodle = moodle;
        this.env = env;
        this.converter = converter;
    }

    public Map<LocalDate, List<Gradable>> getGradables() {
        List<GradableDTO> reviews = moodle.getGradables(env.course());
        Course course = switch (env.course()) {
            case "12" -> new JavaCourse(converter, reviews);
            default -> new NoOPCourse();
        };

        return course.getReviews();
    }
}
