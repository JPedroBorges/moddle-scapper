package com.roomelephant.moodlescrapper.scrapper.courses;

import com.roomelephant.moodlescrapper.model.Gradable;
import com.roomelephant.moodlescrapper.scrapper.moodle.exceptions.DisplayGradesNotFound;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

public sealed interface Course permits JavaCourse, NoOPCourse {
    TreeMap<LocalDate, List<Gradable>> getReviews() throws DisplayGradesNotFound;
}
