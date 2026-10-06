package com.salesforce.tests;

import com.salesforce.framework.base.BaseDriver;
import com.salesforce.framework.config.ConfigReader;
import com.salesforce.framework.pages.LoginPage;
import com.salesforce.framework.utils.WaitUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginValidTest extends BaseDriver {

    private LoginPage loginPage;

    @BeforeMethod
    public void navigateToLoginPage() {
        getDriver().get(ConfigReader.get("app.url"));
        loginPage = new LoginPage(getDriver());
    }

    @Test(description = "TC_LOGIN_001: Successful login with valid credentials")
    public void testSuccessfulLoginWithValidCredentials() {
        loginPage
                .enterUsername(ConfigReader.get("valid.username"))
                .submitUsername()
                .enterPassword(ConfigReader.get("valid.password"))
                .clickLogin();

        boolean redirected = WaitUtil.waitForUrlContains(getDriver(), "lightning")
                || WaitUtil.waitForUrlContains(getDriver(), "salesforce.com");

        Assert.assertTrue(redirected,
                "Expected post-login redirect but URL is still: " + getDriver().getCurrentUrl());
    }

    @Test(description = "TC_LOGIN_002: Remember Me checkbox is functional on login page")
    public void testRememberMeCheckboxIsSelectable() {
        loginPage.checkRememberMe();
        Assert.assertTrue(loginPage.isRememberMeChecked(),
                "Remember Me checkbox should be checked after interaction");
    }

    @Test(description = "TC_LOGIN_003: Login page loads with username field visible")
    public void testLoginPageLoadsSuccessfully() {
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Login page should display the username field on load");
    }
}
