package com.ebay.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class PLP extends BasePage{
    private static final Logger log = LoggerFactory.getLogger(PLP.class);
    private final By resultTitle = By.xpath("//span[@class='su-styled-text primary default' and not(contains(text(),'Shop on eBay'))]");

    public PLP (WebDriver driver){
        super(driver);
    }

    public int countResults(){
        return driver.findElements(resultTitle).size();
    }

    @Step("Validating search results")
    public boolean validateResults(String searchKey){
        String[] keywords = searchKey.toLowerCase().replaceAll("[^a-z0-9 ]", " ").trim().split("\\s+");


        for(WebElement el : driver.findElements(resultTitle)){
            String text = el.getText().toLowerCase().trim();
            boolean matchesAny = Arrays.stream(keywords).anyMatch(text::contains);
//            log.info(el.getText());
            if(!matchesAny){
                log.warn("Result does not match search key: '{}'", text);
                return false;
            }
        }
        return true;
    }


    @Step("Filtering search by {filterGroup} as group and {filterValue} as value")
    public PLP filterBy(String filterGroup, String filterValue){
        String groupXpath=String.format("//div[text()='%s']",filterGroup);
        String valueXpath = String.format("//span[text()='%s']",filterValue);
        String filterAppliedXpath = valueXpath + "//ancestor::div[@class='x-refine__multi-select']//span[text()='Filter Applied']";

        //Here i am implicitly checking that both values are present and them performing my action
        myWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(groupXpath)));
        myWait().until(ExpectedConditions.elementToBeClickable(By.xpath(valueXpath)));

        clickElement(By.xpath(valueXpath));

        boolean isPresent = !driver.findElements(By.xpath(filterAppliedXpath)).isEmpty();
        softly().assertTrue(isPresent, "Filter element should be present in the DOM");


        return this;
    }
}
