package script;

import org.testng.Assert;
import org.testng.annotations.Test;
import generic.BaseTest;
import generic.Utility;
import page.FormActionsPage;
import page.ManualLoginPage;
import page.SignInPage;
import page.ErrorPage;



public class EndToEndWarningValidationTest extends BaseTest {

    @Test
    public void testWarningMessageAndFAQLink() throws InterruptedException {
    	 String username = Utility.getXLCellData(XL_PATH, "ValidLogin", 1, 0);
         String password = Utility.getXLCellData(XL_PATH, "ValidLogin", 1, 1);

         // Step 1: Login using SignInPage
         SignInPage signInPage = new SignInPage(driver);
         signInPage.login(username, password);

         // Step 2: Fill and Submit Manual Login Page
         ManualLoginPage manualPage = new ManualLoginPage(driver);
         manualPage.verifyHeaders();
         manualPage.fillManualLoginFields(CONFIG_PATH);
         manualPage.completeManualLoginAndNavigate(CONFIG_PATH);

         // Step 3: Assert that user is on Eligibility Page
         Assert.assertTrue(driver.getCurrentUrl().contains("/form/eligibility"), "Did not reach the Eligibility form page.");
         test.pass("Login successful and navigated to Eligibility form.");

        // Select 'No' options for all questions
        FormActionsPage formActions = new FormActionsPage(driver);
        formActions.selectAllNo();
        test.info("Selected 'No' options for all eligibility questions");

        // Initialize ErrorPage
        ErrorPage errorPage = new ErrorPage(driver);

        // Validate warning message
        Assert.assertTrue(errorPage.isWarningDisplayed(), "Warning not displayed!");
        String actualWarning = errorPage.getWarningMessage();
        
        String expectedWarning = Utility.getXLCellData(XL_PATH, "ValidLogin", 2, 0);
        Assert.assertTrue(actualWarning.contains("The applicant may not meet the eligibility criteria"), "Warning message mismatch!");
        test.pass("Warning message verified successfully");

        // Validate FAQ link
        String expectedFAQUrl = Utility.getProperty(CONFIG_PATH, "FAQ_URL");
        //String expectedFAQUrl = "https://www.gobusiness.gov.sg/business-grants-portal-faq/";
        String actualFAQUrl = errorPage.getFAQLink();
        Assert.assertEquals(actualFAQUrl, expectedFAQUrl, "FAQ link URL mismatch");
        test.pass("FAQ link URL verified successfully");

        // Click FAQ link, switch tab, validate URL, then return
        String originalWindow = driver.getWindowHandle();
        errorPage.clickFAQLink();
        Thread.sleep(2000);

        // Switch to new tab
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        String actualTabUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualTabUrl, expectedFAQUrl, "Navigated to incorrect FAQ URL");
        test.pass("Successfully navigated to FAQ page in new tab");

        // Close new tab and switch back
        driver.close();
        driver.switchTo().window(originalWindow);
        test.info("Returned to original session");
    }
}
