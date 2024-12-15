package com.roomelephant.moodlescrapper.scrapper.action;

import com.roomelephant.moodlescrapper.scrapper.GradableDTO;
import com.roomelephant.moodlescrapper.scrapper.exceptions.DisplayGradesNotFound;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;
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
        List<WebElement> modules = driver.findElements(By.className("module"));

        return modules.stream().map(module -> {
            WebElement moduleName = module.findElement(By.className("grademe-mod-name"));
            String exercise = moduleName.getText();

            List<WebElement> gradables = module.findElements(By.className("gradable"));
            return gradables.stream()
                    .map(gradable -> {
                        WebElement linkElement = gradable.findElement(By.className("gradable-icon"));
                        WebElement wrapper = gradable.findElement(By.className("gradable-wrap"));

                        WebElement userElement = wrapper.findElement(By.className("gradable-user"));
                        WebElement dateElement = wrapper.findElement(By.className("gradable-date"));

                        String link = linkElement.getDomAttribute("href");
                        String name = userElement.getText();
                        String date = dateElement.getDomProperty("innerText");

                        return new GradableDTO(link, name, date, exercise);
                    })
                    .toList();
        }).flatMap(Collection::stream).toList();
    }
}
