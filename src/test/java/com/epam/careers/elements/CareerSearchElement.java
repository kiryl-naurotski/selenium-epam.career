package com.epam.careers.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CareerSearchElement {
    private static final By KEYWORD_SELECTOR = By.cssSelector("input.job-search-input");
    private static final By CITY_DROPDOWN_SELECTOR = By.cssSelector("ul#select-box-location-select-results");
    private static final By CITYS_SELECTOR = By.xpath("//li[starts-with(@id, 'select-box-location-select-result')]");
    private static final By FILTERS_DROPDOWN_SELECTOR = By.cssSelector("div.multi-select-filter");
    private static final By FILTER_WINDOW_SELECTOR = By.cssSelector("div.multi-select-dropdown");
    private static final String FILTER_LOCATOR = "//div[@class='multi-select-dropdown']//span[contains(text(), '%s')]";
    private static final By SEARCH_BUTTON_DESKTOP_SELECTOR = By.cssSelector("div.career-apply-box-desktop > button[name='Search']");
    private WebDriver webDriver;
    private WebDriverWait webDriverWait;

    public CareerSearchElement(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.webDriverWait = new WebDriverWait(webDriver, 3);
    }

    public CareerSearchElement setKeyword(String keyword) {
        webDriver.findElement(KEYWORD_SELECTOR).sendKeys(keyword);
        return this;
    }

    public CareerSearchElement selectCity(String cityName) {
        webDriver.findElement(By.cssSelector("div.select-box-selection")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(CITY_DROPDOWN_SELECTOR));
        List<WebElement> countyOptionsList = webDriver.findElements(CITYS_SELECTOR);
        countyOptionsList.stream()
                .filter(element -> element.getText().contains(cityName))
                .findFirst()
                .get()
                .click();
        /*WebElement usa = webDriver.findElement(By.cssSelector("ul#select-box-location-select-results > li[aria-label='США']"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", usa);*/
        return this;
    }

    public CareerSearchElement selectFilters(String filterName) {
        webDriver.findElement(FILTERS_DROPDOWN_SELECTOR).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(FILTER_WINDOW_SELECTOR));
        ////div[@class='multi-select-dropdown']//span[contains(text(), '%s')]/preceding-sibling::input
        webDriver.findElement(By.xpath(String.format(FILTER_LOCATOR, filterName))).click();
        return this;
    }

    public void searchAvailablePositions() {
        webDriver.findElement(SEARCH_BUTTON_DESKTOP_SELECTOR).click();
    }
}
