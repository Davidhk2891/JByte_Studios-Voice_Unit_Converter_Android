package superunitconverter.nexmii.com.superunitconverter.util;

import java.text.DecimalFormat;

import superunitconverter.nexmii.com.superunitconverter.helpers.ErrorInterface;
import superunitconverter.nexmii.com.superunitconverter.helpers.ErrorLogMessages;

/**
 * Singleton class that rounds up all results to three decimal places
 */
public final class RoundNumber {

    private static final RoundNumber instance = new RoundNumber();
    private final Logger mLogger;

    private RoundNumber(){
        mLogger = Logger.getLoggerInstance();
    }

    public static RoundNumber getInstance(){
        return instance;
    }

    public String roundedResult(String number, ErrorInterface ei){
        double numToDouble = 0.0;
        DecimalFormat f = new DecimalFormat("###.#####");
        try {
            mLogger.infoLog("NUMBER_TO_ROUND", "IS: " + number);
            numToDouble = Double.parseDouble(number);
        }catch(NullPointerException | NumberFormatException npe){
            ei.onErrorCalled(ErrorLogMessages.EMPTY_NUMBER_TO_ROUND);
        }
        return f.format(numToDouble);
    }
}
