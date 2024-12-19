package com.roomelephant.moopper;

import com.roomelephant.moopper.configuration.EnvVariables;
import com.roomelephant.moopper.controller.GradablesController;
import com.roomelephant.moopper.controller.MessageController;
import com.roomelephant.moopper.model.Gradable;
import com.roomelephant.moopper.model.Message;
import com.roomelephant.moopper.scrapper.GradableDTO;
import com.roomelephant.moopper.scrapper.MessageDTO;
import com.roomelephant.moopper.scrapper.Moodle;
import com.roomelephant.moopper.services.courses.CourseManagement;
import com.roomelephant.moopper.services.messages.MessageManagement;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class App {
    private final Moodle moodle;
    private final EnvVariables env;
    private final CourseManagement courseManagement;
    private final MessageManagement messageManagement;
    private final GradablesController gradablesController;
    private final MessageController messageController;

    public App(Moodle moodle, EnvVariables env, CourseManagement courseManagement, MessageManagement messageManagement, GradablesController gradablesController, MessageController messageController) {
        this.moodle = moodle;
        this.env = env;
        this.courseManagement = courseManagement;
        this.messageManagement = messageManagement;
        this.gradablesController = gradablesController;
        this.messageController = messageController;
    }

    public void launch() {
        List<MessageDTO> messagesDTO;
        List<GradableDTO> gradablesDTO;
        try {
            moodle.login();

            messagesDTO = moodle.getMessages();
            gradablesDTO = moodle.getGradables(env.course());
        } finally {
            moodle.close();
        }

        TreeMap<LocalDate, List<Message>> messages = messageManagement.getMessages(messagesDTO);
        messageController.show(messages);

        Map<LocalDate, List<Gradable>> gradables = courseManagement.getGradables(gradablesDTO);
        gradablesController.show(gradables);
    }
}