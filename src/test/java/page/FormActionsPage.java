package page;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import generic.Utility;

/**
 * FormActionsPage.java
 * 
 * Handles form actions for BGP application:
 * - Selecting options (radio buttons, checkboxes)
 * - Entering text, numbers, dates
 * - Navigating sections (Eligibility, Contact Details)
 * - Handling buttons (Save, Next, Review, Submit)
 */

public class FormActionsPage extends BasePage  {
	
	private static final String TEXT_AREA2 = "//textarea[@id='react-project_cost-office_rentals-0-description']";
	private static final String SELECT_YES_RADIOBUTTON_XPATH = "//input[@type='radio' and @value='true']";
	private static final String SELECT_NO_RADIOBUTTON_XPATH = "//input[@type='radio' and @value='false']";
	private static final String SAVE_BUTTON = "//button[@id='save-btn']";
	private static final String NEXT_BUTTON_XPATH = "//button[@id='next-btn']";
	private static final String TEXT_FIELD = "//input[(@data-testid='text-field' or @data-testid='number-field') and not(@disabled)]";
	private static final String CONTATCT_DETAILS_CHECKBOX = "//input[@type='checkbox']";
	private static final String START_DATE = "//input[contains(@id,'react-project-start_date')]";
	private static final String End_Date = "//input[contains(@id,'react-project-end_date')]";
	private static final String Business_Impact_DATE = "//input[contains(@id,'react-project_impact-fy_end_date_0')]";
	private static final String TEXT_AREA = "//textarea";
	private static final String DROPDOWN_ARROW = "//span[@class='Select-arrow']";
	private static final String SELECT_OPTIONS_FROM_DROPDOWN = "div.Select-option";
	private static final String SELECT_VALUES_PLACEHOLDER = "//div[@class='Select-placeholder']";
	private static final String FILL_SGD_AMOUNT = "//input[@class='form-control bgp-textfield ']";
	private static final String CONFIRMATION_MESSAGE_XPATH = "//div[contains(@class,'main summary-page')]//h3[contains(text(),'Your application has been submitted.')]";
	
	
    @FindBy(xpath = SELECT_YES_RADIOBUTTON_XPATH)
    private WebElement selectYesRadioButton;
    @FindBy(xpath = SELECT_NO_RADIOBUTTON_XPATH)
    private WebElement selectNoRadioButton;
    @FindBy(xpath = SAVE_BUTTON)
    private WebElement saveButton;
    @FindBy(xpath = NEXT_BUTTON_XPATH)
    private WebElement nextButton;
    @FindBy(xpath = TEXT_FIELD)
    private WebElement textField;
    @FindBy(xpath = CONTATCT_DETAILS_CHECKBOX)
    private WebElement contactDetailsCheckbox;
    @FindBy(xpath = START_DATE)
    private WebElement todayDate;
    @FindBy(xpath = End_Date)
    private WebElement nextMonthDate;
    @FindBy(xpath = Business_Impact_DATE)
    private WebElement businessDate;
    @FindBy(xpath = TEXT_AREA)
    private WebElement textArea;
    @FindBy(xpath = DROPDOWN_ARROW)
    private WebElement dropdownArrow;
    @FindBy(css = SELECT_OPTIONS_FROM_DROPDOWN)
    private WebElement selectOptions;
    @FindBy(xpath = SELECT_VALUES_PLACEHOLDER)
    private WebElement selectPlaceholder;
    @FindBy(xpath = FILL_SGD_AMOUNT)
    private WebElement fillSgdAmt;
    @FindBy(xpath = CONFIRMATION_MESSAGE_XPATH)
	private WebElement confirmationMessage;
    @FindBy(xpath = "//span[@class= 'menu-text' and contains(text(), 'Eligibility')]")
	private WebElement eligibility;
	@FindBy(xpath = "//h2[contains(text(),'Check Your Eligibility')]")
	private WebElement checkYourEligibility;
	@FindBy(xpath = "//h2[contains(text(),'Provide Your Contact Details')]")
	private WebElement provideYourContactDetails;
	@FindBy(xpath = "//div[text()='ElementJustToWaitForSync']")
    private WebElement waitElement;

	// In FormActionsPage.java
	@FindBy(xpath = "//div[text()='ElementJustToWaitForSync']")
	private WebElement waitElement1;

	// Usage
	public void waitForFormSync(int timeoutInSeconds) {
	    waitForSyncElement(waitElement1, timeoutInSeconds);
	}

    
	    public FormActionsPage(WebDriver driver){
	        super(driver);
	       }
	    
	    
	    public void clickEligibilitySection() {
	        wait.until(ExpectedConditions.visibilityOf(eligibility));
	        wait.until(ExpectedConditions.elementToBeClickable(eligibility));
	        eligibility.click();
	    }
	
	    
	    public boolean isEligibilityMenuItemVisible() {
	        wait.until(ExpectedConditions.visibilityOf(eligibility));
	        return eligibility.isDisplayed();
	    }
	   
	    
	    public boolean isCheckEligibilityHeaderVisible() {
	        wait.until(ExpectedConditions.visibilityOf(checkYourEligibility));
	        return checkYourEligibility.isDisplayed();
	    }
	
	    public void navigateToContactDetailsSection() {
	        provideYourContactDetails.click();
	        wait.until(ExpectedConditions.visibilityOf(provideYourContactDetails));
	    }
	    
	    public void selectAllYes() {
	        List<WebElement> allYes = driver.findElements(By.xpath(SELECT_YES_RADIOBUTTON_XPATH));
	        for (WebElement yes : allYes) 
	        {
	            wait.until(ExpectedConditions.elementToBeClickable(yes));
	            yes.click();
	        }
	    }
	    
	    public void selectAllNo() {
	        List<WebElement> allNo = driver.findElements(By.xpath(SELECT_NO_RADIOBUTTON_XPATH));
	        for (WebElement no : allNo) 
	        {
	            wait.until(ExpectedConditions.elementToBeClickable(no));
	            no.click();
	        }
	    }
	    
	    public void clickSave() 
	    {
	        clickSafely(By.xpath(SAVE_BUTTON));
	    }
	
	    public void clickNext() 
	    {
	        clickSafely(By.xpath(NEXT_BUTTON_XPATH));
	    }
    
	    public void uncheckAllSelectedCheckBoxes() {
	        List<WebElement> allCheckBoxes = driver.findElements(By.xpath(CONTATCT_DETAILS_CHECKBOX));
	        for (WebElement checkBox : allCheckBoxes) 
	        {
	        	wait.until(ExpectedConditions.elementToBeClickable(checkBox));
	            if (checkBox.isSelected()) {
	                checkBox.click();
	                System.out.println("Checkbox unchecked: " + checkBox.getAttribute("id"));
	            }
	        }
	    }


//	    public void waitforElemnt(WebDriver driver,int duration)
//	    {
//	    	WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(duration));
//	    	try 
//	    	{
//	    		wait.until(ExpectedConditions.visibilityOf(waitElement));
//	    	}
//	    	catch (Exception e)
//	    	{
//	//			e.printStackTrace();
//			}
//	    }
	    
	    public void waitforElemnt(int duration) {
	        WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(duration));
	        try {
	            localWait.until(ExpectedConditions.visibilityOf(waitElement));
	        } catch (Exception e) {
	            // Optionally log or handle exception
	        }
	    }
	    
	    public void fillData(String[] data)  {
	    	{
	    		List<WebElement> allTextBox = driver.findElements(By.xpath(TEXT_FIELD));
	    		for(int i=0;i<data.length;i++)
	    		{	
	    			 wait.until(ExpectedConditions.visibilityOf(allTextBox.get(i)));
	    			allTextBox.get(i).sendKeys(data[i]);
	    		}
	    	}
	    }
    
	    public void selectAllCheckBoxes()
	    {
	    		List<WebElement> allCheckBoxes = driver.findElements(By.xpath(CONTATCT_DETAILS_CHECKBOX));
	    		for(WebElement checkBox: allCheckBoxes)
	    		{	
	    			wait.until(ExpectedConditions.elementToBeClickable(checkBox));
	    				checkBox.click();
	    		}
	    }
	    
	    public void selectProposalStartDate(String date) {
	        WebElement dates = driver.findElement(By.xpath(START_DATE));
	        dates.sendKeys(date);
	    }
	    
	    public void selectProposalEndDate(String date) {
	        WebElement dates = driver.findElement(By.xpath(End_Date));
	        dates.sendKeys(date);
	    }
	    
	    public void selectBusinessImpactDate(String date) {
	        WebElement dates = driver.findElement(By.xpath(Business_Impact_DATE));
	        dates.sendKeys(date);
	    }

    
	    public void fillTextArea(String[] data) {
	        List<WebElement> textAreas = driver.findElements(By.xpath(TEXT_AREA));
	        for (int i = 0; i < data.length; i++) {
	            if (textAreas.get(i).isDisplayed()) {
	                textAreas.get(i).sendKeys(data[i]);
	            }
	        }
	    }
	    
	    public void fillTextArea2(String[] data) {
	        List<WebElement> textAreas = driver.findElements(By.xpath(TEXT_AREA2));
	        for (int i = 0; i < data.length; i++) {
	            if (textAreas.get(i).isDisplayed()) {
	                textAreas.get(i).sendKeys(data[i]);
	            }
	        }
	    }
	    
	    public void selectListBox()  {
	        List<WebElement> listBoxes = driver.findElements(By.xpath(DROPDOWN_ARROW));
	        for (WebElement listBox : listBoxes) {
	        	wait.until(ExpectedConditions.elementToBeClickable(listBox));
	            listBox.click();
	            WebElement option = driver.findElement(By.cssSelector(SELECT_OPTIONS_FROM_DROPDOWN));
	            wait.until(ExpectedConditions.elementToBeClickable(option));
	            option.click();
	        }
	    }
	    
	    public void selectOptions(WebDriverWait wait) {
	        List<WebElement> options = driver.findElements(By.xpath(SELECT_VALUES_PLACEHOLDER));
	        for (WebElement option : options) {
	        	wait.until(ExpectedConditions.elementToBeClickable(option));
	            option.click();
	        }
	    }
	    
	    public void fillSGD(String[] data) {
			List<WebElement> allFields = driver.findElements(By.xpath(FILL_SGD_AMOUNT));
			for(int i=0;i<data.length;i++)
			{	
				wait.until(ExpectedConditions.visibilityOf(allFields.get(i)));
				allFields.get(i).sendKeys(data[i]);
			}
	    }
	    
	    public void clickReview()
		{
	    	WebElement reviewButton = driver.findElement(By.id("review-btn"));
	    	wait.until(ExpectedConditions.elementToBeClickable(reviewButton));
	        reviewButton.click();
		}
	    
	    public void enterNewItemInCostSection(String detailsData, String duration, String amount) {
	    	By officeSpaceRental = By.xpath("//div[text()='Office Space Rental']");
	        clickSafely(officeSpaceRental); 
	        System.out.println("Clicked: Office Space Rental");
	        
	        By addNewItemButton = By.xpath("//button[text()='Add New Item' and contains(@id,'office_rentals')]");
	        clickSafely(addNewItemButton);  
	        System.out.println("Clicked: Add New Item Button");

	        WebElement description = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.cssSelector("textarea#react-project_cost-office_rentals-0-description")));
	        description.sendKeys(detailsData);
	        
	        WebElement durationInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@id,'rental_duration')]")));
	        durationInput.sendKeys(duration);

	        WebElement amountInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@id,'amount_in_billing_currency')]")));
	        amountInput.sendKeys(amount);
	    }
	    
	    public void enterSubmitButton() {
	        WebElement declarationCheckbox = driver.findElement(By.xpath("//input[@type='checkbox' and @id='react-declaration-info_truthfulness_check']"));
	        wait.until(ExpectedConditions.elementToBeClickable(declarationCheckbox));
	        declarationCheckbox.click();
	        
	        WebElement submitButton = driver.findElement(By.xpath("//button[@id='submit-btn']"));
	        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
	        submitButton.click();
	    }
	    public WebElement getConfirmationMessage(WebDriverWait wait) {
	    	wait.until(ExpectedConditions.visibilityOf(confirmationMessage));
	    	return confirmationMessage;
	    }
	    
	    public void completeEligibilitySection() {
	        selectAllYes();
	        clickSave();
	        clickNext();
	    }
	    
	    public void completeContactDetailsSection(String[] contactDetails) {
	        fillData(contactDetails);	       
	        selectAllCheckBoxes();
	        clickSave();
	        clickNext();
	    }
	    
	    public void completeProposalSection(String[] projectTitle, String[] textDetails) {        
	    	fillData(projectTitle);
	    	String[] dates = Utility.generateDates();
	        System.out.println("Today's Date: " + dates[0]);
	        System.out.println("Next Date: " + dates[1]);
	    	selectProposalStartDate(dates[0]);
	    	selectProposalEndDate(dates[1]);
	        selectListBox();
	        selectAllYes();
	        fillTextArea(textDetails);
	        clickSave();
	        clickNext();
	    }
	
	    public void completeBusinessImpactSection(String[] sgdAmounts, String[] textDetails) {
	    	String[] dates = Utility.generateDates();
	        System.out.println("Today's Date: " + dates[0]);
	    	selectBusinessImpactDate(dates[0]);
	        fillSGD(sgdAmounts);
	        fillTextArea(textDetails);
	        clickSave();
	        clickNext();
	    }
	    
	    public void completeReviewSection() {
	    	waitforElemnt(1);
	        selectAllNo();
	        selectAllCheckBoxes();
	        clickSave();
	        clickReview();
	    }


}