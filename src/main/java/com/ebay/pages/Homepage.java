package com.ebay.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Homepage extends BasePage{
    private final By ebayLogo = By.id("ebayLogoTitle");
    private final By searchBar = By.id("gh-ac");
    private final By searchButton = By.id("gh-search-btn");

    public Homepage(WebDriver driver){
        super(driver);
    }


    public boolean isHomepage(){
        return driver.findElement(ebayLogo).isDisplayed();
    }


    public void searchForProduct(String product){
        typeElement(searchBar,product);
        clickElement(searchButton);
    }

}
