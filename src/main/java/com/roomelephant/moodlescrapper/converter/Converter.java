package com.roomelephant.moodlescrapper.converter;

public interface Converter<A, B> {
    B convert(A a);
}
