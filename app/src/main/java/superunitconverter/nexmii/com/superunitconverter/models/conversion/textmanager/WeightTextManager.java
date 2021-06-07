package superunitconverter.nexmii.com.superunitconverter.models.conversion.textmanager;

import java.util.HashMap;

import superunitconverter.nexmii.com.superunitconverter.models.ConversionContext;
import superunitconverter.nexmii.com.superunitconverter.models.ConversionResult;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitConstants;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitLists;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.weight.WeightContext;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.weight.imperial.Ounce;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.weight.imperial.Pound;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.weight.metric.Gram;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.weight.metric.Kilogram;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.weight.metric.Milligram;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.weight.metric.Tonne;
import superunitconverter.nexmii.com.superunitconverter.util.Logger;

/**
 * Singleton Weight manager class for handling all weight operations
 */
public final class WeightTextManager extends TextManager{

    private final Logger mLogger;
    private final UnitLists mUnitLists;
    private HashMap<String, String> weightKeywords;

    private static final WeightTextManager instance = new WeightTextManager();

    private WeightTextManager(){
        mLogger = Logger.getLoggerInstance();
        mUnitLists = UnitLists.getInstance();
    }

    public static WeightTextManager getInstance(){
        return instance;
    }

    /**
     *
     * @param userInput -> Important method. Gets the user input and extrapolates the three keywords
     *                  necessary for this app to work. The method calls the extractKeywords method
     *                  from the KeywordsExtractor class.
     */
    @Override
    public void keywordsCollectorInit(String userInput) {
        weightKeywords = new HashMap<>();
        weightKeywords = this.getKeywordsExtractorM().exctractMeasurementKeywords(userInput, mUnitLists.getWeightUnitList());
    }

    /**
     *
     * @param userInput gets the userInput that was originally collected by ConversionController.
     *                  This method gets it and depending on the deduction on what unit it needs
     *                  to be converted to, it classifies it to that context and calls the method:
     *                  conversionToMilligram(), conversionToGram(), conversionToKilogram(),
     *                  conversionToTonne(), conversionToUSTon(), conversionToOunce(),
     *                  conversionToPound().
     * @return -> returns a HashMap with String sets which is used by the CategoryManager class.
     */
    @Override
    public HashMap<String, String> manageText(String userInput) {
        mLogger.infoLog("CAPTURED_CATEGORY:", ">>>WEIGHT<<<");
        HashMap<String, String> finalResultSet = new HashMap<>();
        keywordsCollectorInit(userInput);
        String convertTo = weightKeywords.get(UnitConstants.CONVERT_TO);
        if (convertTo != null){
            if (UnitSingularPlural.OUNCE_SOP(convertTo))
                finalResultSet = conversionToOunce();
            else if (UnitSingularPlural.POUND_SOP(convertTo))
                finalResultSet = conversionToPound();
            else if (UnitSingularPlural.GRAM_SOP(convertTo))
                finalResultSet = conversionToGram();
            else if (UnitSingularPlural.KILOGRAM_SOP(convertTo))
                finalResultSet = conversionToKilogram();
            else if (UnitSingularPlural.MILLIGRAM_SOP(convertTo))
                finalResultSet = conversionToMilligram();
            else if (UnitSingularPlural.TONNE_SOP(convertTo))
                finalResultSet = conversionToTonne();
        }
        return finalResultSet;
    }

    private HashMap<String, String> conversionToOunce(){
        ConversionContext ounceContext = new WeightContext(new Ounce());
        return ConversionResult.makeResult(ounceContext, weightKeywords.get(UnitConstants.NUMBER)
                , weightKeywords.get(UnitConstants.CONVERT_FROM), weightKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToPound(){
        ConversionContext poundContext = new WeightContext(new Pound());
        return ConversionResult.makeResult(poundContext, weightKeywords.get(UnitConstants.NUMBER)
                , weightKeywords.get(UnitConstants.CONVERT_FROM), weightKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToGram(){
        ConversionContext gramContext = new WeightContext(new Gram());
        return ConversionResult.makeResult(gramContext, weightKeywords.get(UnitConstants.NUMBER)
                , weightKeywords.get(UnitConstants.CONVERT_FROM), weightKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToKilogram(){
        ConversionContext kilogramContext = new WeightContext(new Kilogram());
        return ConversionResult.makeResult(kilogramContext, weightKeywords.get(UnitConstants.NUMBER)
                , weightKeywords.get(UnitConstants.CONVERT_FROM), weightKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToMilligram(){
        ConversionContext milligramContext = new WeightContext(new Milligram());
        return ConversionResult.makeResult(milligramContext, weightKeywords.get(UnitConstants.NUMBER)
                , weightKeywords.get(UnitConstants.CONVERT_FROM), weightKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToTonne(){
        ConversionContext tonneContext = new WeightContext(new Tonne());
        return ConversionResult.makeResult(tonneContext, weightKeywords.get(UnitConstants.NUMBER)
                , weightKeywords.get(UnitConstants.CONVERT_FROM), weightKeywords.get(UnitConstants.CONVERT_TO));
    }
}
