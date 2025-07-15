package com.roomelephant.moopper.adapter.scrapper.converter.gradable;

import com.roomelephant.moopper.adapter.scrapper.converter.Converter;
import com.roomelephant.moopper.adapter.scrapper.converter.ConverterExceptions;
import com.roomelephant.moopper.model.Gradable;
import com.roomelephant.moopper.adapter.scrapper.GradableDTO;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class GradableConverter implements Converter<GradableDTO, Gradable> {

    public static final LocalDateTime NOW = LocalDateTime.now();

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
                .parseDefaulting(ChronoField.YEAR, LocalDate.now().getYear())
                .toFormatter(Locale.forLanguageTag("pt"));
        finalDate = LocalDateTime.parse(dto.date(), formatter);

        if (finalDate.isAfter(NOW)) {
            finalDate = finalDate.minusYears(1);
        }

        return finalDate;
    }

    private static URL getUrl(GradableDTO dto) throws URISyntaxException, MalformedURLException {
        URL finalUrl;
        URI uri = new URI(dto.link());
        finalUrl = uri.toURL();
        return finalUrl;
    }

}
