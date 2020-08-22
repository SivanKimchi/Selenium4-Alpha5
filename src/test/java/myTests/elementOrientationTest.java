package myTests;

import General.GeneralProperties;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.IMDBHomePage;

import java.io.IOException;
import java.sql.SQLOutput;

public class elementOrientationTest {


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
    public void testElementOrientation() {

        IMDBHomePage page = new IMDBHomePage(driver);

        int width = page.googleSearchBar().getRect().getWidth();
        int height = page.googleSearchBar().getRect().getHeight();
        System.out.println("Search bar width= " + width);
        System.out.println("Search bar height= " + height);

        int surfaceArea = width * height;
        System.out.println("Search bar surface area is " + surfaceArea + " pixels");

        int xLocation = page.googleSearchBar().getRect().getX();
        int yLocation = page.googleSearchBar().getRect().getY();
        System.out.println("Search bar X location= " + xLocation);
        System.out.println("Search bar Y location= " + yLocation);

        Assert.assertTrue(page.googleSearchBar().getSize().toString().equals("("+width+", "+height + ")"));
        Assert.assertTrue(page.googleSearchBar().getLocation().toString().equals("("+xLocation+", "+yLocation + ")"));
        System.out.println("");
        System.out.println("getRect().getWidth() + getRect().getHeight() is equal to existing getSize()- " + page.googleSearchBar().getSize());
        System.out.println("getRect().getX() + getRect().getY() is equal to existing getLocation()- " + page.googleSearchBar().getLocation());
    }
}
