package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.locators.RelativeLocator;

import java.util.List;

public class IMDBHomePage {



    private WebDriver driver;

    public By signInLink = By.linkText("Sign In");

    public By benefitsOfAccountHeadline = By.cssSelector("div[id='signin-perks'] h1");

    //Relative Locator
    public By benefit = RelativeLocator.withTagName("p").below(benefitsOfAccountHeadline);

    public By orDivider = By.cssSelector("p[class='divider-text']");

    public By signInOptionsList = RelativeLocator.withTagName("a").above(orDivider);

    public By mainSearchBar = By.id("suggestion-search");


//    public By signInNotice = By.cssSelector("div[id='signin-notice'] p");

    //Relative Locator
//    public By createNewAccountButton = RelativeLocator.withTagName("a").above(signInNotice);

    public By createAccountHeadline = By.cssSelector("h1[class='a-spacing-small']");



    public By email = By.id("ap_email");

    public By password = By.id("ap_password");

    //Relative Locator
    public By button = RelativeLocator.withTagName("input").below(password);

    //Relative Locator
    public By imdbPro = RelativeLocator.withTagName("div").toRightOf(mainSearchBar);

    public By imdbProButton = By.partialLinkText("Try IMDbPro Free");

    public By menu = By.id("imdbHeader-navDrawerOpen--desktop");

    //Relative Locator
    public By searchAllDropDown = RelativeLocator.withTagName("div").toLeftOf(mainSearchBar).toRightOf(menu);

    public By searchAllOptionsList = By.cssSelector("span[id='navbar-search-category-select-contents'] ul a");



    public WebElement signInLink(){
        return driver.findElement(signInLink);
    }

    public WebElement benefitsOfAccountHeadline(){
        return driver.findElement(benefitsOfAccountHeadline);
    }

    public List<WebElement> benefits(){
        return driver.findElements(benefit);
    }

    public WebElement orDivider(){
        return driver.findElement(orDivider);
    }

    public List<WebElement> signInOptionsList(){
        return driver.findElements(signInOptionsList);
    }

    public WebElement mainSearchBar(){
        return driver.findElement(mainSearchBar);
    }

    public WebElement imdbPro(){
        return driver.findElement(imdbPro);
    }

    public WebElement imdbProButton(){
        return driver.findElement(imdbProButton);
    }

    public WebElement searchAllDropDown(){
        return driver.findElement(searchAllDropDown);
    }

    public List<WebElement> searchAllOptionsList(){
        return driver.findElements(searchAllOptionsList);
    }






//    public WebElement signInNotice(){ return driver.findElement(signInNotice);
//    }
//
//    public WebElement createNewAccountButton(){ return driver.findElement(createNewAccountButton);
//    }

    public WebElement createAccountHeadline(){
        return driver.findElement(createAccountHeadline);
    }




    public WebElement email(){
        return driver.findElement(email);
    }

    public WebElement password(){
        return driver.findElement(password);
    }

    public WebElement button(){
        return driver.findElement(button);
    }



    //constructor
    public IMDBHomePage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
