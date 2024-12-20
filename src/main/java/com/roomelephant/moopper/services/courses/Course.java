package com.roomelephant.moopper.services.courses;

import com.roomelephant.moopper.model.Gradable;
import com.roomelephant.moopper.adapter.scrapper.moodle.exceptions.DisplayGradesNotFound;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

public sealed interface Course permits JavaCourse, NoOPCourse {
    TreeMap<LocalDate, List<Gradable>> getReviews() throws DisplayGradesNotFound;
}
