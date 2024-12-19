package com.roomelephant.moopper.services.messages;

import com.roomelephant.moopper.model.Message;
import com.roomelephant.moopper.scrapper.MessageDTO;
import com.roomelephant.moopper.scrapper.converter.Converter;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MessageManagement {
    private final Converter<MessageDTO, Message> converter;

    public MessageManagement(Converter<MessageDTO, Message> converter) {
        this.converter = converter;
    }

    public TreeMap<LocalDate, List<Message>> getMessages(List<MessageDTO> messages) {
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
