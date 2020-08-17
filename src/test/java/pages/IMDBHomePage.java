package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.locators.RelativeLocator;

public class IMDBHomePage {



    private WebDriver driver;

    public By signInLink = By.cssSelector("div[class='list-group'] a");

    public By email = By.id("ap_email");

    public By password = By.id("ap_password");

    //Relative Locator
    public By button = RelativeLocator.withTagName("input").below(password);



    public WebElement signInLink(){
        return driver.findElement(signInLink);
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
