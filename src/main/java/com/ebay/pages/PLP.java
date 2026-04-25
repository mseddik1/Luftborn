package com.ebay.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PLP extends BasePage{
    private final By ebayLogo = By.id("ebayLogoTitle");

    public PLP (WebDriver driver){
        super(driver);
    }
}
