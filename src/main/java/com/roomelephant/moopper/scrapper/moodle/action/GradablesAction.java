package com.roomelephant.moopper.scrapper.moodle.action;

import com.roomelephant.moopper.scrapper.GradableDTO;
import com.roomelephant.moopper.scrapper.moodle.exceptions.DisplayGradesNotFound;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class GradablesAction extends Actions {
    private static final Logger logger = LoggerFactory.getLogger(GradablesAction.class);

    public List<GradableDTO> getReviews(WebDriver driver, String baseUrl, String courseId, String sectionId) {
        logger.debug("operation='getReviews', message='Navigating to course page'");
        navigateCoursePage(driver, baseUrl, courseId, sectionId);
        clickDisplayButton(driver);
        return extractGradables(driver);
    }

    private void clickDisplayButton(WebDriver driver) {
        List<WebElement> btns = driver.findElements(By.className("btn"));
        Optional<WebElement> buttonToClick = btns.stream().filter(btn -> btn.getText().equals("Contrair/Expandir tudo")).findFirst();
        if (buttonToClick.isEmpty()) {
            throw new DisplayGradesNotFound();
        }
        buttonToClick.get().click();
    }

    private List<GradableDTO> extractGradables(WebDriver driver) {
        logger.debug("operation='extractGradables', message='Exacting gradable'");
        List<WebElement> modules = driver.findElements(By.className("module"));

        return modules.stream().map(module -> {
            String exercise = module.findElement(By.className("grademe-mod-name")).getText();

            List<WebElement> gradables = module.findElements(By.className("gradable"));
            return gradables.stream()
                    .map(gradable -> {
                        WebElement wrapper = gradable.findElement(By.className("gradable-wrap"));

                        String link = gradable.findElement(By.className("gradable-icon")).getDomAttribute("href");
                        String name = wrapper.findElement(By.className("gradable-user")).getText();
                        String date = wrapper.findElement(By.className("gradable-date")).getDomProperty("innerText");

                        return new GradableDTO(link, name, date, exercise);
                    })
                    .toList();
        }).flatMap(Collection::stream).toList();
    }
}
