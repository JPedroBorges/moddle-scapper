package com.roomelephant.moodlescrapper;

import com.roomelephant.moodlescrapper.Presentation.GradablesPresentation;
import com.roomelephant.moodlescrapper.bean.BeanManager;
import com.roomelephant.moodlescrapper.courses.CourseManagement;
import com.roomelephant.moodlescrapper.model.Gradable;
import com.roomelephant.moodlescrapper.scrapper.Moodle;
import com.roomelephant.moodlescrapper.scrapper.exceptions.LoginFailed;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

public class App {

    public static void main(String[] args) {
        System.out.println("initializing beans" );
        BeanManager beanManager = new BeanManager();

        Moodle moodle = beanManager.moodle();
        CourseManagement courseManagement = beanManager.courseManagement();
        GradablesPresentation gradablesPresentation = beanManager.gradablesPresentation();

        try {
            System.out.println("logging in platform" );
            moodle.init();
        } catch (LoginFailed e) {
            moodle.close();
            throw new RuntimeException(e);
        }

        System.out.println("getting gradables" );
        TreeMap<LocalDate, List<Gradable>> gradables = courseManagement.getGradables();

        System.out.println("presenting gradables" );
        gradablesPresentation.presentGrdables(gradables);
    }
}