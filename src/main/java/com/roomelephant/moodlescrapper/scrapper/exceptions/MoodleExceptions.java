package com.roomelephant.moodlescrapper.scrapper.exceptions;


public sealed abstract class MoodleExceptions extends RuntimeException permits DisplayGradesNotFound, LoginFailed {
}
