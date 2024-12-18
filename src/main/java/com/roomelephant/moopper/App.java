package com.roomelephant.moopper;

import com.roomelephant.moopper.configuration.EnvVariables;
import com.roomelephant.moopper.services.courses.CourseManagement;
import com.roomelephant.moopper.model.Gradable;
import com.roomelephant.moopper.controller.GradablesController;
import com.roomelephant.moopper.scrapper.GradableDTO;
import com.roomelephant.moopper.scrapper.MessageDTO;
import com.roomelephant.moopper.scrapper.Moodle;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class App {
    private final Moodle moodle;
    private final EnvVariables env;
    private final CourseManagement courseManagement;
    private final GradablesController gradablesController;

    public App(Moodle moodle, EnvVariables env, CourseManagement courseManagement, GradablesController gradablesController) {
        this.moodle = moodle;
        this.env = env;
        this.courseManagement = courseManagement;
        this.gradablesController = gradablesController;
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

        System.out.println(messagesDTO);

        Map<LocalDate, List<Gradable>> gradables = courseManagement.getGradables(gradablesDTO);
        gradablesController.presentGrdables(gradables);
    }
}