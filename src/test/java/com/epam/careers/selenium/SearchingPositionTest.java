package com.epam.careers.selenium;

import com.epam.careers.WebDriverFactory;
import com.epam.careers.bo.xml.SearchCriteria;
import com.epam.careers.data.XmlDataProvider;
import com.epam.careers.elements.OpenPositionItemElement;
import com.epam.careers.extensions.TestsExtension;
import com.epam.careers.pages.OpenPositionsPage;
import com.epam.careers.pages.SearchPage;
import com.epam.careers.utils.ScreenshotUtil;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.openqa.selenium.WebDriver;

import java.util.List;

@ExtendWith(TestsExtension.class)
public class SearchingPositionTest {
    private WebDriver webDriver;

    @BeforeEach
    void setup() throws Exception {
        webDriver = WebDriverFactory.getWebDriver();
    }

    @AfterEach
    void teardown() {
        webDriver.quit();
    }

    @ParameterizedTest
    @DisplayName("Search for positions by criteria and count specific results")
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

        val fileName = String.format("screenshots/%s_%s.png", searchCriteria.getPosition(), searchCriteria.getCity());
        ScreenshotUtil.createAndSaveSnapshot(webDriver, fileName);

        Assertions.assertNotNull(searchPositionList);
    }
}
