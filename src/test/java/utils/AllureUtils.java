package utils;




import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;



public final class AllureUtils {

    private AllureUtils() {

    }

    @Attachment(value = "{name}", type = "image/png")
    public static byte[] attachScreenshot(String name) {
        WebDriver driver = DriverManager.getDriver();

        if (driver == null) {
            return new byte[0];
        }
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{name}", type = "text/plain")
    public static String attachText(String name, String content) {
        return content == null ? "" : content;
    }

    @Attachment(value = "{name}", type = "application/json")
    public static String attachJson(String name, String content) {
        return content == null ? "{}" : content;
    }

    @Attachment(value = "{name}", type = "text/html")
    public static String attachHtml(String name, String content) {
        return content == null ? "" : content;
    }
}
