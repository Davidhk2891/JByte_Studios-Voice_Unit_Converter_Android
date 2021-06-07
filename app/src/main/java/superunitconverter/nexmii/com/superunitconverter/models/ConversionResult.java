package superunitconverter.nexmii.com.superunitconverter.models;

import java.util.HashMap;

import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitConstants;
import superunitconverter.nexmii.com.superunitconverter.util.Logger;

/**
 * This class single purpose is to prepare the result set which will be used in the answer
 */
public final class ConversionResult {

    /**
     * This handles all the operations that occurr in the individual conversion methods.
     * For example: Kelvin -> Fahrenheit, Celcius -> Fahrenheit, Mile -> Kilometer, etc.
     * The method makes the correct conversion depending on the conversion context.
     * The method returns a HashMap<String, String> which contains four sets:
     * number, convertedNumber, original unit and target unit.
     */
    public static HashMap<String, String> makeResult(ConversionContext cc, String number
            , String preunit, String postUnit){

        HashMap<String, String> finalSet = new HashMap<>();
        Logger logger = Logger.getLoggerInstance();

        double numberToConvert = 0.0;
        if (number != null)
            numberToConvert = Double.parseDouble(number);

        String conNumber = String.valueOf(cc.executeStrategy(numberToConvert, preunit));

        finalSet.put(UnitConstants.CONVERTED_NUMBER, conNumber);
        finalSet.put(UnitConstants.NUMBER, number);
        finalSet.put(UnitConstants.CONVERT_FROM, preunit);
        finalSet.put(UnitConstants.CONVERT_TO, postUnit);

        logger.infoLog("CAPTURED_CONVERT_NUMBER:", number);
        logger.infoLog("CAPTURED_CONVERT_FROM:", preunit);
        logger.infoLog("CAPTURED_CONVERT_TO:", postUnit);
        logger.infoLog("CAPTURED_CONVERTED_NUMBER:", conNumber);

        return finalSet;
    }

}
