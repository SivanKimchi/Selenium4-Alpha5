package myTests;

import General.GeneralProperties;
import com.google.common.util.concurrent.Uninterruptibles;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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



    @After
    public void quitTests() {
        driver.quit();
    }


    @Test
    public void testRelativeLocatorBelow(){

        // get all text under headline "Benefits of your free IMDb account" on IMDB.com's 'Sign In' page
        //using relative locator "below"

        IMDBHomePage page = new IMDBHomePage(driver);
        
        page.signInLink().click();

        //new sleep method- without exceptions
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
        System.out.println("Read the 'benefits' of imdb account, using 'below' locator in a loop of webelements");

        }




    @Test
    public void testRelativeLocatorAbove() {

        IMDBHomePage page = new IMDBHomePage(driver);

        page.signInLink().click();

        //new sleep method- without exceptions
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
                    System.out.println("Clicked on IMDB log-in option using 'above' locator in loop of webelements");
                    break;
                }
            }
        }
    }


    @Test
    public void testRelativeLocatorRightLeft() {

        //relative locators right of / left of

        IMDBHomePage page = new IMDBHomePage(driver);

        Actions action = new Actions(driver);
        action.moveToElement(page.imdbPro()).build().perform();   //toRightOf search bar

        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

        Assert.assertTrue(page.imdbProButton().isDisplayed());
        System.out.println("Hoovered over the element RIGHT to the search bar and validated 'imdbPro' button is displayed");

        page.searchAllDropDown().click(); //roLeftOf search bar & toRightOf menu

        String searchCategories= "Search categories are- " + "\r";
        WebElement chosenCategory = null;

        for (WebElement webElement : page.searchAllOptionsList()) {
            searchCategories = searchCategories + webElement.getText() + "\n";

            if (webElement.getText().equals("TV Episodes")) {   //use parameter if method is moved to IMDBhomePage class
                chosenCategory = webElement;
            }
        }

        System.out.println(searchCategories);
        chosenCategory.click();
        Assert.assertTrue(page.searchAllDropDown().getText().contains("TV Episodes"));
        System.out.println("Clicked the element LEFT to search bar and chose a search category");

        }


    }








