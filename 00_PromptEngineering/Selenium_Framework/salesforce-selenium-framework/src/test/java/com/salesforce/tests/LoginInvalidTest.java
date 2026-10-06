package com.salesforce.tests;

import com.salesforce.framework.base.BaseDriver;
import com.salesforce.framework.config.ConfigReader;
import com.salesforce.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginInvalidTest extends BaseDriver {

    private LoginPage loginPage;

    @BeforeMethod
    public void navigateToLoginPage() {
        getDriver().get(ConfigReader.get("app.url"));
        loginPage = new LoginPage(getDriver());
    }

    @Test(description = "TC_LOGIN_INV_001: Valid username + wrong password shows error")
    public void testLoginWithWrongPasswordShowsError() {
        loginPage
                .enterUsername(ConfigReader.get("valid.username"))
                .submitUsername()
                .enterPassword(ConfigReader.get("invalid.password"))
                .clickLogin();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed when password is incorrect");
        Assert.assertFalse(loginPage.getErrorMessageText().isEmpty(),
                "Error message text must not be empty on wrong password");
    }

    @Test(description = "TC_LOGIN_INV_002: Invalid username + invalid password shows error")
    public void testLoginWithInvalidCredentialsShowsError() {
        loginPage
                .enterUsername(ConfigReader.get("invalid.username"))
                .submitUsername()
                .enterPassword(ConfigReader.get("invalid.password"))
                .clickLogin();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed when both credentials are invalid");
    }

    @Test(description = "TC_LOGIN_INV_003: Empty username shows error without reaching password page")
    public void testLoginWithEmptyUsernameShowsError() {
        loginPage
                .enterUsername("")
                .submitUsername();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed when username is empty");
    }

    @Test(description = "TC_LOGIN_INV_004: Valid username + empty password shows error")
    public void testLoginWithValidUsernameEmptyPasswordShowsError() {
        loginPage
                .enterUsername(ConfigReader.get("valid.username"))
                .submitUsername()
                .enterPassword("")
                .clickLogin();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed when password is empty");
    }
}
