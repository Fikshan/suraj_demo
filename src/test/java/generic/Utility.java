package generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import page.ManualLoginPage;
import page.SignInPage;

public class Utility {

    /**
     * Reads data from an Excel file at the given path.
     * @param xlpath Path to the Excel file.
     * @param sheetName Name of the sheet.
     * @param row Row index (0-based).
     * @param col Column index (0-based).
     * @return Cell value as a String.
     */
    public static String getXLCellData(String xlpath,String sheetName,int row,int col) {
        String data="";
        try {
            Workbook wb = WorkbookFactory.create(new FileInputStream(xlpath));
            Sheet sheet1 = wb.getSheet(sheetName);
            data = sheet1.getRow(row).getCell(col).toString();
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Returns the current timestamp in a specific format for unique identification.
     * @return Formatted timestamp string.
     */
    public static String getTimeStamp() {
        LocalDateTime n = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd_MM_YYYY_hh_mm_ss");
        return n.format(format);
    }

    /**
     * Takes a screenshot of the current browser window and saves it to the given path.
     * @param driver WebDriver instance.
     * @param path Destination file path.
     */
    public static void takeScreenshot(WebDriver driver,String path) {
        TakesScreenshot t = (TakesScreenshot)driver;
        File srcFile = t.getScreenshotAs(OutputType.FILE);
        File dstFile = new File(path);
        try {
            FileUtils.copyFile(srcFile, dstFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Reads a value from a properties file.
     * @param file Path to properties file.
     * @param key Key to look up.
     * @return Property value as a String.
     */
    public static String getProperty(String file,String key) {
        String value = "";
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(file));
            value = p.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
    
    public static String[] generateDates() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Date today = new Date();  // Current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        
        String currentDate = sdf.format(today);  // Format today's date
        
        calendar.add(Calendar.DATE, 1);  // Add 1 day
        String nextDate = sdf.format(calendar.getTime());  // Format next day's date
        
        return new String[] { currentDate, nextDate };
       }
}
