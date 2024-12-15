package com.roomelephant.moodlescrapper.scrapper;

import com.roomelephant.moodlescrapper.scrapper.action.GradablesAction;
import com.roomelephant.moodlescrapper.scrapper.exceptions.LoginFailed;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

public class Moodle {
    private final WebDriver driver;
    private final String baseUrl;
    private final String username;
    private final String password;
    private final String sectionId;
    private final GradablesAction gradableAction;

    private Moodle(WebDriver driver, String baseUrl, String username, String password, String sectionId) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
        this.sectionId = sectionId;
        this.gradableAction = new GradablesAction();
    }

    public void init() throws LoginFailed {
        login();
    }

    private void login() throws LoginFailed {
        driver.get(baseUrl + "login/index.php");

        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("loginbtn"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();

        if (!Objects.requireNonNull(driver.getCurrentUrl()).contains("my")) {
            throw new LoginFailed();
        }
    }

    public List<GradableDTO> getGradables(String courseId) {
        return gradableAction.getReviews(driver, baseUrl, courseId, sectionId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void close() {
        driver.quit();
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
