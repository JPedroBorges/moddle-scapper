package com.roomelephant.moodlescrapper.scrapper.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class Actions {
    void navigateCoursePage(WebDriver driver, String baseUrl, String courseId, String sectionId) {
        driver.get(baseUrl + "course/view.php?id=" + courseId + "&section=" + sectionId);
    }

    static WebElement upperLevel(WebElement element) {
        return element.findElement(By.xpath(".."));
    }
}
