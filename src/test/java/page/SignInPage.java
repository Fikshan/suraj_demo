package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

/**
 * SignInPage.java
 * 
 * This class handles the login functionality of the BGP portal.
 * It uses JavaScript execution for field inputs and button clicks,
 * extending the BasePage for shared utilities and driver access.
 */
public class SignInPage extends BasePage {
    // JavaScript executor for custom script executions
    private final JavascriptExecutor js;

    // WebElements using @FindBy for easier management
    @FindBy(xpath = "//div[@class='error']")
    private WebElement errMsg;

    @FindBy(xpath = "//div[@id='login-button']")
    private WebElement loginBTN;

    /**
     * Constructor to initialize the page with driver and JavaScript executor.
     * @param driver WebDriver instance from the test.
     */
    public SignInPage(WebDriver driver) {
        super(driver);
        this.js = (JavascriptExecutor) driver;  // Initialize js here
    }

    /**
     * Perform valid login by setting username and password and clicking submit.
     * @param username User's login ID.
     * @param password User's password.
     */
    public void login(String username, String password) {
        setField("signInFormUsername", username);
        setField("signInFormPassword", password);
        clickSubmit();
        clickLoginButton();
    }

    /**
     * Perform invalid login by entering credentials and clicking submit.
     * @param username Invalid username.
     * @param password Invalid password.
     */
    public void inValidlogin(String username, String password) {
        setField("signInFormUsername", username);
        setField("signInFormPassword", password);
        clickSubmit();
    }

    /**
     * Set a field value using JavaScript to avoid Selenium issues with visibility.
     * @param fieldId HTML ID of the input field.
     * @param value Value to be set.
     */
    private void setField(String fieldId, String value) {
        String script = "var el = document.getElementById('" + fieldId + "');" +
                "if(el){el.value=arguments[0];" +
                "el.dispatchEvent(new Event('input',{bubbles:true}));" +
                "el.dispatchEvent(new Event('change',{bubbles:true}));}";
        js.executeScript(script, value);
    }

    /**
     * Clicks the hidden submit button using JavaScript.
     */
    private void clickSubmit() {
        js.executeScript("var btn=document.getElementsByName('signInSubmitButton')[0];if(btn){btn.click();}");
    }

    /**
     * Clicks the visible login button after form submission.
     */
    private void clickLoginButton() {
        loginBTN.click();
    }

    /**
     * Checks if the login was successful by verifying the visibility of the login button.
     * @return true if login button is visible, false otherwise.
     */
    public boolean isLoginSuccessful() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(loginBTN)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Composite method to handle full login and navigation to the eligibility form.
     * @param username User's login ID.
     * @param password User's password.
     * @param configPath Path to config properties for manual login.
     */
    public void loginToEligibilityForm(String username, String password, String configPath) {
        login(username, password);
        new ManualLoginPage(driver).completeManualLoginAndNavigate(configPath);
    }

    /**
     * Verifies the error message after failed login attempts.
     * @param eErrMsg Expected error message.
     * @return true if the actual error message matches the expected.
     * @throws InterruptedException if thread sleep is interrupted.
     */
    public boolean verifyErrMsgIsDisplayed(String eErrMsg) throws InterruptedException {
        for (int i = 1; i <= 10; i++) {
            String aErrorMsg = (String) js.executeScript("return document.getElementById('loginErrorMessage').innerText");
            System.out.println("expected:" + aErrorMsg + ":i:" + i);
            System.out.println("Actual :" + aErrorMsg + ":");
            if (aErrorMsg.equalsIgnoreCase(eErrMsg)) {
                return true;
            }
            Thread.sleep(500);
        }
        return false;
    }
}
