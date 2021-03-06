package com.epam.careers.elements;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Slf4j
public class OpenPositionItemElement {
    private static final String MESSAGE_FORMAT = "Position name: '%s', Department: '%s', Location: '%s'";
    private static final By POSITION_SELECTOR = By.cssSelector("div.position-name > a");
    private static final By DEPARTMENT_SELECTOR = By.cssSelector("div.department");
    private static final By LOCATION_SELECOTR = By.cssSelector("div.location");
    private static final By APPLY_BUTTON_SELECTOR = By.cssSelector("div.button-apply > a");
    private WebElement webElement;
    @Getter private String position;
    @Getter private String department;
    @Getter private String location;

    public OpenPositionItemElement(WebElement webElement) {
        this.webElement = webElement;
        this.position = webElement.findElement(POSITION_SELECTOR).getText();
        this.department = webElement.findElement(DEPARTMENT_SELECTOR).getText();
        this.location = webElement.findElement(LOCATION_SELECOTR).getText();
    }

    public void clickApply() {
        webElement.findElement(APPLY_BUTTON_SELECTOR).click();
    }

    @Override
    public String toString() {
        return String.format(MESSAGE_FORMAT, position, department, location);
    }
}
