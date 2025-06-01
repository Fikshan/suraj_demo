package script;

import org.testng.Assert;
import org.testng.annotations.Test;
import generic.BaseTest;
import generic.Utility;
import page.SignInPage;

public class InValidLoginTest extends BaseTest {

    @Test(priority = 1)
    public void testInvalidUsernameAndPassword() throws InterruptedException {
        // Fetch test data
        String un = Utility.getXLCellData(XL_PATH, "InValidLogin", 1, 0);
        String pw = Utility.getXLCellData(XL_PATH, "InValidLogin", 1, 1);
        String expectedError = Utility.getXLCellData(XL_PATH, "InValidLogin", 1, 2);

        test.info("Testing with invalid username and password");

        // Initialize SignInPage with driver from BaseTest
        SignInPage signIn = new SignInPage(driver);

        // Perform invalid login
        signIn.inValidlogin(un, pw);

        // Verify error message is displayed
        test.info("Expecting error message: " + expectedError);
        boolean result = signIn.verifyErrMsgIsDisplayed(expectedError);
        Assert.assertTrue(result, "Error message verification failed for invalid credentials.");

        test.pass("Verified error message for invalid username and password.");
    }

    @Test(priority = 2)
    public void testValidUsernameAndInvalidPassword() throws InterruptedException {
        // Fetch test data
        String un = Utility.getXLCellData(XL_PATH, "InValidLogin", 2, 0);
        String pw = Utility.getXLCellData(XL_PATH, "InValidLogin", 2, 1);
        String expectedError = Utility.getXLCellData(XL_PATH, "InValidLogin", 2, 2);

        test.info("Testing with valid username and invalid password");

        // Initialize SignInPage with driver from BaseTest
        SignInPage signIn = new SignInPage(driver);

        // Perform invalid login
        signIn.inValidlogin(un, pw);

        // Verify error message is displayed
        test.info("Expecting error message: " + expectedError);
        boolean result = signIn.verifyErrMsgIsDisplayed(expectedError);
        Assert.assertTrue(result, "Error message verification failed for valid username and invalid password.");

        test.pass("Verified error message for valid username and invalid password.");
    }
}
