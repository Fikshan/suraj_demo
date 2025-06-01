// ContactDetailsErrorPage.java
package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import generic.Utility;

/**
 * Page Object representing the Contact Details Error Page.
 * Encapsulates the logic to verify and retrieve error messages displayed
 * when required fields in the Contact Details section are not filled.
 */
public class ContactDetailsErrorPage extends BasePage {

    // XPath locator for the generic error message on the Contact Details form
    private static final String ERROR_MESSAGE_LOCATOR = "//p[contains(@class,'field-error-message') and contains(text(),'We need a response for this field')]";
    
    // WebElement representing the error message
    @FindBy(xpath = ERROR_MESSAGE_LOCATOR)
    private WebElement errorMessageLocator;
    
    public ContactDetailsErrorPage(WebDriver driver){
        super(driver);
       }

    /**
     * Verifies whether the error message is displayed on the Contact Details page.
     * @return true if error message is visible, false otherwise.
     */
    public boolean isErrorMessageDisplayed() {
        //Utility.waitForElementVisible(wait, errorMessageLocator, "Error Message");
        wait.until(ExpectedConditions.elementToBeClickable(errorMessageLocator));
        return errorMessageLocator.isDisplayed();
    }

    /**
     * Retrieves the text of the error message displayed on the Contact Details page.
     * @return Error message text as a String.
     */
    public String getErrorMessage() {
        //Utility.waitForElementVisible(wait, errorMessageLocator, "Error Message");
        wait.until(ExpectedConditions.elementToBeClickable(errorMessageLocator));
        return errorMessageLocator.getText();
    }

}
