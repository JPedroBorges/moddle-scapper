package com.roomelephant.moopper.scrapper.converter;

public interface Converter<A, B> {
    B convert(A a);
}
