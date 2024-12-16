package com.roomelephant.moodlescrapper.scrapper.moodle.action;

import com.roomelephant.moodlescrapper.scrapper.moodle.exceptions.LoginFailed;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class LoginAction extends Actions {
    private static final Logger logger = LoggerFactory.getLogger(LoginAction.class);

    public void login(WebDriver driver, String baseUrl, String username, String password) {
        logger.debug("operation='login', message='Starting to login'");
        driver.get(baseUrl + "login/index.php");

        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("loginbtn"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();

        if (!Objects.requireNonNull(driver.getCurrentUrl()).contains("my")) {
            logger.error("operation='login', message='Could not login. Or redirect page is incorrect', url='{}'", driver.getCurrentUrl());
            throw new LoginFailed();
        }
        logger.debug("operation='login', message='Login successful'");
    }
}
