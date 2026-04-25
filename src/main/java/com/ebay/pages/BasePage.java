package com.ebay.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.asserts.SoftAssert;
import utils.ConfigManager;
import utils.SoftAssertManager;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;




    public BasePage(WebDriver driver){
        this.driver=driver;
    }

    protected void clickElement(By element){
        myWait().until(ExpectedConditions.elementToBeClickable(element)).click();
    }


    protected void typeElement(By element,String text){
        WebElement el = myWait().until(ExpectedConditions.visibilityOfElementLocated(element));
        el.clear();
        el.sendKeys(text);
    }


    protected Wait<WebDriver> myWait(){
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Long.parseLong(ConfigManager.get("explicitWait"))))
                .pollingEvery(Duration.ofMillis(Long.parseLong(ConfigManager.get("pollingInterval"))))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }





    //Note for me: here i am creating an object of the softmanager.softly() that i already created
    protected SoftAssert softly() {
        return SoftAssertManager.softly();
    }


}
