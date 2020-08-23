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
import org.openqa.selenium.devtools.emulation.Emulation;
import org.openqa.selenium.devtools.network.Network;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

public class emulateGeoLocationAndTimeZoneTest {

    private static WebDriver driver;
    private  DevTools tool;

    @Before
    public void setUp() throws IOException, ParseException {

        System.setProperty(GeneralProperties.driverName, GeneralProperties.driverLocation);
        driver = new ChromeDriver();

        driver.manage().window().maximize();


        //emulate geoLocation
        tool = ((ChromeDriver) driver).getDevTools();
        tool.createSession();


    }

    @After
    public void quitTests() {
        driver.quit();
    }


    @Test
    public void testEmulateGeoLocation() {

            //parameters: latitude, longitude, accuracy   // London
            tool.send(Emulation.setGeolocationOverride(Optional.of(51.5074), Optional.of(0.1278), Optional.of(100)));  //override to current physical location. number==coordinate. 100=accurate 100%

            driver.get("https://google.com");

            WebElement search = driver.findElement(By.cssSelector("input[name='q']"));
            search.sendKeys("University");
            search.submit();

            List<WebElement> resultsDivs = driver.findElements(By.cssSelector("div[class='g']"));

            boolean relevantGeoSearch = false;

            for (WebElement webElement : resultsDivs) {
                if (webElement.getText().contains("University of London")) {
                    relevantGeoSearch = true;
                    break;
                }
            }

            Assert.assertTrue(relevantGeoSearch);
            System.out.println("Search results are relevant to chosen GeoLocation");

        }



    @Test
    public void testEmulateTimeZone() {

        // my actual time
        LocalTime localTime = LocalTime.now();
        String[] time = localTime.toString().split(":");
        int hour = Integer.parseInt(time[0]);

        //parameters: latitude, longitude, accuracy
        tool.send(Emulation.setTimezoneOverride("Europe/Amsterdam"));  //emulate Amsterdam -1Hour
        driver.get("http://whatismytimezone.com");

        String[] webHourText = driver.findElement(By.xpath("//article[1]")).getText().split(":");
        String[] webHour = webHourText[0].split(" ");
        int webHourInt = Integer.parseInt(webHour[webHour.length-1]); //last item in array

        Assert.assertTrue(hour - webHourInt == 1);   //1 hour difference between current time zone and chosen Amsterdam timezone
        System.out.println("Different time zone was set to browser. Actual current hour: " + hour + ", and browser's hour: " + webHourInt);
    }
  }
