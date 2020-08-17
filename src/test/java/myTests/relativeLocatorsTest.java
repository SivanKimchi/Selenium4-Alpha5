package myTests;

import General.GeneralProperties;
import com.google.common.util.concurrent.Uninterruptibles;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.IMDBHomePage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class relativeLocatorsTest {


    private static WebDriver driver;


    @Before
    public void setUp() throws IOException, ParseException {

        System.setProperty(GeneralProperties.driverName, GeneralProperties.driverLocation);
        driver = new ChromeDriver();

        driver.manage().window().maximize();
    }



//    @After
//    public void quitTests() {
//        driver.quit();
//    }


    @Test
    public void firstTestRelativeLocator(){


        driver.get(GeneralProperties.SiteURL2);
        System.out.println("Opened driver");

        IMDBHomePage page = new IMDBHomePage(driver);

        //relative locators
//        driver.findElement(By.cssSelector("div[class='list-group'] a")).click();  //sign in with imdb
        page.signInLink().click();

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

//        driver.findElement(By.id("ap_email")).sendKeys("test123");
        page.email().sendKeys("test123");

//        WebElement password = driver.findElement(By.id("ap_password"));
//        password.sendKeys("pass123");
        page.password().sendKeys("pass123");


//        driver.findElement(RelativeLocator.withTagName("input").below(password)).click();
        page.button().click();

        //  (find by 'near' default is 50 pixels, or by specific location.)

        //new sleep- without exceptions
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

    }




}
