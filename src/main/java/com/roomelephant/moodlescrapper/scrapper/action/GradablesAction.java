package com.roomelephant.moodlescrapper.scrapper.action;

import com.roomelephant.moodlescrapper.scrapper.GradableDTO;
import com.roomelephant.moodlescrapper.scrapper.exceptions.DisplayGradesNotFound;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;

public class GradablesAction extends Actions {
    public List<GradableDTO> getReviews(WebDriver driver, String baseUrl, String courseId, String sectionId) {
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
        List<WebElement> gradables = driver.findElements(By.className("gradable"));
        return gradables.stream()
                .map(gradable -> {
                    String exercise = upperLevel(upperLevel(gradable)).findElement(By.className("grademe-mod-name")).getText();
                    String link = gradable.findElement(By.className("gradable-icon")).getDomAttribute("href");

                    WebElement wrapper = gradable.findElement(By.className("gradable-wrap"));
                    String name = wrapper.findElement(By.className("gradable-user")).getText();
                    String date = wrapper.findElement(By.className("gradable-date")).getText();

                    return new GradableDTO(link, name, date, exercise);
                })
                .toList();
    }

}
