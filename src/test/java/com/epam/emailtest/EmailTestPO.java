package com.epam.emailtest;

import com.gmail.pageobject.*;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

/**
 * Created by Yahor local on 1/18/2016.
 */
public class EmailTestPO extends BaseTestPO {

    @Test(testName = "Login", description = "Gmail account logIN test")
    public void accountLoginTest() {
//        signInPage = new SignInPage(driver);
        inboxPage = new InboxPage(driver);
        signInPage.setEmail();
        signInPage.goToPasswPage();
        signInPage.setPasswd();
        signInPage.goToInboxPage();
        driver.get(InboxPage.HTML_GMAIL_URL);
        assertEquals(inboxPage.getComposeLabel(), "Compose Mail", "Wasn't login (asserted elements are not equal)");
    }

    @Test(testName = "Compose", description = "A new email compose and save as draft test", priority = 1)
    public void emailComposeTest() {
        composePage = new ComposePage(driver);
        inboxPage.goToComposePage();
        composePage.setTo("ivan.mailtest@mail.ru");
        composePage.setSubject("Test subject " + randomNum + " gmail");
        composePage.setBody("Test body " + randomNum + " test");
        composePage.saveDraft();
        assertEquals(composePage.getDraftSavedLabel(), "Your message has been saved.", "Draft not saved (asserted elements are not equal)");
    }

    @Test(testName = "inDraft", description = "An email can be found in Draft test", priority = 2)
    public void presentInDraftTest() {
        draftPage = new DraftPage(driver);
        composePage.goToDraftPage();
        assertEquals(draftPage.getDraftTitle(), "Test subject " + randomNum + " gmail - Test body " + randomNum + " test", "No saved draft mail in Draft (asserted elements are not equal)");
    }

    @Test(testName = "fieldsCheck", description = "Saved as draft email fields(To, Subject, Body) verification", priority = 3)
    public void draftEmailFieldsTest() {
        draftItemPage = new DraftItemPage(driver);
        draftPage.goToDraftItemPage();
        assertEquals(draftItemPage.getTo(), "ivan.mailtest@mail.ru", "To is incorrect (asserted elements are not equal)");
        assertEquals(draftItemPage.getSubject(), "Test subject " + randomNum + " gmail", "Subject is incorrect (asserted elements are not equal)");
        assertEquals(draftItemPage.getBody(), "Test body " + randomNum + " test", "Body is incorrect (asserted elements are not equal)");
    }

    @Test(testName = "Send", description = "Send email and check that it's present in Sent", priority = 4)
    public void emailSendTest() {
        sentPage = new SentPage(driver);
        draftItemPage.clickSendButton();
        inboxPage.goToSentPage();
        assertEquals(sentPage.getSentTitle(), "Test subject " + randomNum + " gmail - Test body " + randomNum + " test", "Mail wasn't sent (asserted elements are not equal)");
    }

    @Test(testName = "Logout", description = "Gmail account logOUT test", priority = 5)
    public void accountLogoutTest() {
        sentPage.clickLogoutLink();
        assertEquals(signInPage.getHeaderTitle(), "Sign in to continue to Gmail", "Wasn't logout (asserted elements are not equal)");
    }

}