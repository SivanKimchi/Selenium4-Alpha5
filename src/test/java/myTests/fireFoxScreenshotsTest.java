package myTests;

import General.GeneralProperties;
import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.commons.io.FileUtils;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.IMDBHomePage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class fireFoxScreenshotsTest {

    private static WebDriver driver;


    @Before
    public void setUp() throws IOException, ParseException {

        System.setProperty(GeneralProperties.driverNameFireFox, GeneralProperties.driverLocationFireFox);
        driver = new FirefoxDriver();

        driver.manage().window().maximize();

        driver.get(GeneralProperties.SiteURLIMDB);
    }


    @After
    public void quitTests() {
        driver.quit();
    }


    @Test
    public void testPartialAndFullScreenshot() throws IOException {

        IMDBHomePage page = new IMDBHomePage(driver);

        // screenshot of IMDb logo
        File source = page.logo().getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File("./screenshots/logo.png"));
        System.out.println("Took screen shot of WebElement");

        // full VISIBLE page
        File screen = ((FirefoxDriver)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screen, new File("./screenshots/screen.png"));
        System.out.println("Took screen shot of visible screen");

        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);

        // full page  (no need for scrolling)  -- firefox only
        File fullScreen = ((FirefoxDriver)driver).getFullPageScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(fullScreen, new File("./screenshots./fullscreen.png"));
        System.out.println("Took screen shot of whole screen");

    }



}
