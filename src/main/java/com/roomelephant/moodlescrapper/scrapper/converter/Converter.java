package com.roomelephant.moodlescrapper.scrapper.converter;

public interface Converter<A, B> {
    B convert(A a);
}
