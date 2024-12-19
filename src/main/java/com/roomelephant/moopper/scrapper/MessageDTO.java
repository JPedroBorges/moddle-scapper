package com.roomelephant.moopper.scrapper;

public record MessageDTO(String link, String name, String subject, String date) implements DTO {
}
