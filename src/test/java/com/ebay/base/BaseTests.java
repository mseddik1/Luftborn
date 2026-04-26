package com.ebay.base;

import com.ebay.pages.Homepage;
import com.fasterxml.jackson.databind.JsonNode;
import listeners.MyListeners;
import listeners.TestListeners;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import utils.ConfigManager;
import utils.DriverManager;
import utils.SoftAssertManager;
import utils.Utils;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Listeners(TestListeners.class)
public class BaseTests {

    protected static final JsonNode testDataFile = Utils.readAsJsonResource("testData/comTestData.json");
    private static final Logger log = LoggerFactory.getLogger(BaseTests.class);


    private String browser;
    private String env;

    protected Homepage homepage;


    @BeforeClass(alwaysRun = true )
    @Parameters({ "browser", "env" })
    public void initialize(@Optional("chrome") String browser,
                           @Optional("prod") String env) {
        this.browser = browser;
        this.env = env;

    }



    @BeforeMethod(alwaysRun = true)
    protected void Setup(Method m) {
        SoftAssertManager.reset();


        log.info("Initiating {} | Thread: {}", m.getName(), Thread.currentThread().threadId());


        WebDriver rawDriver= switch (browser.toLowerCase()) {
            case "chrome" -> new ChromeDriver(chromeOptions());
            case "firefox" ->  new FirefoxDriver(firefoxOptions());
            case "edge" ->  new EdgeDriver(edgeOptions());
            case "safari" ->  new SafariDriver();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
        WebDriver driver = new EventFiringDecorator(new MyListeners()).decorate(rawDriver);


        DriverManager.setDriver(driver);
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(ConfigManager.get("implicitWait"))));


        goToHomePage();

        homepage = new Homepage(DriverManager.getDriver());
    }



    @AfterMethod(alwaysRun = true)
    protected void tearDown(ITestResult result) {

        WebDriver driver = DriverManager.getDriver();



        try {
            if (result.getStatus() == ITestResult.FAILURE && driver != null) {



                long tid = Thread.currentThread().threadId();
                String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
                String fileName = ts + "_T" + tid + "_" + result.getName() + ".png";

                Path dir = Paths.get(System.getProperty("user.dir"), "test-output", "screenshots");
                Files.createDirectories(dir);

                File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Path dest = dir.resolve(fileName);

                Files.copy(src.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
                log.info("Screenshot saved: {}", dest);


            }

        } catch (Exception e) {
            log.error("Screenshot failed", e);
        } finally {
            try {


                if (driver != null) {
                    driver.quit();
                }


            } catch (Exception e) {
                log.error("Quit failed", e);
            } finally {
                DriverManager.unload();
                SoftAssertManager.unload();


            }
        }
    }


    protected void goToHomePage(){
        DriverManager.getDriver().get(ConfigManager.get(env+".ui.baseUrl"));
    }


    private ChromeOptions chromeOptions(){
        ChromeOptions options = new ChromeOptions();

        options.addArguments("start-maximized");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("autofill.profile_enabled", false);
        prefs.put("autofill.credit_card_enabled", false);
        prefs.put("useAutomationExtension", false);
        if (System.getenv("CI") != null) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--force-device-scale-factor=1");

        }
        options.setExperimentalOption("prefs", prefs);



        return options;
    }

    private FirefoxOptions firefoxOptions() {

        FirefoxProfile profile = new FirefoxProfile();

        profile.setPreference("signon.rememberSignons", false);     // disable password saving
        profile.setPreference("browser.formfill.enable", false);    // disable autofill

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);

        return options;
    }

    private EdgeOptions edgeOptions() {

        EdgeOptions options = new EdgeOptions();

        options.addArguments("start-maximized");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        options.setExperimentalOption("prefs", prefs);

        return options;
    }


    protected SoftAssert softly() {
        return SoftAssertManager.softly();
    }
}
