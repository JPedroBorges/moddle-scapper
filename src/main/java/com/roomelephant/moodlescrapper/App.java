package com.roomelephant.moodlescrapper;

import com.roomelephant.moodlescrapper.presentation.GradablesPresentation;
import com.roomelephant.moodlescrapper.bean.BeanManager;
import com.roomelephant.moodlescrapper.courses.CourseManagement;
import com.roomelephant.moodlescrapper.model.Gradable;
import com.roomelephant.moodlescrapper.scrapper.Moodle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.debug("initializing beans");
        BeanManager beanManager = new BeanManager();

        Moodle moodle = beanManager.moodle();
        CourseManagement courseManagement = beanManager.courseManagement();
        GradablesPresentation gradablesPresentation = beanManager.gradablesPresentation();

        Map<LocalDate, List<Gradable>> gradables;

        try {
            logger.debug("logging in platform");
            moodle.init();
            logger.debug("getting gradables");
            gradables = courseManagement.getGradables();
        } finally {
            moodle.close();
        }

        logger.debug("presenting gradables");
        gradablesPresentation.presentGrdables(gradables);
    }
}