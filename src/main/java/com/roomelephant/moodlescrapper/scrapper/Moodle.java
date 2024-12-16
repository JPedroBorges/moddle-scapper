package com.roomelephant.moodlescrapper.scrapper;

import com.roomelephant.moodlescrapper.scrapper.moodle.action.GradablesAction;
import com.roomelephant.moodlescrapper.scrapper.moodle.action.LoginAction;
import com.roomelephant.moodlescrapper.scrapper.moodle.action.MessageAction;
import com.roomelephant.moodlescrapper.scrapper.moodle.exceptions.LoginFailed;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Moodle {
    private static final Logger logger = LoggerFactory.getLogger(Moodle.class);

    private final WebDriver driver;
    private final String baseUrl;
    private final String username;
    private final String password;
    private final String sectionId;
    private final GradablesAction gradableAction;
    private final LoginAction loginAction;
    private final MessageAction messageAction;

    private Moodle(WebDriver driver, String baseUrl, String username, String password, String sectionId) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
        this.sectionId = sectionId;
        this.loginAction = new LoginAction();
        this.gradableAction = new GradablesAction();
        this.messageAction = new MessageAction();
    }

    public void login() throws LoginFailed {
        loginAction.login(driver, baseUrl, username, password);
    }

    public List<GradableDTO> getGradables(String courseId) {
        return gradableAction.getReviews(driver, baseUrl, courseId, sectionId);
    }

    public List<MessageDTO> getMessages() {
        return messageAction.getMessage(driver, baseUrl);
    }

    public void close() {
        driver.quit();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private WebDriver driver;
        private String baseUrl;
        private String username;
        private String password;
        private String sectionId = "1";

        public Builder withDriver(WebDriver driver) {
            this.driver = driver;
            return this;
        }

        public Builder withBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withSectionId(String sectionId) {
            this.sectionId = sectionId;
            return this;
        }

        public Moodle build() {
            return new Moodle(driver, baseUrl, username, password, sectionId);
        }
    }
}
