package com.roomelephant.moopper.services.messages;

import com.roomelephant.moopper.adapter.Moodle;
import com.roomelephant.moopper.adapter.scrapper.MessageDTO;
import com.roomelephant.moopper.adapter.scrapper.converter.Converter;
import com.roomelephant.moopper.model.Message;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MessageService {
    private final Moodle moodle;
    private final Converter<MessageDTO, Message> converter;

    public MessageService(Moodle moodle, Converter<MessageDTO, Message> converter) {
        this.moodle = moodle;
        this.converter = converter;
    }

    public Map<LocalDate, List<Message>> getMessages() {
        List<MessageDTO> messages = moodle.getMessages();
        List<Message> gradables = convert(messages);

        return gradables.stream()
                .toList().stream()
                .collect(Collectors.groupingBy(
                        gradable -> gradable.date().toLocalDate(),
                        TreeMap::new,
                        Collectors.toList()
                ));
    }

    private List<Message> convert(List<MessageDTO> reviews) {
        return reviews.stream().map(converter::convert).toList();
    }
}
