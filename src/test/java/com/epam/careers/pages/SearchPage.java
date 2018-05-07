package com.epam.careers.pages;

import com.epam.careers.elements.CareerSearchElement;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
public class SearchPage {
    private static final String PAGE_URL = "https://careers.epam.by/";
    private static final By JOB_SEARCH_SELECOR = By.cssSelector("h2.job-search-title");
    private WebDriver webDriver;
    private WebDriverWait webDriverWait;

    public SearchPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.webDriverWait = new WebDriverWait(webDriver, 3);

        log.info("Open " + PAGE_URL);
        webDriver.get(PAGE_URL);
        webDriver.manage().window().maximize();
    }

    public CareerSearchElement scrollToJobSearchSection() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(JOB_SEARCH_SELECOR));
        WebElement jobSearchTitle = webDriver.findElement(JOB_SEARCH_SELECOR);
        log.info("Scroll to " + jobSearchTitle.getText());
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", jobSearchTitle);
        return new CareerSearchElement(webDriver);
    }
}
