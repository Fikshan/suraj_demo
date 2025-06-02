package generic;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


/**
 * BaseTest.java
 * 
 * This is the base class for all test classes. It handles:
 * - TestNG lifecycle hooks for suite and method setup/teardown
 * - WebDriver initialization (local and grid)
 * - Implicit and explicit waits
 * - Extent Reports setup for test reporting
 * - Screenshot capture on failure
 */
public class BaseTest {
    public static final String CONFIG_PATH = "./config.properties"; // Path to configuration file
    public static final String XL_PATH = "./data/input.xlsx"; // Path to Excel file for test data
    public static ExtentReports extent; // ExtentReports instance for reporting

    public WebDriver driver; // WebDriver instance
    public WebDriverWait wait; // Explicit wait instance
    public ExtentTest test; // ExtentTest instance for logging

    /**
     * Initializes the Extent Reports before any test suite starts.
     */
    @BeforeSuite
    public void initReport() {
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
        extent.attachReporter(spark);
    }

    /**
     * Flushes and finalizes the Extent Reports after all test suites finish.
     */
    @AfterSuite
    public void generateReport() {
        extent.flush();
    }

    /**
     * Pre-condition method that runs before each test method.
     * Sets up the WebDriver based on the config (local or grid), maximizes window,
     * sets implicit and explicit waits, and navigates to the application URL.
     *
     * @param method   The test method (used for naming reports)
     * @param property Optional config file path parameter
     * @throws Exception
     */
    @Parameters("property")
    @BeforeMethod
    public void preCondition(Method method, @Optional(CONFIG_PATH) String property) throws Exception {
        String testName = method.getName();
        test = extent.createTest(testName);

        // Read configuration properties
        String grid = Utility.getProperty(property, "GRID");
        String grid_url = Utility.getProperty(property, "GRID_URL");
        String browser = Utility.getProperty(property, "BROWSER");
        String appURL = Utility.getProperty(property, "APP_URL");
        String ITO = Utility.getProperty(property, "ITO"); // Implicit Timeout
        String ETO = Utility.getProperty(property, "ETO"); // Explicit Timeout

        // Initialize WebDriver (Remote or Local)
        if(grid.equalsIgnoreCase("yes"))
		{
        	 // Run tests on Selenium Grid
			if(browser.equalsIgnoreCase("chrome"))
			{
				test.info("Open Chrome Browser in Remote System");
				driver=new RemoteWebDriver(new URL(grid_url),new ChromeOptions());
			}
			else if(browser.equalsIgnoreCase("firefox"))
			{
				test.info("Open Firefox Browser in Remote System");
				driver=new RemoteWebDriver(new URL(grid_url),new FirefoxOptions());
			}
			else
			{
				test.info("Open Edge Browser in Remote System");
				driver=new RemoteWebDriver(new URL(grid_url),new EdgeOptions());
			}	
		}
		else
		{	// Run tests locally
			if(browser.equalsIgnoreCase("chrome"))
			{
				ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-extensions");
                options.addArguments("--start-maximized");
                options.addArguments("--disable-infobars");
                options.addArguments("--disable-notifications");
                test.info("Open Chrome Browser in Local System");
                driver = new ChromeDriver(options);
			}
			else if(browser.equalsIgnoreCase("firefox"))
			{
				test.info("Open Firefox Browser in Local System");
				driver=new FirefoxDriver();
			}
			else
			{
				test.info("Open Edge Browser in Local System");
				driver=new EdgeDriver();
			}	
		}

        // Browser setup and navigation
        test.info("Enter the URL:" + appURL);
        driver.get(appURL);
        test.info("Maximize the Browser");
        driver.manage().window().maximize();

        // Set implicit wait
        test.info("Set implicit Wait:" + ITO);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(ITO)));

        // Initialize explicit wait
        test.info("Set Explicit Wait:" + ETO);
        wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(ETO)));
    }

    /**
     * Post-condition method that runs after each test method.
     * Closes the browser, captures a screenshot if test failed, and logs the result in the report.
     *
     * @param testResult The result of the executed test method
     * @throws IOException
     */
    @AfterMethod
    public void postCondition(ITestResult testResult) throws IOException {
        String testName = testResult.getName();
        int status = testResult.getStatus();

        if (status == ITestResult.SUCCESS) {
            test.pass("The Test is Passed");
        } else {
            String timeStamp = Utility.getTimeStamp();
            Utility.takeScreenshot(driver, "target/" + testName + timeStamp + ".png");
            test.fail("The Test is Failed", MediaEntityBuilder.createScreenCaptureFromPath(testName + timeStamp + ".png").build());
        }

        test.info("Close the Browser");
        driver.quit();
    }
}
