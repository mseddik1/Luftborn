package listeners;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.WebDriverListener;


public class MyListeners implements WebDriverListener {

//   @Override
//   public void beforeClick(WebElement element) {
//       try {
//           String elementText = element.getText();
//           if (elementText == null || elementText.isEmpty()) {
//               System.out.println("Locating  #Name! ...");
//           } else {
//               System.out.println("Locating " + elementText+" ...");
//           }
//       } catch (org.openqa.selenium.StaleElementReferenceException e) {
//           System.out.println("Still locating " + element.getText());
//       }
//   }
//   @Override
//   public void afterClick(WebElement element){
//       try {
//           String elementText = element.getText();
//           if (elementText == null || elementText.isEmpty()) {
//               System.out.println("Clicked on #Name!");
//           } else {
//               System.out.println("Clicked on " + elementText);
//           }
//       } catch (org.openqa.selenium.StaleElementReferenceException e) {
//           System.out.println("Clicking on Element");
//       } catch (org.openqa.selenium.NoSuchElementException e) {
//           System.out.println("No such element found. Unable to get the element's text.");
//       } catch (Exception e) {
//           System.out.println("Error while logging afterClick: " + e.getMessage());
//       }
//   }
    @Override
    public void afterQuit(WebDriver driver) {
        System.out.println("Quitting");
    }


}
