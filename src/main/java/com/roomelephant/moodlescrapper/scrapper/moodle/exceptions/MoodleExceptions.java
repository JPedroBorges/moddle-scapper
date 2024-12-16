package com.roomelephant.moodlescrapper.scrapper.moodle.exceptions;


public sealed abstract class MoodleExceptions extends RuntimeException permits DisplayGradesNotFound, LoginFailed {
}
