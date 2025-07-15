package com.roomelephant.moopper.adapter.scrapper.moodle.exceptions;


public sealed abstract class MoodleExceptions extends RuntimeException permits DisplayGradesNotFound, LoginFailed {
    public MoodleExceptions() {
    }

    public MoodleExceptions(String message) {
        super(message);
    }
}
