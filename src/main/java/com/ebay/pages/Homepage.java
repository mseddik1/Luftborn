package com.ebay.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Homepage extends BasePage{
    private final By ebayLogo = By.id("ebayLogoTitle");
    private final By searchBar = By.id("gh-ac");
    private final By searchButton = By.id("gh-search-btn");

    public Homepage(WebDriver driver){
        super(driver);
    }

    @Step("Verify whether user is on the  homepage")
    public boolean isHomepage(){
        return driver.findElement(ebayLogo).isDisplayed();
    }

    @Step("Search for: {searchKey} in search bar")
    public PLP searchForProduct(String searchKey){
        typeElement(searchBar,searchKey);
        clickElement(searchButton);
        return new PLP(driver);
    }

}
