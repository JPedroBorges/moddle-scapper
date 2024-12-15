package com.roomelephant.moodlescrapper.converter.gradable;

import com.roomelephant.moodlescrapper.converter.Converter;
import com.roomelephant.moodlescrapper.converter.ConverterExceptions;
import com.roomelephant.moodlescrapper.model.Gradable;
import com.roomelephant.moodlescrapper.scrapper.GradableDTO;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class GradableConverter implements Converter<GradableDTO, Gradable> {
    @Override
    public Gradable convert(GradableDTO dto) {
        String finalName;
        URL finalUrl;
        LocalDateTime finalDate;
        String finalExercise;
        try {
            finalName = getName(dto);
        } catch (Exception e) {
            throw new ConverterExceptions("name", dto, e);
        }
        try {
            finalUrl = getUrl(dto);
        } catch (Exception e) {
            throw new ConverterExceptions("url", dto, e);
        }
        try {
            finalDate = getDate(dto);
        } catch (Exception e) {
            throw new ConverterExceptions("date", dto, e);
        }
        try {
            finalExercise = dto.exercise();
        } catch (Exception e) {
            throw new ConverterExceptions("exercise", dto, e);
        }

        return new Gradable(finalUrl, finalName, finalDate, finalExercise);
    }

    private static String getName(GradableDTO dto) {
        String finalName;
        finalName = dto.name().replace(" ", "_");
        return finalName;
    }

    private static LocalDateTime getDate(GradableDTO dto) {
        LocalDateTime finalDate;
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("MMMM d, h:mm a")
                .parseDefaulting(ChronoField.YEAR, 2024)
                .toFormatter(Locale.forLanguageTag("pt"));
        finalDate = LocalDateTime.parse(dto.date(), formatter);
        return finalDate;
    }

    private static URL getUrl(GradableDTO dto) throws URISyntaxException, MalformedURLException {
        URL finalUrl;
        URI uri = new URI(dto.link());
        finalUrl = uri.toURL();
        return finalUrl;
    }

}
