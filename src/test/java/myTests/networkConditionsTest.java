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
import org.openqa.selenium.devtools.network.Network;
import org.openqa.selenium.devtools.network.model.ConnectionType;
import org.openqa.selenium.devtools.performance.Performance;
import org.openqa.selenium.devtools.performance.model.Metric;
import static org.openqa.selenium.devtools.performance.Performance.getMetrics;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class networkConditionsTest {

    private static WebDriver driver;
    private DevTools tool;


    @Before
    public void setUp() throws IOException, ParseException {

        System.setProperty(GeneralProperties.driverName, GeneralProperties.driverLocation);
        driver = new ChromeDriver();

        driver.manage().window().maximize();

        tool = ((ChromeDriver) driver).getDevTools();
        tool.createSession();



    }

    @After
    public void quitTests() {
        driver.quit();
    }


    @Test
    public void testOfflineMode (){

        //true = offline ; other parameters aren't important here because it's offline
        tool.send(Network.emulateNetworkConditions(true, 100, 100, 100, Optional.of(ConnectionType.OTHER)));

        driver.get(GeneralProperties.SiteURLIMDB);

        try {
            Assert.assertTrue(driver.findElement(By.xpath("//*[text()='No internet']")).isDisplayed());
            System.out.println("Website is offline");
        } catch (Exception e) {
            System.out.println("There is connection");
        }

    }


    /*

    @Test   // *** this works in ALPHA 6 version of selenium 4
    public void testNetworkTypeAndPerformance() {

        //connection type = wifi
        tool.send(Network.emulateNetworkConditions(false, 100, 10000, 90000, Optional.of(ConnectionType.WIFI)));
        //latency = how long it takes from sending the request until it is received
        //with lower download/upload time, site will find it difficult to load...for instance 500

        //performance metrics
        tool.send(Performance.enable(Optional.of(Performance.EnableTimeDomain.TIMETICKS)));  //ALPHA 6 version of Selenium 4
        List<Metric> metrics = tool.send(getMetrics());  //all performance metrics

        long start = System.currentTimeMillis();
        driver.get(GeneralProperties.SiteURLIMDB);
        long finish = System.currentTimeMillis();
        long totalTime = finish - start;

        Assert.assertTrue(totalTime>10000);
        System.out.println("Total Time for page load - "+totalTime + "\r\n");

        metrics.forEach(metric -> System.out.println(metric.getName() + " " + metric.getValue()));  //will print all available performance metrics
    }

    */

}
