package com.roomelephant.moopper.adapter.scrapper.converter;

public interface Converter<A, B> {
    B convert(A a);
}
