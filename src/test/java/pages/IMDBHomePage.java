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

    //Relative Locators
    public By benefit = RelativeLocator.withTagName("p").below(benefitsOfAccountHeadline);




    public By email = By.id("ap_email");

    public By password = By.id("ap_password");

    //Relative Locator
    public By button = RelativeLocator.withTagName("input").below(password);



    public WebElement signInLink(){
        return driver.findElement(signInLink);
    }

    public WebElement benefitsOfAccountHeadline(){
        return driver.findElement(benefitsOfAccountHeadline);
    }

    public List<WebElement> benefits(){
        return driver.findElements(benefit);
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
