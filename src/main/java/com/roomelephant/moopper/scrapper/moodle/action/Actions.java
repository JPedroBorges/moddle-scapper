package com.roomelephant.moopper.scrapper.moodle.action;

import org.openqa.selenium.WebDriver;

public abstract class Actions {
    void navigateCoursePage(WebDriver driver, String baseUrl, String courseId, String sectionId) {
        driver.get(baseUrl + "course/view.php?id=" + courseId + "&section=" + sectionId);
    }
}
