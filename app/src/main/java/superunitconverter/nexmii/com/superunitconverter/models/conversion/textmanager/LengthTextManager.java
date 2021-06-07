package superunitconverter.nexmii.com.superunitconverter.models.conversion.textmanager;

import java.util.HashMap;

import superunitconverter.nexmii.com.superunitconverter.models.ConversionContext;
import superunitconverter.nexmii.com.superunitconverter.models.ConversionResult;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.length.LengthContext;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.length.imperial.Foot;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.length.imperial.Inch;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.length.imperial.Mile;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.length.imperial.Yard;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.length.metric.Centimeter;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.length.metric.Kilometer;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.length.metric.Meter;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.length.metric.Millimeter;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitConstants;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitLists;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;
import superunitconverter.nexmii.com.superunitconverter.util.Logger;

/**
 * Singleton Length text manager class for handling all Length operations
 */
public final class LengthTextManager extends TextManager{

    private final Logger mLogger;
    private final UnitLists mUnitLists;
    private HashMap<String, String> lengthKeywords;

    private static final LengthTextManager instance = new LengthTextManager();

    private LengthTextManager(){
        mLogger = Logger.getLoggerInstance();
        mUnitLists = UnitLists.getInstance();
    }

    public static LengthTextManager getInstance(){
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
        lengthKeywords = new HashMap<>();
        lengthKeywords = this.getKeywordsExtractorM().exctractMeasurementKeywords(userInput, mUnitLists.getLengthUnitList());
    }

    /**
     *
     * @param userInput gets the userInput that was originally collected by ConversionController.
     *                  This method gets it and depending on the deduction on what unit it needs
     *                  to be converted to, it classifies it to that context and calls the method:
     *                  conversionToMillimeter(), conversionToMeter(), conversionToKilometer(),
     *                  conversionToCentimeter(), conversionToInch(), conversionToFoot(),
     *                  conversionToYard(), conversionToMile().
     *
     * @return -> returns a HashMap with String sets which is used by the CategoryManager class.
     */
    @Override
    public HashMap<String, String> manageText(String userInput) {
        mLogger.infoLog("CAPTURED_CATEGORY:", ">>>LENGTH<<<");
        HashMap<String, String> finalResultSet = new HashMap<>();
        keywordsCollectorInit(userInput);
        String convertTo = lengthKeywords.get(UnitConstants.CONVERT_TO);
        if (convertTo != null){
            if (UnitSingularPlural.MILLIMETER_SOP(convertTo))
                finalResultSet = conversionToMillimeter();
            else if (UnitSingularPlural.CENTIMETER_SOP(convertTo))
                finalResultSet = conversionToCentimeter();
            else if (UnitSingularPlural.METER_SOP(convertTo))
                finalResultSet = conversionToMeter();
            else if (UnitSingularPlural.KILOMETER_SOP(convertTo))
                finalResultSet = conversionToKilometer();
            else if (UnitSingularPlural.INCH_SOP(convertTo))
                finalResultSet = conversionToInch();
            else if (UnitSingularPlural.FOOT_SOP(convertTo))
                finalResultSet = conversionToFoot();
            else if (UnitSingularPlural.YARD_SOP(convertTo))
                finalResultSet = conversionToYard();
            else if (UnitSingularPlural.MILE_SOP(convertTo))
                finalResultSet = conversionToMile();
        }
        return finalResultSet;
    }

    private HashMap<String, String> conversionToMillimeter(){
        ConversionContext millimeterContext = new LengthContext(new Millimeter());
        return ConversionResult.makeResult(millimeterContext, lengthKeywords.get(UnitConstants.NUMBER)
                , lengthKeywords.get(UnitConstants.CONVERT_FROM), lengthKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToCentimeter(){
        ConversionContext centimeterContext = new LengthContext(new Centimeter());
        return ConversionResult.makeResult(centimeterContext, lengthKeywords.get(UnitConstants.NUMBER)
                , lengthKeywords.get(UnitConstants.CONVERT_FROM), lengthKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToMeter(){
        ConversionContext meterContext = new LengthContext(new Meter());
        return ConversionResult.makeResult(meterContext, lengthKeywords.get(UnitConstants.NUMBER)
                , lengthKeywords.get(UnitConstants.CONVERT_FROM), lengthKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToKilometer(){
        ConversionContext kilometerContext = new LengthContext(new Kilometer());
        return ConversionResult.makeResult(kilometerContext, lengthKeywords.get(UnitConstants.NUMBER)
                , lengthKeywords.get(UnitConstants.CONVERT_FROM), lengthKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToInch(){
        ConversionContext inchContext = new LengthContext(new Inch());
        return ConversionResult.makeResult(inchContext, lengthKeywords.get(UnitConstants.NUMBER)
                , lengthKeywords.get(UnitConstants.CONVERT_FROM), lengthKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToFoot(){
        ConversionContext footContext = new LengthContext(new Foot());
        return ConversionResult.makeResult(footContext, lengthKeywords.get(UnitConstants.NUMBER)
                , lengthKeywords.get(UnitConstants.CONVERT_FROM), lengthKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToYard(){
        ConversionContext yardContext = new LengthContext(new Yard());
        return ConversionResult.makeResult(yardContext, lengthKeywords.get(UnitConstants.NUMBER)
                , lengthKeywords.get(UnitConstants.CONVERT_FROM), lengthKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToMile(){
        ConversionContext mileContext = new LengthContext(new Mile());
        return ConversionResult.makeResult(mileContext, lengthKeywords.get(UnitConstants.NUMBER)
                , lengthKeywords.get(UnitConstants.CONVERT_FROM), lengthKeywords.get(UnitConstants.CONVERT_TO));
    }
}
