package com.epam.careers.elements;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CareerSearchResultHeaderElement {
    private static final By OPEN_POSITIONS_LIST_SELECTOR = By.cssSelector("ul.search-result-list > li");
    private static final By POSITION_LIST_LOADER_SELECTOR = By.cssSelector("ul.search-result-list > li.preloader-blue");
    private static final By ACTIVE_SORT_LOCATOR = By.cssSelector("div.sort-by > span.active");
    private static final String SORT_LOCATOR = "//div[@class='sort-by']/span[@title='%s']";
    private WebDriver webDriver;
    private WebDriverWait webDriverWait;

    public CareerSearchResultHeaderElement(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.webDriverWait = new WebDriverWait(webDriver, 3);
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(OPEN_POSITIONS_LIST_SELECTOR));
    }

    public CareerSearchResultHeaderElement sortBy(String sort) {
        if(webDriver.findElement(ACTIVE_SORT_LOCATOR).getText().equalsIgnoreCase(sort)) {
            return this;
        }
        log.info(String.format("Sort results by '%s'", sort));
        webDriver.findElement(By.xpath(String.format(SORT_LOCATOR, sort))).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(POSITION_LIST_LOADER_SELECTOR));
        //webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='sort-by']//span[@title='Горячие вакансии' and contains(@class, 'active')]")));
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(POSITION_LIST_LOADER_SELECTOR));
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(OPEN_POSITIONS_LIST_SELECTOR));
        return this;
    }

    public List<OpenPositionItemElement> getAllOpenPosition() {
        List<OpenPositionItemElement> openPositionItemElementList = new ArrayList<>();
        webDriver.findElements(OPEN_POSITIONS_LIST_SELECTOR).forEach(webElement -> openPositionItemElementList.add(new OpenPositionItemElement(webElement)));
        return openPositionItemElementList;
    }

    public List<OpenPositionItemElement> getOpenPositionsWithTitle(String title) {
        return getAllOpenPosition().stream()
                .filter(item -> item.getPosition().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }
}
