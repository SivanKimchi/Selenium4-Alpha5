package myTests;

import General.GeneralProperties;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.network.Network;
import org.openqa.selenium.devtools.network.model.Headers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class additionalHttpHeadersTest {

    private static WebDriver driver;
    private Headers data;

    @Before
    public void setUp() throws IOException, ParseException {

        System.setProperty(GeneralProperties.driverName, GeneralProperties.driverLocation);
        driver = new ChromeDriver();

        driver.manage().window().maximize();


        //add headers
        DevTools tool = ((ChromeDriver) driver).getDevTools();
        tool.createSession();
        tool.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty())); //empty buffer size

        HashMap<String, Object> header = new HashMap<String, Object>();
        header.put("My-Name", "Sivan Kimchi");
        data = new Headers(header);
        tool.send(Network.setExtraHTTPHeaders(data));


    }


    @After
    public void quitTests() {
        driver.quit();
    }


    @Test
    public void testAddAdditionalHttpHeaders() {

        driver.get("http://xhaus.com/headers");  //website that shows headers

        List<WebElement> tableCells = driver.findElements(By.tagName("td"));

        boolean httpHeaderAdded = false;
        boolean httpHeaderValueAdded = false;

        for (int i=0; i<tableCells.size(); i++) {

            String[] dataArray = data.toString().split("=");

            if (dataArray[0].contains(tableCells.get(i).getText())){
                System.out.println("HTTP headers table has new cell, with new header: " + tableCells.get(i).getText());
                httpHeaderAdded=true;
            }
            if (dataArray[1].contains(tableCells.get(i).getText())){
                System.out.println("New header VALUE: " + tableCells.get(i).getText());
                httpHeaderValueAdded=true;
            }
        }

        Assert.assertTrue(httpHeaderAdded && httpHeaderValueAdded);
    }
 }
