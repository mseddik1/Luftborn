package com.ebay.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PLP extends BasePage{
    private static final Logger log = LoggerFactory.getLogger(PLP.class);
    private final By resultTitle = By.xpath("//span[@class='su-styled-text primary default' and not(contains(text(),'Shop on eBay'))]");

    String test="//div[text()='Exterior Color']/following::div[1]//span[text()='White']/preceding::input[@type='checkbox'][1]";
    public PLP (WebDriver driver){
        super(driver);
    }

    public int countResults(){
        //Here i am not using driver.findElements().size(), because when i run cicd some of the elements are not displayed! so i want to capture only the displayed ones
        int count=0;
        for (WebElement el :driver.findElements(resultTitle) ){
            if(el.isDisplayed())count++;
        }
        return count;
    }

    @Step("Validating search results")
    public boolean validateResults(String searchKey){

        for(WebElement el : driver.findElements(resultTitle)){
            //i am skipping not displayed ones
            if (!el.isDisplayed())continue;
            log.info(el.getText().toLowerCase());
            if(!el.getText().toLowerCase().contains(searchKey.toLowerCase())){
                log.info(el.getText().toLowerCase());
                return false;
            }
        }
        return true;
    }


    @Step("Filtering search by {filterGroup} as group and {filterValue} as value")
    public PLP filterBy(String filterGroup, String filterValue){
        String groupXpath=String.format("//div[text()='%s']",filterGroup);
        String valueXpath = String.format("//span[text()='%s']",filterValue);
        String checkboxXpath = valueXpath+"/ancestor::div[@class='x-refine__select__svg' or @class='x-refine__multi-select']//input[@type='checkbox']";

        //Here i am implicitly checking that both values are present and them performing my action
        myWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(groupXpath)));
        myWait().until(ExpectedConditions.elementToBeClickable(By.xpath(valueXpath)));

        clickElement(By.xpath(valueXpath));

        return this;
    }
}
