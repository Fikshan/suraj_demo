package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import generic.Utility;

public class ManualLoginPage extends BasePage{

	private static final By GET_NEW_GRANT = By.xpath("//h4[contains(text(),'Get new grant')]");
    private static final By IT_BUTTON = By.xpath("//div[contains(text(),'IT')]");
    private static final By BRING_MY_BUSINESS = By.xpath("//div[contains(text(),'Bring my business overseas')]");
    private static final By MARKET_READINESS = By.xpath("//div[contains(text(),'Market Readiness Assistance')]");
    private static final By APPLY_BUTTON = By.xpath("//button[contains(text(),'Apply')]");
    private static final By PROCEED_BUTTON = By.xpath("//button[contains(text(),'Proceed')]");

    @FindBy(xpath="//h1[contains(text(),'Manual Log In')]")
    private WebElement manualLoginHeader;
    @FindBy(xpath="//h1[contains(text(),'CorpPass')]")
    private WebElement corpPassHeader;
    @FindBy(xpath="//button[contains(text(),'Log In')]")
    private WebElement loginButton;

    @FindBy(xpath="//input[@id='entityId']")
    private WebElement entityId;
    @FindBy(xpath="//input[@id='userId']")
    private WebElement userId;
    @FindBy(xpath="//input[@id='userRole']")
    private WebElement userRole;
    @FindBy(xpath="//input[@id='userFullName']")
    private WebElement userFullName;

    
    public ManualLoginPage(WebDriver driver) {
        super(driver);
    }

    public void verifyHeaders() {
        if (!corpPassHeader.isDisplayed() || !corpPassHeader.getText().equals("CorpPass")) {
            throw new AssertionError("CorpPass header missing/incorrect");
        }
        if (!manualLoginHeader.isDisplayed() || !manualLoginHeader.getText().equals("Manual Log In")) {
            throw new AssertionError("Manual Login header missing/incorrect");
        }
    }

    public void fillManualLoginFields(String configPath) {
        entityId.sendKeys(Utility.getProperty(configPath, "ENTITY_ID"));
        userId.sendKeys(Utility.getProperty(configPath, "USER_ID"));
        userRole.sendKeys(Utility.getProperty(configPath, "ROLE"));
        userFullName.sendKeys(Utility.getProperty(configPath, "FULL_NAME"));
    }

    public void completeManualLoginAndNavigate(String configPath) {
        loginButton.click();  // This can stay if simple

        waitForElementVisible(getElement(GET_NEW_GRANT), "Get New Grant");
        clickSafely(GET_NEW_GRANT);

        waitForElementVisible(getElement(IT_BUTTON), "IT Button");
        clickSafely(IT_BUTTON);

        waitForElementVisible(getElement(BRING_MY_BUSINESS), "Bring My Business");
        clickSafely(BRING_MY_BUSINESS);

        waitForElementVisible(getElement(MARKET_READINESS), "Market Readiness");
        clickSafely(MARKET_READINESS);

        waitForElementVisible(getElement(APPLY_BUTTON), "Apply Button");
        clickSafely(APPLY_BUTTON);

        waitForElementVisible(getElement(PROCEED_BUTTON), "Proceed Button");
        clickSafely(PROCEED_BUTTON);
    }

}
