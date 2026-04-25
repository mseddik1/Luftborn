package com.ebay.tests;


import com.ebay.base.BaseTests;
import com.ebay.pages.PLP;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import utils.AllureUtils;

public class SearchTests extends BaseTests {
    private static final Logger log = LoggerFactory.getLogger(SearchTests.class);

    @Test
    public void searchFilterTest(){
        String searchKey = "mazda mx-5";
        String filterGroup = "Transmission";
        String filterValue = "Manual";
        softly().assertTrue(homepage.isHomepage());

        PLP plp = homepage.searchForProduct(searchKey);

        log.info("Number of search results: {}", plp.countResults());
        Allure.step("Validating the search results",()->{
            softly().assertTrue(plp.validateResults(searchKey), "Results should contain the search key!");
            AllureUtils.attachScreenshot("Search results validated!");
        });

        Allure.step("Applying filters",()->{
            plp.filterBy(filterGroup, filterValue);
            AllureUtils.attachScreenshot("Filters applied!");
        });


        softly().assertAll();

    }
}
