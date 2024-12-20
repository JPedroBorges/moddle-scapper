package com.roomelephant.moopper.adapter.scrapper.moodle.exceptions;


public sealed abstract class MoodleExceptions extends RuntimeException permits DisplayGradesNotFound, LoginFailed {
}
