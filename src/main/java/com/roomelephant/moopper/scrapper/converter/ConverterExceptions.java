package com.roomelephant.moopper.scrapper.converter;

public class ConverterExceptions extends RuntimeException {
    public ConverterExceptions(String field, Object original, Exception e) {
        super("Failed to convert field '" + field + "' with value '" + original + "'", e);
    }
}
