package myTests;

import General.GeneralProperties;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.commons.io.FileUtils;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.log.Log;
import org.openqa.selenium.devtools.network.Network;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.IMDBHomePage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class testBlockResources {

    private static WebDriver driver;
    private DevTools tool;


    @Before
    public void setUp() throws IOException, ParseException {

        System.setProperty(GeneralProperties.driverName, GeneralProperties.driverLocation);
        driver = new ChromeDriver();

        driver.manage().window().maximize();


        //block resources
        tool = ((ChromeDriver) driver).getDevTools();
        tool.createSession();
        tool.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty())); //empty buffer size


    }


    @After
    public void quitTests() {
        driver.quit();
    }


    @Test
    public void testBlockResourcesSpecificBlock() throws IOException {

        //block a SPECIFIC image on the IMDb website:
        tool.send(Network.setBlockedURLs(ImmutableList.of("www_imdb_logo._CB443130112_.png")));  //from ‘src’ attribute- the image’s name at the end.
        driver.get(GeneralProperties.SiteURLIMDB);


        IMDBHomePage page = new IMDBHomePage(driver);
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

        page.signInLink().click();

        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

        //clicking IMDb sign in
        for (int i = 0; i < page.signInOptionsList().size(); i++) {

            if (page.signInOptionsList().get(i).getText().contains("Sign in with")) {
                String[] signInOptionArray = page.signInOptionsList().get(i).getText().split("Sign in with ");
                String signInOption = signInOptionArray[1];

                if (signInOption.equals("IMDb")) {

                    page.signInOptionsList().get(i).click();

                    break;
                }
            }
        }

        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        //taking screenshot of missing logo
        File source = page.logoInIMDbLogin().getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File("./screenshots/logoBlocked.png"));
        System.out.println("Took screen shot of missing IMDb logo in 'sign with imdb account' page -- force block");

    }


    @Test
    public void testBlockResourcesFullBlock() throws IOException {

        //block all files of x source
        tool.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty())); //empty buffer size
        tool.send(Network.setBlockedURLs(ImmutableList.of("*.css"))); // block ALL css, jpg, javascript, whatever...
        driver.get(GeneralProperties.SiteURLIMDB);

        IMDBHomePage page = new IMDBHomePage(driver);
        page.signInLink().click();
        System.out.println(page.createNewAccountButton().getCssValue("background-color"));

        Assert.assertTrue(page.createNewAccountButton().getCssValue("background-color").contains("0, 0, 0, 0")); //should be rgba(228, 183, 33, 1) WITH CSS

        File source = page.createNewAccountButton().getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File("./screenshots/noCSS.png"));
        System.out.println("Took screen shot of missing CSS example");

    }

    @Test    //in comments - compare SPECIFIC webElement image OR full page screenshot
    public void testCompareImagesWithWithoutBlock() throws IOException {

        driver.get(GeneralProperties.SiteURLIMDB);
        IMDBHomePage page = new IMDBHomePage(driver);

        //  ***  specific webElement
//        page.signInLink().click();
//        File source = page.createNewAccountButton().getScreenshotAs(OutputType.FILE);
//        FileUtils.copyFile(source, new File("./screenshots/createAccountWithCSS.png"));
        File screen = ((ChromeDriver)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screen, new File("./screenshots/visibilePageWithCSS.png"));

        //loading without css
        tool.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        tool.send(Network.setBlockedURLs(ImmutableList.of("*.css"))); // block ALL css
//        driver.get(GeneralProperties.SiteURLIMDB);
        driver.navigate().refresh();      //page print

//        page.signInLink().click();
//        File source2 = page.createNewAccountButton().getScreenshotAs(OutputType.FILE);
//        FileUtils.copyFile(source2, new File("./screenshots/createAccountWithoutCSS.png"));
        File screen2 = ((ChromeDriver)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screen2, new File("./screenshots/visibilePageWithoutCSS.png"));

//        BufferedImage withCSS = ImageIO.read(new File("./screenshots/createAccountWithCSS.png"));   //specific webElement
//        BufferedImage withoutCSS = ImageIO.read(new File("./screenshots/createAccountWithoutCSS.png"));
        BufferedImage withCSS = ImageIO.read(new File("./screenshots/visibilePageWithCSS.png"));
        BufferedImage withoutCSS = ImageIO.read(new File("./screenshots/visibilePageWithoutCSS.png"));

        Assert.assertTrue(page.compareImages(withCSS, withoutCSS)==false);

    }

}
