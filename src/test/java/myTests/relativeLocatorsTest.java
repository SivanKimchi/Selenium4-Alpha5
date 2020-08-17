package myTests;

import General.GeneralProperties;
import com.google.common.util.concurrent.Uninterruptibles;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Assert;
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

        driver.get(GeneralProperties.SiteURLIMDB);
    }



//    @After
//    public void quitTests() {
//        driver.quit();
//    }


    @Test
    public void testRelativeLocator(){

        // get all text under headline "Benefits of your free IMDb account" on IMDB.com's 'Sign In' page
        //using relative locator "below"

        IMDBHomePage page = new IMDBHomePage(driver);
        
        page.signInLink().click();

        //new sleep- without exceptions
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

        //list of texts
        ArrayList<String> benefitsText = new ArrayList<String>();

        for (int i=0; i<page.benefits().size(); i++) {

            String benefitText = page.benefits().get(i).getText();
            benefitsText.add(benefitText);
        }

        String message = "The benefits of having a free IMDB account are: ";

        for(String benefit : benefitsText.subList( 1, benefitsText.size() )) {

            if ((!benefit.equals("By signing in, you agree to IMDb's Conditions of Use and Privacy Policy.")) && !benefit.contains("by IMDb.com, Inc.")){
                message = message +"\n\r"+ "* " + benefit + "\n\r";
            }
        }

        System.out.println(message);

        }




    @Test
    public void testRelativeLocator2() {

        IMDBHomePage page = new IMDBHomePage(driver);

        page.signInLink().click();

        //new sleep- without exceptions
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

        //reverse loop
        for (int i =page.signInOptionsList().size(); i-- > 0;) {

            if (page.signInOptionsList().get(i).getText().contains("Sign in with")) {
                String[] signInOptionArray = page.signInOptionsList().get(i).getText().split("Sign in with ");
                String signInOption = signInOptionArray[1];
                System.out.println("Sign in with- " + signInOption);

                if (signInOption.equals("IMDb")) {
                    page.signInOptionsList().get(i).click();

                    //Assert click was made on webelement located by relative locator - "above"
                    Assert.assertEquals(page.createAccountHeadline().getText(), "Sign-In");
                    System.out.println("Clicked on IMDB log in option");
                    break;
                }
            }
        }

    }










    }





