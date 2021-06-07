package superunitconverter.nexmii.com.superunitconverter.helpers;

/**
 * Final class which holds the error numbers to be used depending on the type of error that was
 * triggered
 */
public final class ErrorLogMessages {

    //INPUT ERRORS/EXCEPTIONS

    public static final String USER_EMPTY_INPUT = "USER_EMPTY_INPUT";

    public static final String CANNOT_READ_NUMBER = "CANNOT_READ_NUMBER";

    public static final String EMPTY_NUMBER_TO_ROUND = "EMPTY_NUMBER_TO_ROUND";

    public static final String ARRAY_OUT_OF_BOUNDS = "ARRAY_OUT_OF_BOUNDS";

    //INTERNET ERRORS/EXCEPTIONS (Retrofit)

    public static final String API_CALL_FAILURE = "API_CALL_FAILURE";

    public static final String USER_NOT_FOUND_404 = "404_USER_NOT_FOUND";

    public static final String BAD_REQUEST_400 = "400_BAD_REQUEST";

    public static final String SERVER_BROKEN_500 = "500_SERVER_IS_BROKEN";

    public static final String REQUEST_NOT_IMPLEMENTED_501 = "501_REQUEST_NOT_IMPLEMENTED";

    public static final String BAD_GATEWAY_502 = "502_BAD_GATEWAY";

    public static final String SERVICE_UNAVAILABLE_503 = "503_SERVICE_UNAVAILABLE";

    public static final String UNKNOWN_ERROR = "UNKOWN_ERROR";

}
