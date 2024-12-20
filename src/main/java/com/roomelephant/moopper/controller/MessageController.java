package com.roomelephant.moopper.controller;

import com.roomelephant.moopper.model.Message;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MessageController extends AbstractController<Map<LocalDate, List<Message>>, Message> {

    private static final String TOTAL_GRADABLES = "Total messages: ";

    @Override
    public void close() {
        // Nothing to do
    }

    @Override
    protected void headerSpecifics(Map<LocalDate, List<Message>> messagesByDate) {
        System.out.println(TOTAL_GRADABLES + elementsCount(messagesByDate));
        System.out.println(Colors.BOLD
                + TAB + fillSpaces("Name", NAME_MAX_LENGTH)
                + TAB + fillSpaces("Subject", EXERCISE_MAX_LENGTH)
                + TAB + fillSpaces("Link", URL_MAX_LENGTH));
    }

    @Override
    protected void pintBody(Map<LocalDate, List<Message>> messagesByDate) {
        if (messagesByDate.isEmpty()) {
            System.out.println(TAB + Colors.BOLD + Colors.GREEN + "No messages for today" + Colors.RESET);
            return;
        }

        LocalDate twoDaysBefore = LocalDate.now().minusDays(2);
        messagesByDate.keySet()
                .forEach(date -> {
                    List<Message> dailyMessage = messagesByDate.get(date);
                    Colors color = getColor(date, twoDaysBefore);

                    System.out.println("due: " + color.toString() + date.plusWeeks(1) + Colors.RESET
                            + "\tsubmitted: " + date + "\t(" + dailyMessage.size() + ")");

                    dailyMessage.stream()
                            .map(this::message)
                            .forEach(System.out::println);
                });
    }

    public String message(Message message) {
        return TAB + fillSpaces(message.name(), NAME_MAX_LENGTH)
                + TAB + fillSpaces(message.subject(), EXERCISE_MAX_LENGTH)
                + TAB + fillSpaces(message.link().toString(), URL_MAX_LENGTH);
    }
}
