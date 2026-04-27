package api.tests;

import api.base.BaseTests;
import apis.models.User;
import apis.models.UserCreateResponse;
import apis.models.UserPageResponse;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import utils.AllureUtils;
import utils.RetryAnalyzer;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.lessThan;
import static utils.SoftAssertManager.softly;

public class UsersTests extends BaseTests {
    private static final Logger log = LoggerFactory.getLogger(UsersTests.class);

    @Test(
            groups = {"smoke", "regression"},
            retryAnalyzer = RetryAnalyzer.class,
            description = "User should be able to retrieve users successfully"
    )
    @Story("Get Users Paginated")
    @Severity(SeverityLevel.CRITICAL)
    public void getUsersPaginated(){
        int page = 2;
        Response response = userService.getAllUsers(page);


        log.info("Time: {}",response.getTime());




        response .then()
                .statusCode(200)
                .time(lessThan(1000L))
                .body(matchesJsonSchemaInClasspath("schemas/users-schema.json"));

        UserPageResponse userPageResponse = response.as(UserPageResponse.class);
        softly().assertNotNull(userPageResponse.getData());
        softly().assertFalse(userPageResponse.getData().isEmpty(), "Users list should not be empty");


        int actualPageNumber = userPageResponse.getPage();
        int actualPageSize = userPageResponse.getData().size();

        softly().assertEquals(actualPageNumber,page,"Error in page number!");
        softly().assertEquals(actualPageSize,6,"Error in page size!");
        for(User user : userPageResponse.getData()){
            softly().assertNotNull(user.getId(),"ID should not be null!");
            softly().assertTrue(user.getId()>0,"ID should be a valid positive number!");
        }

        AllureUtils.attachJson("Json Response: ", response.body().asPrettyString());
        softly().assertAll();

    }

    @Test(
            groups = {"smoke", "regression"},
            retryAnalyzer = RetryAnalyzer.class,
            description = "User should be able to create user"
    )
    @Story("Create User")
    @Severity(SeverityLevel.CRITICAL)
    public void createUser(){
        Response response = userService.createUser();


        log.info("Time: {}",response.getTime());




        response .then()
                .statusCode(201)
                .time(lessThan(1000L))
                .body(matchesJsonSchemaInClasspath("schemas/createUser-schema.json"));

        UserCreateResponse userCreateResponse = response.as(UserCreateResponse.class);
        softly().assertNotNull(userCreateResponse.getId());
        softly().assertFalse(userCreateResponse.getId().isEmpty(), "ID should not be empty");
        softly().assertNotNull(userCreateResponse.getCreatedAt(),"Creation data should not be null!");
        softly().assertFalse(userCreateResponse.getCreatedAt().isEmpty(),"Creation date should not be empty!");


        log.info(userCreateResponse.getId());




        AllureUtils.attachJson("Json Response: ", response.body().asPrettyString());
        softly().assertAll();

    }
}
