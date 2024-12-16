package com.roomelephant.moopper.scrapper.converter;


import com.roomelephant.moopper.scrapper.GradableDTO;

public class ConverterExceptions extends RuntimeException {
    public ConverterExceptions(String field, GradableDTO original, Exception e) {
        super("Failed to convert field '" + field + "' with value '" + original + "'", e);
    }
}
