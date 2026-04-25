package utils;

import com.beust.jcommander.internal.Nullable;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import org.apache.commons.io.FileUtils;


import org.aspectj.util.FileUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VisualTestUtil {
    private WebDriver driver;
    private static final Logger log = LoggerFactory.getLogger(VisualTestUtil.class);

    private static final String BASE_PATH = "src/test/";


    public VisualTestUtil(WebDriver driver){

        this.driver= driver;


    }






    /**
     * This function takes a screenshot for WebDriver and AppiumDriver
     * If there is a By locator, function will take a screenshot of the element only.
     * If no locator sent, function will take screenshot for the whole view/page
     * @param locator
     */
    public void takeScreenshot(@Nullable By locator, String StepName) {
        File destFile;
        File screenshotFile;
        String methodName = getTestMethodName("com.sauceDemo.tests");
        String className = getTestClassName("com.sauceDemo.tests");

        destFile = new File( "test-output/visual/actual/"+className+"/" +StepName+"_"+ methodName + ".png");


        //I will be using this method inside the test method, so thats why i added the index [2]
        //So it will be takeScreenshot() inside the testMethod() so the test method index now is 2

        if(locator != null) {
            screenshotFile = driver.findElement(locator).getScreenshotAs(OutputType.FILE);
        }else{
            screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        }


        try {
            FileUtils.copyFile(screenshotFile, destFile);
            log.info("Screenshot saved!");

        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot", e);
        }
    }



    /**
     * Compares the actual screenshot to the baseline and optionally saves a diff image.
     */
    public boolean compareScreenshot(@Nullable String baselineImageName, String ActualStepName) {
        String methodName = getTestMethodName("com.cms.cms_api_app.tests");
        String className = getTestClassName("com.cms.cms_api_app.tests");
        String baselineFileBase;
        String actualFileBase;
        String diffFileBase;


        baselineFileBase = BASE_PATH + "web/baseline/"+className+"/" ;
        actualFileBase = BASE_PATH + "web/actual/"+className+"/" ;
        diffFileBase = BASE_PATH + "web/diff/"+className+"/" ;

        File baselineFile;
        if(baselineImageName!=null){
            baselineFile= new File(baselineFileBase.replace(className+"/","") + baselineImageName + ".png");
        }else {
            baselineFile = new File(baselineFileBase + ActualStepName +"_"+ methodName + ".png");
        }
        File actualFile=new File(actualFileBase + ActualStepName +"_"+ methodName + ".png");
        File diffFile=new File(diffFileBase + ActualStepName +"_"+ methodName + ".png");



        try {
            BufferedImage expectedImage = ImageIO.read(baselineFile);
            BufferedImage actualImage = ImageIO.read(actualFile);



            ImageComparison comparison = new ImageComparison(expectedImage, actualImage);
            comparison.setThreshold(10);
            ImageComparisonResult result = comparison.compareImages();


            if (result.getImageComparisonState() != ImageComparisonState.MATCH) {
                System.out.println("Visual mismatch detected. See diff file!❌");
                generateCombinedBaselineAndActual(baselineFile, actualFile, diffFile);
//                throw new AssertionError("Visual regression detected in screenshot: " + methodName);
                return false;

            } else {
                System.out.println("Screenshot matched baseline!✅");
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to compare screenshots", e);

        }
    }


    public static void generateCombinedBaselineAndActual(File baselineFile, File actualFile, File outputFile) {
        try {
            BufferedImage baselineImage = ImageIO.read(baselineFile);
            BufferedImage actualImage = ImageIO.read(actualFile);

            int width = baselineImage.getWidth() + actualImage.getWidth();
            int height = Math.max(baselineImage.getHeight(), actualImage.getHeight());

            BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = combined.createGraphics();

            // Draw both images side by side
            g.drawImage(baselineImage, 0, 0, null);
            g.drawImage(actualImage, baselineImage.getWidth(), 0, null);
            g.dispose();

            // Save to the same "diff" file
            outputFile.getParentFile().mkdirs();
            ImageIO.write(combined, "png", outputFile);
            log.info("Combined baseline + actual saved as diff image!");


        } catch (IOException e) {
            throw new RuntimeException("Failed to create combined diff image", e);
        }
    }



    public void saveScreenshotInBaseline(By locator,String StepName) {
        File destFile;
        String methodName = getTestMethodName("com.sauceDemo.tests");
        String className = getTestClassName("com.sauceDemo.tests");

        destFile = new File(BASE_PATH + "/resources/visualBaseline/" +className+"/"+StepName+"_"+ methodName + ".png");
//        destFile.getParentFile().mkdirs();



        File screenshotFile =driver.findElement(locator).getScreenshotAs(OutputType.FILE);


        try {
            FileUtils.copyFile(screenshotFile, destFile);
            log.info("Screenshot saved to baseline: {}",destFile.getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot to baseline!", e);
        }
    }


    public boolean takeAndCompareScreenshot(@Nullable By locator,String StepName,@Nullable String baselineImageName){
        String methodName = getTestMethodName("com.sauceDemo.tests");
        String className = getTestClassName("com.sauceDemo.tests");
        String baselineFileBase;
        String actualFileBase;
        String diffFileBase;
//        System.out.println("Method Name: "+methodName);
//        System.out.println("Class Name: "+className);

        takeScreenshot(locator,StepName);

        baselineFileBase = BASE_PATH + "/resources/visualBaseline/"+className+"/" ;
        actualFileBase =  "test-output/visual/actual/"+className+"/" ;
        diffFileBase = "test-output/visual/diff/"+className+"/" ;

        File baselineFile;
        if(baselineImageName!=null){
            baselineFile= new File(baselineFileBase.replace(className+"/","") + baselineImageName + ".png");
        }else {
            baselineFile = new File(baselineFileBase + StepName + "_" + methodName + ".png");
            if (!baselineFile.exists() || !baselineFile.canRead()) {
                log.info("No Baseline image, updating Baseline!");

                saveScreenshotInBaseline(locator, StepName);
            }
        }
        File actualFile=new File(actualFileBase + StepName +"_"+ methodName + ".png");
        File diffFile=new File(diffFileBase + StepName +"_"+ methodName + ".png");




        try {
            BufferedImage expected = ImageIO.read(baselineFile);
            BufferedImage actual   = ImageIO.read(actualFile);

            ImageComparison comparison = new ImageComparison(expected, actual);
            comparison.setThreshold(3);

            ImageComparisonResult result = comparison.compareImages();

            if (result.getImageComparisonState() == ImageComparisonState.MATCH) {
                log.info("Screenshot matched baseline! ✅");

                return true;
            }else{
                log.info("Visual mismatch detected. See diff file!❌");
                generateCombinedBaselineAndActual(baselineFile, actualFile, diffFile);
                return false;
            }


        } catch (IOException e) {
            throw new java.io.UncheckedIOException("Failed to compare screenshots", e);
        }
    }


    /**
     * Gets the method name of the test by specifying the class name where the tests are located
     * @return
     */
    private String getTestMethodName(String classPrefix) {

        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {

            String className = element.getClassName();
            String methodName = element.getMethodName();

            //I am adding those here to ignore the lambda expressions (()->) that i use in Allure.step
            if (className.startsWith(classPrefix)
                    && !methodName.startsWith("lambda$")
                    && !methodName.contains("$")) {

                return methodName;
            }
        }

        return null;
    }

    private String getTestClassName(String classPrefix) {

        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {

            String fullClassName = element.getClassName();

            if (fullClassName.startsWith(classPrefix)) {
                return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
            }
        }

        return null;
    }







}
