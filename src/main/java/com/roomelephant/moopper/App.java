package com.roomelephant.moopper;

import com.roomelephant.moopper.adapter.scrapper.Moodle;
import com.roomelephant.moopper.configuration.EnvVariables;
import com.roomelephant.moopper.controller.GradablesController;
import com.roomelephant.moopper.controller.MessageController;
import com.roomelephant.moopper.model.Gradable;
import com.roomelephant.moopper.model.Message;
import com.roomelephant.moopper.services.courses.CourseService;
import com.roomelephant.moopper.services.messages.MessageService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class App {
    private final Moodle moodle;
    private final EnvVariables env;
    private final CourseService courseService;
    private final MessageService messageService;
    private final GradablesController gradablesController;
    private final MessageController messageController;

    public App(Moodle moodle, EnvVariables env, CourseService courseService, MessageService messageService, GradablesController gradablesController, MessageController messageController) {
        this.moodle = moodle;
        this.env = env;
        this.courseService = courseService;
        this.messageService = messageService;
        this.gradablesController = gradablesController;
        this.messageController = messageController;
    }

    public void launch() {
        Map<LocalDate, List<Message>> messages;
        Map<LocalDate, List<Gradable>> gradables;
        try {
            moodle.login();

            messages = messageService.getMessages();
            gradables = courseService.getGradables();
        } finally {
            moodle.close();
        }

        messageController.show(messages);

        gradablesController.setIterable(Boolean.parseBoolean(env.interactive()));
        gradablesController.show(gradables);

    }
}