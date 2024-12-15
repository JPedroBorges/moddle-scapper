package com.roomelephant.moodlescrapper.courses;

import com.roomelephant.moodlescrapper.model.Gradable;
import com.roomelephant.moodlescrapper.scrapper.exceptions.DisplayGradesNotFound;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

public final class NoOPCourse implements Course {
    @Override
    public TreeMap<LocalDate, List<Gradable>> getReviews() throws DisplayGradesNotFound {
        return null;
    }
}
