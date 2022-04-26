import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;
import java.sql.DriverManager;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Demo {
    public static void main(String[] args) {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("http://www.amazon.in/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));


        try{
            WebElement hamMenu = driver.findElement(By.xpath("//a[@id='nav-hamburger-menu']"));
            hamMenu.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            WebElement tvCategory = driver.findElement(By.xpath("//a/div[text()='TV, Appliances, Electronics']"));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(tvCategory));
            tvCategory.click();
            //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            WebElement tv = driver.findElement(By.xpath("//a[text()='Televisions']"));
            wait.until(ExpectedConditions.visibilityOf(tv));
            tv.click();

            WebElement samsung = driver.findElement(By.xpath("//span[text()='Samsung']"));
            samsung.click();

            Select select = new Select(driver.findElement(By.xpath("//select[@id='s-result-sort-select']")));
            select.selectByVisibleText("Price: High to Low");

            String mainWindow = driver.getWindowHandle();

            WebElement secondResult = driver.findElement(By.xpath("//div[@cel_widget_id='MAIN-SEARCH_RESULTS-2']//a"));
            secondResult.click();

            new WebDriverWait(driver,Duration.ofSeconds(10)).until(ExpectedConditions.numberOfWindowsToBe(2));
            Set<String> handles = driver.getWindowHandles();
            System.out.println(handles);
            for(String windows:handles) {
                if (!mainWindow.equals(windows)) {
                    driver.switchTo().window(windows);
                    System.out.println(windows);
                }
            }

            String aboutSection = driver.findElement(By.xpath("//h1[text()=' About this item ']")).getText();

            Assert.assertTrue("Test passed successfully", aboutSection.contains("About this item"));

            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.close();
            driver.quit();
        }


    }
}

