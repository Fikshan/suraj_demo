package script;

import org.testng.Assert;
import org.testng.annotations.Test;
import generic.BaseTest;
import generic.Utility;
import page.FormActionsPage;
import page.ManualLoginPage;
import page.SignInPage;
import page.ErrorPage;

public class EndToEndErrorValidationTest extends BaseTest {

    @Test
    public void testEndToEndErrorValidation() throws InterruptedException {
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

        FormActionsPage form = new FormActionsPage(driver);

        // Click Next 5 times (to move through sections)
        for (int i = 0; i < 5; i++) {
            form.clickNext();
            Thread.sleep(1000);
        }

        // Click Review button
        form.clickReview();
        Thread.sleep(5000);

        // Get error count
        ErrorPage errorPage = new ErrorPage(driver);
        int errorCount = errorPage.getErrorCount();
        test.info("Total error count: " + errorCount);

        // Validate there are errors
        Assert.assertTrue(errorCount > 0, "Expected error indicators are not present");

        test.pass("Error indicators verified successfully");
    }
}
