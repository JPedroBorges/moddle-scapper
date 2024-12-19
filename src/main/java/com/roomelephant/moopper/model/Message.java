package com.roomelephant.moopper.model;

import java.net.URL;
import java.time.LocalDateTime;

public record Message(URL link, String name, String subject, LocalDateTime date) {
}
