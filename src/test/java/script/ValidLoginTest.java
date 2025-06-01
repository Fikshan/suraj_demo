package script;

import org.testng.Assert;
import org.testng.annotations.Test;
import generic.BaseTest;
import generic.Utility;
import page.SignInPage;
import page.ManualLoginPage;

public class ValidLoginTest extends BaseTest {

    @Test(priority = 1)
    public void testLoginAndReachEligibilityForm() {
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
    }
}
