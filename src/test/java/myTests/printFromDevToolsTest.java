package myTests;

import General.GeneralProperties;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.Event;
import org.openqa.selenium.devtools.log.Log;
import org.openqa.selenium.devtools.log.model.LogEntry;
import pages.IMDBHomePage;

import java.io.IOException;

public class printFromDevToolsTest {

    private static WebDriver driver;

    @Before
    public void setUp() throws IOException, ParseException {

        System.setProperty(GeneralProperties.driverName, GeneralProperties.driverLocation);
        driver = new ChromeDriver();

        driver.manage().window().maximize();

        //connection to chrome dev tools
        DevTools tool = ((ChromeDriver)driver).getDevTools();
        tool.createSession();
        tool.send(Log.enable());
        tool.addListener(Log.entryAdded(), entry -> System.out.println("CONSOLE LOG logs: " + entry.getText()));  //printing logs

    }


    @After
    public void quitTests() {
        driver.quit();
    }




    @Test
    public void testPrintFromDevToolsConseLogs() {

        driver.get("http://www.ynet.co.il");



    }
}
