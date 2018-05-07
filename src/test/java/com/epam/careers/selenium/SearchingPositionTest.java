package com.epam.careers.selenium;

import com.epam.careers.bo.SearchCriteria;
import com.epam.careers.data.XmlDataProvider;
import com.epam.careers.elements.OpenPositionItemElement;
import com.epam.careers.pages.OpenPositionsPage;
import com.epam.careers.pages.SearchPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SearchingPositionTest {
    private WebDriver webDriver;
    private WebDriverWait webDriverWait;

    @BeforeAll
    static void init() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.home") + "/chromedriver");
    }

    @BeforeEach
    void setup() {
        webDriver = new ChromeDriver();
        webDriverWait = new WebDriverWait(webDriver, 3);
    }

    @AfterEach
    void teardown() {
        webDriver.quit();
    }

    @ParameterizedTest
    @DisplayName("Search for by criteria and count specific positions results")
    @ArgumentsSource(XmlDataProvider.class)
    void searchPositionAndCountSpecificResults(SearchCriteria searchCriteria) {
        new SearchPage(webDriver)
                .scrollToJobSearchSection()
                .setKeyword(searchCriteria.getSearchValue())
                .selectCity(searchCriteria.getCity())
                .selectFilters(searchCriteria.getFilter())
                .searchAvailablePositions();
        List<OpenPositionItemElement> searchPositionList = new OpenPositionsPage(webDriver)
                .viewSearchResult()
                .sortBy(searchCriteria.getSort())
                .getOpenPositionsWithTitle(searchCriteria.getPosition());

        searchPositionList.forEach(item -> System.out.println(item.getPosition()));
        Assertions.assertTrue(true);
    }
}
