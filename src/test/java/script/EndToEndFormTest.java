package script;

import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import generic.BaseTest;
import generic.Utility;
import page.FormActionsPage;
import page.ManualLoginPage;
import page.SignInPage;

public class EndToEndFormTest extends BaseTest {

    @Test(priority = 1)
    public void testValidLoginAndCompleteForm() throws InterruptedException {
    	String username = Utility.getXLCellData(XL_PATH, "ValidLogin", 1, 0);
        String password = Utility.getXLCellData(XL_PATH, "ValidLogin", 1, 1);

        test.info("Starting End-to-End Form Test...");

        // Step 1: Perform login and manual navigation to eligibility form
        SignInPage signInPage = new SignInPage(driver);
        signInPage.login(username, password);
        test.info("Login completed.");

        ManualLoginPage manualPage = new ManualLoginPage(driver);
        manualPage.verifyHeaders();
        manualPage.fillManualLoginFields(CONFIG_PATH);
        manualPage.completeManualLoginAndNavigate(CONFIG_PATH);
        test.info("Manual login completed.");

        // Step 2: Now on FormActionsPage
        FormActionsPage form = new FormActionsPage(driver);

        Assert.assertTrue(driver.getCurrentUrl().contains("/form/eligibility"), "Did not reach the Eligibility form page.");
        test.pass("Eligibility form page loaded.");
        
        form.completeEligibilitySection();
        form.completeContactDetailsSection(new String[]{"name","title","12345678","abc@gmail.com","xyz@gmail.com"});
        form.completeProposalSection(new String[]{"Project Title "}, new String[]{"details1","details2"});
        form.completeBusinessImpactSection(
        		    new String[]{"100","100","100","100","100","100","100","100"},
        		    new String[]{"details1","details2"});
        form.enterNewItemInCostSection("details1", "1", "100");
        form.clickSave();
        form.clickNext();
        form.completeReviewSection();
        form.enterSubmitButton();
        test.pass("Clicked declaration checkbox and submit button.");   
//        //forSubmissionSuccess
        WebElement confirmationMessage = form.getConfirmationMessage(wait);
        Assert.assertTrue(confirmationMessage.isDisplayed(), "Application submission confirmation not displayed.");
        test.pass("Application submitted successfully. Confirmation message verified.");
        }
}
