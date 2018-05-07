package com.epam.careers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverFactory {
    public static WebDriver getWebDriver() throws Exception {
        String browserName = System.getProperty("browserName");
        if(browserName == null || browserName.isEmpty()) {
            return new ChromeDriver();
        }
        switch (browserName) {
            case "chrome":
                return new ChromeDriver();
            case "firefox":
                return new FirefoxDriver();
                default:
                    throw new Exception(String.format("Browser with name '%s' doesn't exist", browserName));
        }
    }
}
