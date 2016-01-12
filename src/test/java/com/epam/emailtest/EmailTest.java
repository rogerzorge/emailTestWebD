package com.epam.emailtest;

/**
 * Created by Yahor local on 1/9/2016.
 */

import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;

import static java.lang.Math.abs;
import static java.lang.Math.random;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class EmailTest extends BaseTest {

    @Test(testName = "Login", description = "Gmail account logIN test")
    public void accountLoginTest() {
        driver.get("http://gmail.com");
        isElementPresent(By.id("Email"));
        driver.findElement(By.id("Email")).clear();
        driver.findElement(By.id("Email")).sendKeys("ivan.mailfortest");
        isElementPresent(By.id("next"));
        driver.findElement(By.id("next")).click();
        isElementPresent(By.id("Passwd"));
        driver.findElement(By.id("Passwd")).clear();
        driver.findElement(By.id("Passwd")).sendKeys("Zaq1!Xsw2@");
        isElementPresent(By.id("signIn"));
        driver.findElement(By.id("signIn")).click();
    }

    @Test(testName = "Compose", description = "A new email compose and save as draft test", priority = 1)
    public void emailComposeTest() {
        driver.get("https://mail.google.com/mail/?ui=html&zy=h");
        isElementPresent(By.linkText("Compose Mail"));
        driver.findElement(By.linkText("Compose Mail")).click();
        isElementPresent(By.id("to"));
        driver.findElement(By.id("to")).clear();
        driver.findElement(By.id("to")).sendKeys("ivan.mailtest@mail.ru");
        isElementPresent(By.name("subject"));
        driver.findElement(By.name("subject")).clear();
        driver.findElement(By.name("subject")).sendKeys("Test subject " + randomNum + " gmail");
        isElementPresent(By.name("body"));
        driver.findElement(By.name("body")).clear();
        driver.findElement(By.name("body")).sendKeys("Test body " + randomNum + " test");
        isElementPresent(By.xpath("//input[@type='submit' and @value='Save Draft']"));
        driver.findElement(By.xpath("//input[@type='submit' and @value='Save Draft']")).click();
    }

    @Test(testName = "inDraft", description = "An email can be found in Draft test", priority = 2)
    public void presentInDraftTest() {
        isElementPresent(By.xpath("//a[@href='?&s=d']"));
        driver.findElement(By.xpath("//a[@href='?&s=d']")).click();
        assertEquals(driver.findElement(By.cssSelector("span.ts")).getText(), "Test subject " + randomNum + " gmail - Test body " + randomNum + " test");
    }

    @Test(testName = "fieldsCheck", description = "Saved as draft email fields(To, Subject, Body) verification", priority = 3)
    public void draftEmailFieldsTest() {
        isElementPresent(By.cssSelector("span.ts"));
        driver.findElement(By.cssSelector("span.ts")).click();
        assertEquals(driver.findElement(By.id("to")).getText(), "ivan.mailtest@mail.ru");
        assertEquals(driver.findElement(By.name("subject")).getAttribute("value"), "Test subject " + randomNum + " gmail");
        assertEquals(driver.findElement(By.name("body")).getText(), "Test body " + randomNum + " test");
    }

    @Test(testName = "Send", description = "Send email and check that it's present in Sent", priority = 4)
    public void emailSendTest() {
        isElementPresent(By.name("nvp_bu_send"));
        driver.findElement(By.name("nvp_bu_send")).click();
        isElementPresent(By.linkText("Sent Mail"));
        driver.findElement(By.linkText("Sent Mail")).click();
        assertEquals(driver.findElement(By.cssSelector("span.ts")).getText(), "Test subject " + randomNum + " gmail - Test body " + randomNum + " test");
    }

    @Test(testName = "Logout", description = "Gmail account logOUT test", priority = 5)
    public void accountLogoutTest() {
        isElementPresent(By.linkText("Sign out"));
        driver.findElement(By.linkText("Sign out")).click();
        assertEquals(driver.findElement(By.cssSelector("h2.hidden-small")).getText(), "Sign in to continue to Gmail");
    }

}
