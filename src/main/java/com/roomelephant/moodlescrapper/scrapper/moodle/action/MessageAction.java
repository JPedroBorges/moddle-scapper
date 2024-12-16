package com.roomelephant.moodlescrapper.scrapper.moodle.action;

import com.roomelephant.moodlescrapper.scrapper.MessageDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MessageAction extends Actions {
    private static final Logger logger = LoggerFactory.getLogger(MessageAction.class);

    public List<MessageDTO> getMessage(WebDriver driver, String baseUrl) {
        logger.debug("operation='getMessage', message='Navigating to message page'");
        driver.get(baseUrl + "blocks/itop_mailbox/view.php?type=inbox&filter=0");

        List<WebElement> unreadDivs = driver.findElements(By.className("unread"));

        return unreadDivs.stream().map(parentDiv -> {
            WebElement element = parentDiv.findElement(By.className("mailbox-name"));

            WebElement outterdiv = element.findElement(By.xpath("./*[1]"));

            String name = outterdiv.findElement(By.xpath("./*[1]")).getDomAttribute("href");
            String subject = parentDiv.findElement(By.className("mailbox-subject")).getText();
            String date = parentDiv.findElement(By.className("mailbox-date")).getText();

            return new MessageDTO(name, subject, date);

        }).toList();
    }
}
