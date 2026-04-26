package com.ebay.tests;


import com.ebay.base.BaseTests;
import com.ebay.pages.PLP;
import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.Allure;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import utils.AllureUtils;
import utils.RetryAnalyzer;

public class SearchTests extends BaseTests {
    private static final Logger log = LoggerFactory.getLogger(SearchTests.class);
    private final JsonNode searchNode = testDataFile.path("search");

    @Test(groups = {"smoke"},retryAnalyzer = RetryAnalyzer.class,description = "User should be able to apply filters")
    @Story("Search")
    @Severity(SeverityLevel.CRITICAL)
    public void searchFilterTest(){
        JsonNode validSearch = searchNode.path("validSearch");
        String searchKey = validSearch.get("searchKey").asText();
        String filterGroup = validSearch.get("filterGroup").asText();
        String filterValue = validSearch.get("filterValue").asText();
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
