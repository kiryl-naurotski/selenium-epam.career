package com.epam.careers.pages;

import com.epam.careers.elements.CareerSearchResultHeaderElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OpenPositionsPage {
    private static final By CAREER_SEARCH_RESULT_HEADER_SELECTOR = By.cssSelector("div.career-search-result span.searched-for");
    private WebDriver webDriver;
    private WebDriverWait webDriverWait;

    public OpenPositionsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.webDriverWait = new WebDriverWait(webDriver, 3);
        webDriverWait.withTimeout(Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(CAREER_SEARCH_RESULT_HEADER_SELECTOR));
    }

    public CareerSearchResultHeaderElement viewSearchResult() {
        return new CareerSearchResultHeaderElement(webDriver);
    }
}
