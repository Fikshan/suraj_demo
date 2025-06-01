package script;

import generic.BaseTest;
import generic.Utility;
import org.testng.Assert;
import org.testng.annotations.Test;

import page.ContactDetailsErrorPage;
import page.FormActionsPage;
import page.ManualLoginPage;
import page.SignInPage;

public class EndToEndContactDetailsErrorValidationTest extends BaseTest {

    @Test
    public void testContactDetailsErrorMessage() throws InterruptedException {
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
         
        // Step 2: Navigate to Contact Details
        FormActionsPage formActions = new FormActionsPage(driver);
        //formActions.navigateToContactDetailsSection(wait);
        formActions.clickNext();   
        test.info("Navigated to Contact Details section.");
        formActions.selectAllCheckBoxes();
        formActions.uncheckAllSelectedCheckBoxes();  // Uncheck the checkboxes
        test.info("All selected checkboxes are unchecked.");

        // Step 4: Validate Error Message
        ContactDetailsErrorPage contactPage = new ContactDetailsErrorPage(driver);
        Assert.assertTrue(contactPage.isErrorMessageDisplayed(), "Error message not displayed!");
        String actualError = contactPage.getErrorMessage();
        String expectedError = "We need a response for this field";
        Assert.assertTrue(actualError.contains(expectedError), "Error message text does not match!");
        test.pass("Error message validation successful.");
    }
}