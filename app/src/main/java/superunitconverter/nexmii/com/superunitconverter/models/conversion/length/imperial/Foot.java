package superunitconverter.nexmii.com.superunitconverter.models.conversion.length.imperial;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Foot class. Has the logic needed to convert from the other length units to Foot and implements
 * the common convertTo method from the conversionStrategy interface
 */
public class Foot implements ConversionStrategy {

    private double convertedNumber = 0;

    /**
     * Method from the implemented interface which allows all the Unit classes to perform their conversion
     * logic
     * @param number -> The number from the String captured by the Notes-to-text logic that needs
     *               to be converted
     * @param currentUnit -> The unit detected from the String captured by the Notes-to-text logic.
     *                    Needed for doing the correct conversion
     * @return returns the converted number
     */
    @Override
    public double convertTo(double number, String currentUnit) {
        if (UnitSingularPlural.MILLIMETER_SOP(currentUnit))
            convertedNumber = millimeterToFoot(number);
        else if (UnitSingularPlural.CENTIMETER_SOP(currentUnit))
            convertedNumber = centimeterToFoot(number);
        else if (UnitSingularPlural.METER_SOP(currentUnit))
            convertedNumber = meterToFoot(number);
        else if (UnitSingularPlural.KILOMETER_SOP(currentUnit))
            convertedNumber = kilometerToFoot(number);
        else if (UnitSingularPlural.INCH_SOP(currentUnit))
            convertedNumber = inchToFoot(number);
        else if (UnitSingularPlural.YARD_SOP(currentUnit))
            convertedNumber = yardToFoot(number);
        else if (UnitSingularPlural.MILE_SOP(currentUnit))
            convertedNumber = mileToFoot(number);

        return convertedNumber;
    }

    /**
     * All methods for performing the conversions from any of the listed length units to foot/feet
     * Every method returns the converted number in double.
     */
    private double inchToFoot(double inchNumber){
        return inchNumber / 12;
    }

    private double mileToFoot(double mileNumber){
        return mileNumber * 5280;
    }

    private double yardToFoot(double yardNumber){
        return yardNumber * 3;
    }

    private double centimeterToFoot(double centimeterNumber){
        return centimeterNumber / 30.48;
    }

    private double kilometerToFoot(double kilometerNumber){
        return kilometerNumber * 3281;
    }

    private double meterToFoot(double meterNumber){
        return meterNumber * 3.281;
    }

    private double millimeterToFoot(double millimeterNumber){
        return millimeterNumber / 305;
    }
}
