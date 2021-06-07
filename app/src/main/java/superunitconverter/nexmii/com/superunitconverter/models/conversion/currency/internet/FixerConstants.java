package superunitconverter.nexmii.com.superunitconverter.models.conversion.currency.internet;

/**
 * Currency conversion API constants.
 */
public final class FixerConstants {
    public static final String BASE_URL = "http://data.fixer.io/api/";
    public static final String API_KEY = "16102ed37be475c6c9b6230c202e3ed8";

    /*
    - BASE URL: http://data.fixer.io/api/
    - END-POINT (PATH): convert
    - API KEY: ?access_key=16102ed37be475c6c9b6230c202e3ed8
    - QUERY STRINGS(convert)-example:&from=USD&to=ILS&amount=25
    -------------
    Whole thing together: http://data.fixer.io/api/convert?access_key=16102ed37be475c6c9b6230c202e3ed8&from=USD&to=ILS&amount=25
     */
}
