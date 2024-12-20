package com.roomelephant.moopper.adapter.scrapper.converter.gradable;

import com.roomelephant.moopper.model.Message;
import com.roomelephant.moopper.adapter.scrapper.MessageDTO;
import com.roomelephant.moopper.adapter.scrapper.converter.Converter;
import com.roomelephant.moopper.adapter.scrapper.converter.ConverterExceptions;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class MessageConverter implements Converter<MessageDTO, Message> {
    @Override
    public Message convert(MessageDTO dto) {
        String finalName;
        URL finalUrl;
        LocalDateTime finalDate;
        String finalSubject;
        try {
            finalName = getName(dto);
        } catch (Exception e) {
            throw new ConverterExceptions("name", dto, e);
        }
        try {
            finalUrl = getUrl(dto);
        } catch (Exception e) {
            throw new ConverterExceptions("link", dto, e);
        }
        try {
            finalDate = getDate(dto);
        } catch (Exception e) {
            throw new ConverterExceptions("date", dto, e);
        }
        try {
            finalSubject = dto.subject();
        } catch (Exception e) {
            throw new ConverterExceptions("exercise", dto, e);
        }

        return new Message(finalUrl, finalName, finalSubject, finalDate);
    }

    private static String getName(MessageDTO dto) {
        String finalName;
        finalName = dto.name().replace(" ", "_");
        return finalName;
    }

    private static LocalDateTime getDate(MessageDTO dto) {
        LocalDateTime finalDate;
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("d 'de' MMMM 'de' yyyy 'às' HH:mm")
                .parseDefaulting(ChronoField.YEAR, LocalDateTime.now().getYear())
                .toFormatter(Locale.forLanguageTag("pt"));
        finalDate = LocalDateTime.parse(dto.date().split(", ")[1], formatter);
        return finalDate;
    }

    private static URL getUrl(MessageDTO dto) throws URISyntaxException, MalformedURLException {
        URL finalUrl;
        URI uri = new URI(dto.link());
        finalUrl = uri.toURL();
        return finalUrl;
    }

}
