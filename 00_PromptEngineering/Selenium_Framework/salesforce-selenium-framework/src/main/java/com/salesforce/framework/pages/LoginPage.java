package com.salesforce.framework.pages;

import com.salesforce.framework.utils.WaitUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private final WebDriver driver;

    // ── Page 1: username entry ─────────────────────────────────────────────────

    @FindBy(xpath = "//input[@id='username']")
    private WebElement usernameField;

    @FindBy(xpath = "//input[@id='Login']")
    private WebElement loginButton;

    @FindBy(xpath = "//input[@id='rememberUn']")
    private WebElement rememberMeCheckbox;

    // ── Page 2: password entry (rendered after username submit) ────────────────

    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordField;

    // ── Error banner (present on both pages after a failed attempt) ────────────

    @FindBy(xpath = "//div[@id='error']")
    private WebElement errorBanner;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public LoginPage enterUsername(String username) {
        WaitUtil.waitForVisibility(driver, usernameField);
        usernameField.clear();
        usernameField.sendKeys(username);
        return this;
    }

    public LoginPage submitUsername() {
        WaitUtil.waitForClickability(driver, loginButton);
        loginButton.click();
        return this;
    }

    public LoginPage enterPassword(String password) {
        WaitUtil.waitForVisibility(driver, passwordField);
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    public LoginPage clickLogin() {
        WaitUtil.waitForClickability(driver, loginButton);
        loginButton.click();
        return this;
    }

    public LoginPage checkRememberMe() {
        WaitUtil.waitForClickability(driver, rememberMeCheckbox);
        if (!rememberMeCheckbox.isSelected()) {
            rememberMeCheckbox.click();
        }
        return this;
    }

    public boolean isRememberMeChecked() {
        WaitUtil.waitForVisibility(driver, rememberMeCheckbox);
        return rememberMeCheckbox.isSelected();
    }

    public String getErrorMessageText() {
        WaitUtil.waitForVisibility(driver, errorBanner);
        return errorBanner.getText().trim();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            WaitUtil.waitForVisibility(driver, errorBanner);
            return errorBanner.isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public boolean isLoginPageDisplayed() {
        try {
            WaitUtil.waitForVisibility(driver, usernameField);
            return usernameField.isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
}
