package superunitconverter.nexmii.com.superunitconverter.helpers;

import android.content.Context;

import superunitconverter.nexmii.com.superunitconverter.R;

/**
 * ErrorAnalyzer collects the message code that was moved up the hierarchy from the try/catch block
 * that fired up the exception. Singleton
 */
public final class ErrorAnalyzer {

    private static ErrorAnalyzer instance;

    private ErrorAnalyzer(){}

    public static ErrorAnalyzer getInstance(){
        if (instance == null)
            instance = new ErrorAnalyzer();
        return instance;
    }

    /**
     * The method collects the error code, and filters it. Depending on which one is it, the
     * correct message will be returned to be displayed to the user.
     * @param context App context
     * @param errorCode the error code that was collected from exception
     * @return the correct message to display to the user
     */
    public String analyzeError(Context context, String errorCode){
        String messageToUser;
        switch(errorCode){
            case ErrorLogMessages.USER_EMPTY_INPUT:
                messageToUser = context.getResources().getString(R.string.empty_input);
                break;
            case ErrorLogMessages.CANNOT_READ_NUMBER:
            case ErrorLogMessages.EMPTY_NUMBER_TO_ROUND:
                messageToUser = context.getResources().getString(R.string.cannot_read_number);
                break;
            case ErrorLogMessages.API_CALL_FAILURE:
                messageToUser = context.getResources().getString(R.string.cannot_con_curr_no_internet);
                break;
            case ErrorLogMessages.USER_NOT_FOUND_404:
            case ErrorLogMessages.BAD_REQUEST_400:
            case ErrorLogMessages.SERVER_BROKEN_500:
            case ErrorLogMessages.REQUEST_NOT_IMPLEMENTED_501:
            case ErrorLogMessages.BAD_GATEWAY_502:
            case ErrorLogMessages.SERVICE_UNAVAILABLE_503:
                messageToUser = context.getResources().getString(R.string.cannot_con_curr_internal);
                break;
            case ErrorLogMessages.UNKNOWN_ERROR:
            case ErrorLogMessages.ARRAY_OUT_OF_BOUNDS:
            default:
                messageToUser = context.getResources().getString(R.string.cannot_convert);
        }
        return messageToUser;
    }
}
