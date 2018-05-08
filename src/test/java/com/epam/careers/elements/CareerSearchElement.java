package com.epam.careers.elements;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@Slf4j
public class CareerSearchElement {
    private static final By KEYWORD_SELECTOR = By.cssSelector("input.job-search-input");
    private static final By CITY_DROPDOWN_SELECTOR = By.cssSelector("ul#select-box-location-select-results");
    private static final String COUNTRY_BASED_ON_CITY_LOCATOR = "//li[starts-with(@id, 'select-box-location-select-result') and contains(text(), '%s')]/ancestor::li";
    private static final String ACTIVE_COUNTRY_LOCATOR = "li[aria-label='%s'].dropdown-invisible-group";
    private static final By CITYS_SELECTOR = By.xpath("//li[starts-with(@id, 'select-box-location-select-result')]");
    private static final String CITY_LOCATOR = "//li[starts-with(@id, 'select-box-location-select-result') and contains(text(), '%s')]";
    private static final By FILTERS_DROPDOWN_SELECTOR = By.cssSelector("div.multi-select-filter");
    private static final By FILTER_WINDOW_SELECTOR = By.cssSelector("div.multi-select-dropdown");
    private static final String FILTER_LOCATOR = "//div[@class='multi-select-dropdown']//span[contains(text(), '%s')]";
    private static final By SEARCH_BUTTON_DESKTOP_SELECTOR = By.cssSelector("div.career-apply-box-desktop > button[name='Search']");
    private WebDriver webDriver;
    private WebDriverWait webDriverWait;

    public CareerSearchElement(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.webDriverWait = new WebDriverWait(webDriver, 10);
    }

    public CareerSearchElement setKeyword(String keyword) {
        if (keyword.isEmpty()) {
            return this;
        }
        log.info(String.format("Type keyword: '%s' into Keyword Field", keyword));
        webDriver.findElement(KEYWORD_SELECTOR).sendKeys(keyword);
        return this;
    }

    public CareerSearchElement selectCity(String cityName) {
        log.info(String.format("Select '%s' from Cities dropdown list", cityName));
        webDriver.findElement(By.cssSelector("div.select-box-selection")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(CITY_DROPDOWN_SELECTOR));

        WebElement cityToSelect = webDriver.findElement(By.xpath(String.format(CITY_LOCATOR, cityName)));
        if (!cityToSelect.getText().isEmpty()) {
            cityToSelect.click();
            return this;
        }

        WebElement country = webDriver.findElement(By.xpath(String.format(COUNTRY_BASED_ON_CITY_LOCATOR, cityName)));
        val countryName =  country.getAttribute("aria-label");
        country.click();

        country = webDriverWait.until(
                ExpectedConditions.refreshed(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.cssSelector(
                                        String.format(ACTIVE_COUNTRY_LOCATOR, countryName)))));



        //List<WebElement> citiesOptionsList = country.findElements(By.tagName("li"));
        List<WebElement> citiesOptionsList = webDriver.findElements(CITYS_SELECTOR);

        citiesOptionsList.stream()
                .filter(webElement -> webElement.getText().equalsIgnoreCase(cityName))
                .findFirst()
                .get().click();

        return this;
    }

    public CareerSearchElement selectFilters(String filterName) {
        if (filterName.isEmpty() || filterName.equalsIgnoreCase("Select all")) {
            return this;
        }
        log.info(String.format("Select '%s' from filters list", filterName));
        webDriver.findElement(FILTERS_DROPDOWN_SELECTOR).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(FILTER_WINDOW_SELECTOR));
        ////div[@class='multi-select-dropdown']//span[contains(text(), '%s')]/preceding-sibling::input
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(FILTER_LOCATOR, filterName))));
        webDriver.findElement(By.xpath(String.format(FILTER_LOCATOR, filterName))).click();
        return this;
    }

    public void searchAvailablePositions() {
        log.info("Click 'Search' button");
        webDriver.findElement(SEARCH_BUTTON_DESKTOP_SELECTOR).click();
    }
}
