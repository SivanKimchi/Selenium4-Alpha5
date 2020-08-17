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
import java.util.ArrayList;
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
    public void testRelativeLocator(){


        driver.get(GeneralProperties.SiteURLIMDB);
        System.out.println("Opened driver");

        IMDBHomePage page = new IMDBHomePage(driver);

        //relative locators
        page.signInLink().click();

        //new sleep- without exceptions
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

        ArrayList<String> benefitsText = new ArrayList<String>();

        for (int i=0; i<page.benefits().size(); i++) {

            String benefit = page.benefits().get(i).getText();
            benefitsText.add(benefit);
        }


        int numOfBenefits = benefitsText.size();

        String message = "The benefits of having a free IMDB account are: ";

        for(String benefit : benefitsText) {

            if ((!benefit.equals("By signing in, you agree to IMDb's Conditions of Use and Privacy Policy.")) && !benefit.contains("by IMDb.com, Inc.")){
                message = message + "* " + benefit + "\n\r";
            }

        }

        System.out.println(message);



        }









    }





