package page;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import generic.Utility;

/**
 * ErrorPage.java
 * 
 * This class represents the generic Error Page in the application.
 * It provides methods to:
 * - Retrieve the count of validation errors
 * - Detect and retrieve warning messages
 * - Handle FAQ links for additional information
 * It supports clear visibility checks and interactions with error/warning elements.
 */
public class ErrorPage extends BasePage {


    // XPaths for locating error-related elements
    private static final String ERROR_COUNT_XPATH = "//span[contains(@class,'label-error')]";
    private static final String WARNING_XPATH = "//span[contains(text(),'The applicant may not meet the eligibility criteria for this grant')]";
    private static final String FAQ_LINK_XPATH = "//a[contains(text(),'FAQ')]";

    // WebElement for the errorcount
    @FindBy(xpath = ERROR_COUNT_XPATH)
    private WebElement errorCount;

    @FindBy(xpath = WARNING_XPATH)
    private WebElement warningMessageElement;

    @FindBy(xpath = FAQ_LINK_XPATH)
    private WebElement faqLinkElement;
    
    public ErrorPage(WebDriver driver) {
        super(driver);
    }


    /**
     * Retrieves the total number of validation errors on the page.
     * @return Number of error indicators (badges) found.
     */
    public int getErrorCount() {
        List<WebElement> errorIndicators = driver.findElements(By.xpath(ERROR_COUNT_XPATH));
        return errorIndicators.size();
    }

    /**
     * Checks if the specific warning message is displayed.
     * @return true if warning is present, false otherwise.
     */
    public boolean isWarningDisplayed() {
        List<WebElement> warnings = driver.findElements(By.xpath(WARNING_XPATH));
        if (!warnings.isEmpty()) {
            //Utility.waitForElementVisible(wait, warnings.get(0), "Warning Message");
        	wait.until(ExpectedConditions.elementToBeClickable(By.xpath(WARNING_XPATH)));
            return true;
        }
        return false;
    }

    /**
     * Retrieves the warning message text if present.
     * @return Warning message text as a String.
     */
    public String getWarningMessage() {
        //Utility.waitForElementVisible(wait, driver.findElement(By.xpath(WARNING_XPATH)), "Warning Message");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(WARNING_XPATH)));
        return driver.findElement(By.xpath(WARNING_XPATH)).getText();
    }

    /**
     * Retrieves the href link of the FAQ link associated with the warning.
     * @return The URL of the FAQ link, or an empty string if not found.
     */
    public String getFAQLink() {
    	if (faqLinkElement.isDisplayed()) {
            wait.until(ExpectedConditions.visibilityOf(faqLinkElement));
            return faqLinkElement.getAttribute("href");
        }
        return "";
    }

    /**
     * Clicks the FAQ link if available.
     * Waits until the link is clickable before clicking.
     */
    public void clickFAQLink() {
    	if (faqLinkElement.isDisplayed()) {
            wait.until(ExpectedConditions.elementToBeClickable(faqLinkElement));
            faqLinkElement.click();
        }
    }
}
