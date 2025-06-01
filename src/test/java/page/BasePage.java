package page;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Abstract base page class to be extended by all page objects.
 * Encapsulates common actions and utilities like click handling, waits, scrolling, and JavaScript execution.
 */
public abstract class BasePage {
    // WebDriver instance for interacting with the browser
    protected final WebDriver driver;
    // WebDriverWait instance for handling explicit waits
    protected final WebDriverWait wait;

    /**
     * Constructor initializing driver and wait for the page object.
     * Initializes WebElements with PageFactory.
     * @param driver WebDriver instance
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    /**
     * Scrolls the browser viewport to make the specified element visible.
     * @param locator Locator of the element to scroll into view.
     */
    public void scrollIntoElementView(By locator) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        try {
            javascriptExecution("arguments[0].scrollIntoView({block: \"nearest\", inline: \"nearest\"});",
                    getElement(locator));
        } catch (Exception e) {
            // Handle exception if element not found
        }
    }

    /**
     * Safely clicks an element identified by the locator with retries and scrolling if necessary.
     * Handles exceptions like ElementClickIntercepted, StaleElementReference, and fallback to JavaScript click.
     * @param locator Locator of the element to be clicked.
     */
    public void clickSafely(By locator) {
        int maxRetries = 5;
        int attempt = 0;

        while (attempt < maxRetries) {
            try {
                WebElement element = waitForClickable(locator);
                scrollToElement(element);
                element.click();
                return;
            } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
                List<WebElement> overlays = driver.findElements(By.cssSelector("div[id*='overlay'], .Toastify__toast, #usercentrics-root"));
                waitForTime(1); // Optional wait before retrying
            } catch (ElementNotInteractableException e) {
                WebElement element = getElement(locator);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                return;
            } catch (Exception e) {
                break;
            }
            attempt++;
        }
        throw new RuntimeException("Failed to click element after " + maxRetries + " attempts: " + locator);
    }

    /**
     * Scrolls the browser viewport to center the specified WebElement.
     * @param element WebElement to scroll to.
     */
    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    /**
     * Pauses the execution for the given number of seconds.
     * @param timeout Number of seconds to wait.
     */
    public void waitForTime(int timeout) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
    }

    /**
     * Waits until an element located by the given locator is clickable.
     * @param locator Locator of the element.
     * @return The clickable WebElement.
     */
    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Retrieves a WebElement by locator with a default wait time of 30 seconds.
     * @param locator Locator of the element.
     * @return The WebElement found.
     */
    public WebElement getElement(By locator) {
        return getElement(locator, 30);
    }

    /**
     * Retrieves a WebElement by locator with a specified wait time.
     * @param locator Locator of the element.
     * @param waitTime Time to wait for the element in seconds.
     * @return The WebElement found.
     */
    public WebElement getElement(By locator, int waitTime) {
        if (waitTime > 0) {
            waitForElement(locator, waitTime);
        }
        return driver.findElement(locator);
    }

    /**
     * Waits for elements matching the locator to be present on the page within the specified timeout.
     * @param locator Locator of the elements.
     * @param timeout Time to wait in seconds.
     * @return List of WebElements found.
     */
    public List<WebElement> waitForElement(By locator, int timeout) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        List<WebElement> elements = driver.findElements(locator);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        return elements;
    }

    /**
     * Executes the given JavaScript code with the specified element as an argument.
     * @param script JavaScript code to execute.
     * @param element WebElement to pass to the script.
     */
    public void javascriptExecution(String script, WebElement element) {
        ((RemoteWebDriver) driver).executeScript(script, element);
    }
    
 // Add these methods in BasePage.java

    /**
     * Waits until a WebElement is visible.
     * @param element WebElement to check.
     * @param elementName Friendly name for logging and error reporting.
     * @return The visible WebElement.
     */
    public WebElement waitForElementVisible(WebElement element, String elementName) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            System.out.println(elementName + " is visible.");
        } catch (Exception e) {
            throw new AssertionError(elementName + " not visible after wait.", e);
        }
        return element;
    }

    /**
     * Waits until a WebElement is clickable.
     * @param element WebElement to check.
     * @param elementName Friendly name for logging and error reporting.
     * @return The clickable WebElement.
     */
    public WebElement waitForElementClickable(WebElement element, String elementName) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            System.out.println(elementName + " is clickable.");
        } catch (Exception e) {
            throw new AssertionError(elementName + " not clickable after wait.", e);
        }
        return element;
    }
    
    /**
     * Waits for a predefined synchronization element to become visible,
     * ensuring that the page or section has fully loaded.
     * You can use this for generic waits across different pages.
     *
     * @param syncElement The WebElement used as a sync marker.
     * @param timeoutInSeconds The maximum wait time in seconds.
     */
    public void waitForSyncElement(WebElement syncElement, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        try {
            customWait.until(ExpectedConditions.visibilityOf(syncElement));
            System.out.println("Sync element became visible.");
        } catch (TimeoutException e) {
            throw new AssertionError("Sync element was not visible after " + timeoutInSeconds + " seconds.");
        }
    }


}
