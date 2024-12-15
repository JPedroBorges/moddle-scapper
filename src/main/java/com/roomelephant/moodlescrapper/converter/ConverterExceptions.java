package com.roomelephant.moodlescrapper.converter;


import com.roomelephant.moodlescrapper.scrapper.GradableDTO;

public class ConverterExceptions extends RuntimeException {
    public ConverterExceptions(String field, GradableDTO original, Exception e) {
        super("Failed to convert field '" + field + "' with value '" + original + "'", e);
    }
}
