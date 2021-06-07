package superunitconverter.nexmii.com.superunitconverter.models.conversion.currency.internet;

import superunitconverter.nexmii.com.superunitconverter.helpers.ErrorLogMessages;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.currency.CurrencyRepo;
import superunitconverter.nexmii.com.superunitconverter.util.Logger;

/**
 * This Singleton class is responsible for running the conversion. That is, getting the data retrieved from
 * keywordsExtractorC and using it to call the convert endpoint of fixer.io.
 */
public class CurrencyConversion {

    private double convertedNumber;

    private static CurrencyConversion instance;

    private CurrencyConversion(){}

    public static CurrencyConversion getInstance(){
        if (instance == null)
            instance = new CurrencyConversion();
        return instance;
    }

    /**
     * The method that runs the conversion when called which does so by calling the getConversion()
     * method from the CurrencyRepo.
     */
    public void runConversion(String number , String convertFrom, String convertTo, ConversionInterface ci){
        CurrencyRepo.getInstance().getConversion(number, convertFrom, convertTo
                , (double result, String errorCode) -> {
            convertedNumber = result;
            Logger logger = Logger.getLoggerInstance();
            if (errorCode != null) {
                logger.errorLog("ERROR", errorCode);
                ci.onNumberConverted(0, ErrorLogMessages.CANNOT_READ_NUMBER);
            }else{
                ci.onNumberConverted(convertedNumber, null);
            }
        });
    }

    /**
     * Interface for the runConversion() method and whoever calls it.
     */
    public interface ConversionInterface{
        void onNumberConverted(double convertedNumber, String errorCode);
    }
}
