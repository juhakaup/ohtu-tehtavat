package ohtu;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Tester {

    public static void main(String[] args) {
        //WebDriver driver = new ChromeDriver();

        HtmlUnitDriver driver = new HtmlUnitDriver();

        driver.get("http://localhost:4567");
        
        sleep(2);
        
        /*
        WebElement element = driver.findElement(By.linkText("login"));
        element.click();

        sleep(2);

        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka");
        element = driver.findElement(By.name("password"));
        element.sendKeys("akkep");
        element = driver.findElement(By.name("login"));
        
        sleep(2);
        element.submit();

        sleep(3);
        
        driver.quit();
        */


        /*
        // Correct username incorrect password
        WebElement element = driver.findElement(By.linkText("login"));
        element.click();

        sleep(2);

        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka");
        element = driver.findElement(By.name("password"));
        element.sendKeys("vaara");
        element = driver.findElement(By.name("login"));

        sleep(2);

        element.submit();

        sleep(3);

        driver.quit();
        */

        // Creating a new user

        WebElement element = driver.findElement(By.linkText("register new user"));
        element.click();

        Random random = new Random();

        element = driver.findElement(By.name("username"));
        element.sendKeys("mikko"+random.nextInt(199999));

        element = driver.findElement(By.name("password"));
        element.sendKeys("okkim123");

        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("okkim123");

        element = driver.findElement(By.name("signup"));

        sleep(3);

        element.submit();

        // Logging out after creating a new user

        element = driver.findElement(By.linkText("continue to application mainpage"));
        sleep(2);
        element.click();

        element = driver.findElement(By.linkText("logout"));
        sleep(2);
        element.click();

        sleep(5);
        driver.quit();
    }
    
    private static void sleep(int n){
        try{
            Thread.sleep(n*1000);
        } catch(Exception e){}
    }
}
