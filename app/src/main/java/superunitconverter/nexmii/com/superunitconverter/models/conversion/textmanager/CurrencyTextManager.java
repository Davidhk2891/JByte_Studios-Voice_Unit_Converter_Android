package superunitconverter.nexmii.com.superunitconverter.models.conversion.textmanager;

import java.util.HashMap;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.currency.internet.CurrencyConversion;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitConstants;
import superunitconverter.nexmii.com.superunitconverter.util.Logger;

/**
 * Singleton currency text manager class for handling all currency operations
 */
public class CurrencyTextManager extends TextManager {

    private final Logger mLogger;
    private HashMap<String, String> currencyKeywords;

    private String convertFrom;
    private String convertTo;
    private String number;

    private String convertFromValue;
    private String convertToValue;

    private static CurrencyTextManager instance;

    private CurrencyTextManager(){
        mLogger = Logger.getLoggerInstance();
    }

    public static CurrencyTextManager getInstance(){
        if (instance == null){
            instance = new CurrencyTextManager();
        }
        return instance;
    }

    @Override
    public void keywordsCollectorInit(String userInput) {
        //not for use here
    }

    @Override
    public HashMap<String, String> manageText(String userInput) {
        //not for use here
        return null;
    }

    /**
     *
     * @param userInput -> Important method. Gets the user input and extrapolates the three
     *                  key phrases neccessary for the currency operations to work. The method
     *                  calls the extractCurrencyKeywords method from the KeywordsExtractor class.
     */
    //Does not override it. Uses own manageText
    public void manageText(String userInput, TextManagementInterface tmi){
        mLogger.infoLog("CAPTURED_CATEGORY:", ">>>CURRENCY<<<");
        HashMap<String, String> finalResult = new HashMap<>();
        keywordsCollectorInit(userInput, ()->
                CurrencyConversion.getInstance().runConversion(number, convertFrom, convertTo,
                        (conNumber, errorCode) -> {
                    if (errorCode != null){
                        tmi.onDataCollected(null, errorCode);
                    }else{
                        finalResult.put(UnitConstants.CONVERT_FROM, convertFrom);
                        finalResult.put(UnitConstants.CONVERT_TO, convertTo);
                        finalResult.put(UnitConstants.NUMBER, number);
                        finalResult.put(UnitConstants.CONVERTED_NUMBER, String.valueOf(conNumber));
                        finalResult.put(UnitConstants.CONVERT_FROM_VALUE, convertFromValue);
                        finalResult.put(UnitConstants.CONVERT_TO_VALUE, convertToValue);
                        tmi.onDataCollected(finalResult, null);
                    }
        }));
    }

    //Does not override it. Uses own keyword collector
    public void keywordsCollectorInit(String userInput, KeywordsCollectInterface kci) {
        currencyKeywords = new HashMap<>();
        currencyKeywords = this.getKeywordsExtractorC().extractCurrencyKeywords(userInput, () -> {
            convertTo = currencyKeywords.get(UnitConstants.CONVERT_TO);
            convertFrom = currencyKeywords.get(UnitConstants.CONVERT_FROM);
            number = currencyKeywords.get(UnitConstants.NUMBER);
            convertFromValue = currencyKeywords.get(UnitConstants.CONVERT_FROM_VALUE);
            convertToValue = currencyKeywords.get(UnitConstants.CONVERT_TO_VALUE);
            kci.onKeywordsCollected();
        });
    }

    public interface KeywordsCollectInterface{
        void onKeywordsCollected();
    }

    public interface TextManagementInterface{
        void onDataCollected(HashMap<String, String> finalResultSet, String errorCode);
    }
}
