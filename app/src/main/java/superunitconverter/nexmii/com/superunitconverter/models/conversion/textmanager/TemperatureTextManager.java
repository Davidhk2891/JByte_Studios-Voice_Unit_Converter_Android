package superunitconverter.nexmii.com.superunitconverter.models.conversion.textmanager;

import java.util.HashMap;

import superunitconverter.nexmii.com.superunitconverter.models.ConversionContext;
import superunitconverter.nexmii.com.superunitconverter.models.ConversionResult;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.temperature.Celcius;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.temperature.Fahreinheit;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.temperature.Kelvin;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.temperature.TemperatureContext;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitConstants;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitLists;
import superunitconverter.nexmii.com.superunitconverter.util.Logger;

/**
 * Singleton Temperature text manager class for handling all Temperature operations
 */
public final class TemperatureTextManager extends TextManager{

    private final Logger mLogger;
    private final UnitLists mUnitLists;
    private HashMap<String, String> tempKeywords;

    private static final TemperatureTextManager instance = new TemperatureTextManager();

    private TemperatureTextManager(){
        mLogger = Logger.getLoggerInstance();
        mUnitLists = UnitLists.getInstance();
    }

    public static TemperatureTextManager getInstance(){
        return instance;
    }

    /**
     *
     * @param userInput -> Impotant method. Gets the user input and extrapolates the three keywords
     *                  necessary for this app to work. The method calls the extractKeywords method
     *                  from the KeywordsExtractor class.
     */
    @Override
    public void keywordsCollectorInit(String userInput) {
        tempKeywords = new HashMap<>();
        tempKeywords = this.getKeywordsExtractorM().exctractMeasurementKeywords(userInput, mUnitLists.getTempUnitList());
    }

    /**
     *
     * @param userInput gets the userInput that was originally collected by ConversionController.
     *                  This method gets it and depending on the deduction on what unit it needs to
     *                  be converted to, it classifies it to that context and calls the method:
     *                  conversionToCelcius(), conversionToFahreinheit() or conversionToKelvin().
     * @return -> returns a HashMap with String sets which is used by the CategoryManager class
     */
    @Override
    public HashMap<String, String> manageText(String userInput) {
        mLogger.infoLog("CAPTURED_CATEGORY:", ">>>TEMPERATURE<<<");
        HashMap<String, String> finalResultSet = new HashMap<>();
        keywordsCollectorInit(userInput);
        String convertTo = tempKeywords.get(UnitConstants.CONVERT_TO);
        if (convertTo != null) {
            if (convertTo.equalsIgnoreCase(UnitConstants.CELSIUS))
                finalResultSet = conversionToCelcius();
            else if (convertTo.equalsIgnoreCase(UnitConstants.FAHRENHEIT))
                finalResultSet = conversionToFahreinheit();
            else if (convertTo.equalsIgnoreCase(UnitConstants.KELVIN))
                finalResultSet = conversionToKelvin();
        }
        return finalResultSet;
    }

    private HashMap<String, String> conversionToCelcius(){
        ConversionContext celciusContext = new TemperatureContext(new Celcius());
        return ConversionResult.makeResult(celciusContext, tempKeywords.get(UnitConstants.NUMBER)
                ,tempKeywords.get(UnitConstants.CONVERT_FROM), tempKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToFahreinheit(){
        ConversionContext fahreinheitContext = new TemperatureContext(new Fahreinheit());
        return ConversionResult.makeResult(fahreinheitContext, tempKeywords.get(UnitConstants.NUMBER)
                ,tempKeywords.get(UnitConstants.CONVERT_FROM), tempKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToKelvin(){
        ConversionContext kelvinContext = new TemperatureContext(new Kelvin());
        return ConversionResult.makeResult(kelvinContext, tempKeywords.get(UnitConstants.NUMBER)
                ,tempKeywords.get(UnitConstants.CONVERT_FROM), tempKeywords.get(UnitConstants.CONVERT_TO));
    }
}
