package myTests;

import General.GeneralProperties;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.IMDBHomePage;

import java.io.IOException;
import java.util.*;

public class newWindowTabTest {


    private static WebDriver driver;


    @Before
    public void setUp() throws IOException, ParseException {

        System.setProperty(GeneralProperties.driverName, GeneralProperties.driverLocation);
        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get(GeneralProperties.SiteURLIMDB);
    }


    @After
    public void quitTests() {
        driver.quit();
    }


    @Test
    public void testNewWindowTab() {

        IMDBHomePage page = new IMDBHomePage(driver);

        page.scroll(page.posterTitlesList().get(0));
        String posterName = page.posterTitlesList().get(0).getText();

        //newWindow = WindowType.TAB OR WindowType.WINDOW
        driver.switchTo().newWindow(WindowType.TAB);

        Set<String> handles = driver.getWindowHandles();
        Iterator<String> it = handles.iterator();
        String parentWindow = it.next();
        String childWindow = it.next();

        driver.switchTo().window(childWindow);
        driver.get("https://google.com");
        System.out.println("Switched to new tab- google.com");
        page.googleSearchBar().sendKeys(posterName, Keys.ENTER);

        String[] postNameArray = (posterName.split(" "));
        for (String string : postNameArray) {
            System.out.println(string);
            Assert.assertTrue(driver.getCurrentUrl().contains(string));
        }

        System.out.println("Searched term from previous tab- IMDb.com, in google tab");
        driver.close();
        driver.switchTo().window(parentWindow);

        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.get("https://www.wikipedia.org/");
        System.out.println("Switched to new window - wikipedia.org");

        page.wikipediaSearchBar().sendKeys(posterName, Keys.ENTER);
        try {
            Assert.assertTrue(page.wikipediaResultHeadline().getText().contains(posterName));
            System.out.println("Searched term from previous window of IMDb.com, in wikipedia window");
        } catch (Exception e) {
            System.out.println("No conclusive result received from IMDb search term");
        }


    }
}