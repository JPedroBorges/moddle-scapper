package com.roomelephant.moopper.configuration.bean;

import com.roomelephant.moopper.App;
import com.roomelephant.moopper.configuration.EnvVariables;
import com.roomelephant.moopper.controller.MessageController;
import com.roomelephant.moopper.scrapper.converter.gradable.GradableConverter;
import com.roomelephant.moopper.scrapper.converter.gradable.MessageConverter;
import com.roomelephant.moopper.services.courses.CourseManagement;
import com.roomelephant.moopper.controller.GradablesController;
import com.roomelephant.moopper.scrapper.Moodle;
import com.roomelephant.moopper.services.messages.MessageManagement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LoggingPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;

public class BeanManager {
    private static final Logger logger = LoggerFactory.getLogger(BeanManager.class);

    private EnvVariables env;
    private ChromeDriver chromeDriver;
    private GradableConverter gradableConverter;
    private MessageConverter messageConverter;
    private GradablesController gradablesController;
    private MessageController messageController;
    private Moodle moodle;
    private CourseManagement courseManagement;
    private MessageManagement messageManagement;
    private App app;

    public EnvVariables env() {
        if (env == null) {
            env = new EnvVariables();
        }
        return env;
    }

    public ChromeDriver chromeDriver() {
        if (chromeDriver == null) {
            logger.debug("operation=chromeDriver, message='creating chrome driver'");
            LoggingPreferences logs = new LoggingPreferences();
            logs.enable("browser", Level.OFF);  // Disable browser logs

            // Chrome options with logging preferences
            ChromeOptions options = new ChromeOptions();
            options.setCapability("goog:loggingPrefs", logs);
            chromeDriver = new ChromeDriver(options);
        }
        return chromeDriver;
    }

    public GradableConverter gradableConverter() {
        if (gradableConverter == null) {
            gradableConverter = new GradableConverter();
        }
        return gradableConverter;
    }
    public MessageConverter messageConverter() {
        if (messageConverter == null) {
            messageConverter = new MessageConverter();
        }
        return messageConverter;
    }

    public GradablesController gradablesController() {
        if (gradablesController == null) {
            gradablesController = new GradablesController();
        }
        return gradablesController;
    }

    public MessageController messageController() {
        if (messageController == null) {
            messageController = new MessageController();
        }
        return messageController;
    }

    public Moodle moodle() {
        if (moodle == null) {
            moodle = Moodle.builder()
                    .withBaseUrl(env().baseUrl())
                    .withUsername(env().username())
                    .withPassword(env().password())
                    .withDriver(chromeDriver())
                    .build();
        }
        return moodle;
    }

    public CourseManagement courseManagement() {
        if (courseManagement == null) {
            courseManagement = new CourseManagement(env(), gradableConverter());
        }
        return courseManagement;
    }

    public MessageManagement messageManagement() {
        if (messageManagement == null) {
            messageManagement = new MessageManagement(messageConverter());
        }
        return messageManagement;
    }

    public App app() {
        if (app == null) {
            app = new App(moodle(), env(), courseManagement(), messageManagement(), gradablesController(), messageController());
        }
        return app;
    }

}
