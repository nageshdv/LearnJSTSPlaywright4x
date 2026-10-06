package com.salesforce.framework.base;

import com.salesforce.framework.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.nio.file.Paths;

public class BaseDriver {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    @BeforeTest
    public void initDriver() {
        String browser = ConfigReader.get("browser").trim().toLowerCase();
        WebDriver driver;

        switch (browser) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver",
                        Paths.get("drivers", "geckodriver.exe").toAbsolutePath().toString());
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver",
                        Paths.get("drivers", "chromedriver.exe").toAbsolutePath().toString());
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--start-maximized");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "edge":
            default:
                System.setProperty("webdriver.edge.driver",
                        Paths.get("drivers", "msedgedriver.exe").toAbsolutePath().toString());
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--disable-notifications");
                edgeOptions.addArguments("--start-maximized");
                driver = new EdgeDriver(edgeOptions);
                break;
        }

        driver.manage().deleteAllCookies();
        driverThreadLocal.set(driver);
        driver.get(ConfigReader.get("app.url"));
    }

    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    @AfterTest
    public void tearDown() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}
