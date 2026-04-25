package com.ebay.tests;


import com.ebay.base.BaseTests;
import org.testng.annotations.Test;

public class Tests extends BaseTests {
    @Test
    public void test(){
        String product = "mazda mx-5";
        softly().assertTrue(homepage.isHomepage());

        homepage.searchForProduct(product);

    }
}
