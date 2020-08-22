package myTests;

import General.GeneralProperties;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.security.Security;

import java.io.IOException;

public class insecureSiteTest {

    private static WebDriver driver;



    @Before
    public void setUp() throws IOException, ParseException {

        System.setProperty(GeneralProperties.driverName, GeneralProperties.driverLocation);
        driver = new ChromeDriver();

        driver.manage().window().maximize();

        DevTools tool = ((ChromeDriver)driver).getDevTools();
        tool.createSession();
        tool.send(Security.enable());
        tool.send(Security.setIgnoreCertificateErrors(true));  //ignore inssecure alert


    }


    @After
    public void quitTests() {
        driver.quit();
    }


    @Test
    public void testInsecureSiteIgnore() {


        driver.get("http://expired.badssl.com");
        String[] text = driver.findElement(By.tagName("h1")).getText().split("\\r?\\n");
        System.out.println("Text inside website, after ignoring security alert: '" + text[0]+ text[1] +"'");

    }
}
