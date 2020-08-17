package General;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GeneralProperties {


    static JSONParser json = new JSONParser();

    static FileReader reader;

    static {
        try {
            reader = new FileReader("..\\Selenium4-Alpha5\\src\\main\\java\\General\\JsonValues2.json");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static JSONObject obj;

    static {
        try {
            obj = (JSONObject) json.parse(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static final String driverName = (String) obj.get("driverName");
    public static final String driverLocation = (String) obj.get("driverLocation");

    public static final String driverNameFireFox = (String) obj.get("driverNameFireFox");
    public static final String driverLocationFireFox = (String) obj.get("driverLocationFireFox");


    public static final String SiteURL1 = (String) obj.get("SiteURL1");
    public static final String SiteURL2 = (String) obj.get("SiteURL2");

    public static String savedScreenshotLocation = (String) obj.get("savedScreenshotLocation");
//        public final static String CAPTURE = (String) obj.get("CAPTURE");

    public GeneralProperties() throws IOException, ParseException {

    }


}
