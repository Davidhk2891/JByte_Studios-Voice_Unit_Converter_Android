package superunitconverter.nexmii.com.superunitconverter.models.conversion.textmanager;

import java.util.HashMap;
import java.util.Objects;

import superunitconverter.nexmii.com.superunitconverter.models.ConversionContext;
import superunitconverter.nexmii.com.superunitconverter.models.ConversionResult;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitConstants;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitLists;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.volume.VolumeContext;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.volume.imperial.Cup;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.volume.imperial.FluidOunce;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.volume.imperial.Gallon;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.volume.imperial.Pint;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.volume.imperial.TableSpoon;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.volume.metric.CubicMeter;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.volume.metric.Liter;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.volume.metric.Milliliter;
import superunitconverter.nexmii.com.superunitconverter.util.Logger;

/**
 * Singleton Volume manager class for handling all Volume operations
 */
public final class VolumeTextManager extends TextManager{

    private final Logger mLogger;
    private final UnitLists mUnitLists;
    private HashMap<String, String> volumeKeywords;

    private static final VolumeTextManager instance = new VolumeTextManager();

    private VolumeTextManager(){
        mLogger = Logger.getLoggerInstance();
        mUnitLists = UnitLists.getInstance();
    }

    public static VolumeTextManager getInstance(){
        return instance;
    }

    /**
     *
     * @param userInput -> Important method. Gets the user input and extrapolates the three keywords
     *                  necessary for this app to work. The method calls extractKeywords method
     *                  from the KeywordsExtractor class.
     */
    @Override
    public void keywordsCollectorInit(String userInput) {
        volumeKeywords = new HashMap<>();
        volumeKeywords = this.getKeywordsExtractorM().exctractMeasurementKeywords(userInput, mUnitLists.getVolumeUnitList());
    }

    /**
     *
     * @param userInput gets the userInput that was originally collected by ConversionController.
     *                  This method gets it and depending on the deduction on what unit it needs to
     *                  be converted to, it classifies it to that context and calls the method:
     *                  conversionToCup(), conversionToFluidOunce(), conversionToGallon(),
     *                  conversionToPint(), conversionToTableSpoon(), conversionToCubicMeter()
     *                  conversionToLiter(), conversionToMilliliter().
     * @return -> returns a HashMap with String sets which is used by the CategoryManager class.
     */
    @Override
    public HashMap<String, String> manageText(String userInput) {
        mLogger.infoLog("CAPTURED_CATEGORY", ">>>VOLUME<<<");
        HashMap<String, String> finalResultSet = new HashMap<>();
        keywordsCollectorInit(userInput);
        String convertTo = volumeKeywords.get(UnitConstants.CONVERT_TO);
        if (convertTo != null){
            if (UnitSingularPlural.CUP_SOP(convertTo))
                finalResultSet = conversionToCup();
            else if (UnitSingularPlural.FLUID_OUNCE_SOP(convertTo))
                finalResultSet = conversionToFluidOunce();
            else if (UnitSingularPlural.GALLON_SOP(convertTo))
                finalResultSet = conversionToGallon();
            else if (UnitSingularPlural.PINT_SOP(convertTo))
                finalResultSet = conversionToPint();
            else if (UnitSingularPlural.TABLE_SPOON_SOP(convertTo))
                finalResultSet = conversionToTableSpoon();
            else if (UnitSingularPlural.CUBIC_METER_SOP(convertTo))
                finalResultSet = conversionToCubicMeter();
            else if (UnitSingularPlural.LITER_SOP(convertTo))
                finalResultSet = conversionToLiter();
            else if (UnitSingularPlural.MILLILITER_SOP(convertTo))
                finalResultSet = conversionToMilliliter();

            cubicMetersAdjust(finalResultSet);
        }
        return finalResultSet;
    }

    private void cubicMetersAdjust(HashMap<String, String> volumeKeywords){
        if (Objects.equals(volumeKeywords.get(UnitConstants.CONVERT_FROM), UnitConstants.CUBIC_METER))
            volumeKeywords.put(UnitConstants.CONVERT_FROM, UnitConstants.CUBIC_METERS_FULL);

        if (Objects.equals(volumeKeywords.get(UnitConstants.CONVERT_FROM), UnitConstants.CUBIC_METERS))
            volumeKeywords.put(UnitConstants.CONVERT_FROM, UnitConstants.CUBIC_METERS_FULL);

        if (Objects.equals(volumeKeywords.get(UnitConstants.CONVERT_TO), UnitConstants.CUBIC_METER))
            volumeKeywords.put(UnitConstants.CONVERT_TO, UnitConstants.CUBIC_METERS_FULL);

        if (Objects.equals(volumeKeywords.get(UnitConstants.CONVERT_TO), UnitConstants.CUBIC_METERS))
            volumeKeywords.put(UnitConstants.CONVERT_TO, UnitConstants.CUBIC_METERS_FULL);
    }

    private HashMap<String, String> conversionToCup(){
        ConversionContext cupContext = new VolumeContext(new Cup());
        return ConversionResult.makeResult(cupContext, volumeKeywords.get(UnitConstants.NUMBER)
                , volumeKeywords.get(UnitConstants.CONVERT_FROM), volumeKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToFluidOunce(){
        ConversionContext fluidOunceContext = new VolumeContext(new FluidOunce());
        return ConversionResult.makeResult(fluidOunceContext, volumeKeywords.get(UnitConstants.NUMBER)
                , volumeKeywords.get(UnitConstants.CONVERT_FROM), volumeKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToGallon(){
        ConversionContext gallonContext = new VolumeContext(new Gallon());
        return ConversionResult.makeResult(gallonContext, volumeKeywords.get(UnitConstants.NUMBER)
                , volumeKeywords.get(UnitConstants.CONVERT_FROM), volumeKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToPint(){
        ConversionContext pintContext = new VolumeContext(new Pint());
        return ConversionResult.makeResult(pintContext, volumeKeywords.get(UnitConstants.NUMBER)
                , volumeKeywords.get(UnitConstants.CONVERT_FROM), volumeKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToTableSpoon(){
        ConversionContext tableSpoonContext = new VolumeContext(new TableSpoon());
        return ConversionResult.makeResult(tableSpoonContext, volumeKeywords.get(UnitConstants.NUMBER)
                , volumeKeywords.get(UnitConstants.CONVERT_FROM), volumeKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToCubicMeter(){
        ConversionContext cubicMeterContext = new VolumeContext(new CubicMeter());
        return ConversionResult.makeResult(cubicMeterContext, volumeKeywords.get(UnitConstants.NUMBER)
                , volumeKeywords.get(UnitConstants.CONVERT_FROM), volumeKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToLiter(){
        ConversionContext literContext = new VolumeContext(new Liter());
        return ConversionResult.makeResult(literContext, volumeKeywords.get(UnitConstants.NUMBER)
                , volumeKeywords.get(UnitConstants.CONVERT_FROM), volumeKeywords.get(UnitConstants.CONVERT_TO));
    }

    private HashMap<String, String> conversionToMilliliter(){
        ConversionContext milliliterContext = new VolumeContext(new Milliliter());
        return ConversionResult.makeResult(milliliterContext, volumeKeywords.get(UnitConstants.NUMBER)
                , volumeKeywords.get(UnitConstants.CONVERT_FROM), volumeKeywords.get(UnitConstants.CONVERT_TO));
    }
}
