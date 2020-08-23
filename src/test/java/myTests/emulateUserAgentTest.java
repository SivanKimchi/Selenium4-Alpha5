package myTests;

import General.GeneralProperties;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.emulation.Emulation;

import java.io.IOException;
import java.util.Optional;

public class emulateUserAgentTest {

    private static WebDriver driver;
    private DevTools tool;
    public String iOSUserAgent;

    @Before
    public void setUp() throws IOException, ParseException {

        System.setProperty(GeneralProperties.driverName, GeneralProperties.driverLocation);
        driver = new ChromeDriver();

        driver.manage().window().maximize();

        tool = ((ChromeDriver) driver).getDevTools();
        tool.createSession();
        // iOS user agent
        iOSUserAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148";
        tool.send(Emulation.setUserAgentOverride(iOSUserAgent, Optional.of(""), Optional.of(""), Optional.empty()));


    }

    @After
    public void quitTests() {
        driver.quit();
    }


    @Test
    public void testEmulateUserAgent() {

        driver.get("http://whatsmyua.info");

        String presentedUserAgent = driver.findElement(By.id("custom-ua-string")).getText();

        Assert.assertEquals(presentedUserAgent, iOSUserAgent );
        System.out.println("Specific User-Agent was set");
    }

}
