package com.salesforce.framework.utils;

import com.salesforce.framework.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class WaitUtil {

    private static final long TIMEOUT_SECONDS =
            Long.parseLong(ConfigReader.get("explicit.wait.timeout"));

    private WaitUtil() {
    }

    public static WebElement waitForVisibility(WebDriver driver, WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForClickability(WebDriver driver, WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static boolean waitForUrlContains(WebDriver driver, String fragment) {
        return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS))
                .until(ExpectedConditions.urlContains(fragment));
    }
}
