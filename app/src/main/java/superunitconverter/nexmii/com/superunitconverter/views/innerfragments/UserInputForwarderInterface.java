package superunitconverter.nexmii.com.superunitconverter.views.innerfragments;

/**
 * Interface which provides its accessor with the converted unit along with all the original values and error message
 * if provided.
 */
public interface UserInputForwarderInterface {
    void onUserTextParsed(String category, String number, String convertedNumber, String fromUnit
            , String toUnit, String date, String errorMsg);
}
