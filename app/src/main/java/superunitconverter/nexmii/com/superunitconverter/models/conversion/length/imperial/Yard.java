package superunitconverter.nexmii.com.superunitconverter.models.conversion.length.imperial;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Yard class. Has the logic needed to convert from the other length units to yard and implements
 * the common convertTo method from the conversionStrategy interface
 */
public class Yard implements ConversionStrategy {

    private double convertedNumber = 0;

    /**
     * Method from the implemented interface which allowed all the Unit classes to perform their
     * conversion logic
     * @param number -> The number from the String captured by the Notes-to-text logic
     * @param currentUnit -> The unit detected from the String captured by the Notes-to-text logic
     * @return returns the converted number
     */
    @Override
    public double convertTo(double number, String currentUnit) {
        if (UnitSingularPlural.FOOT_SOP(currentUnit))
            convertedNumber = footToYard(number);
        else if (UnitSingularPlural.INCH_SOP(currentUnit))
            convertedNumber = inchToYard(number);
        else if (UnitSingularPlural.MILE_SOP(currentUnit))
            convertedNumber = mileToYard(number);
        else if (UnitSingularPlural.CENTIMETER_SOP(currentUnit))
            convertedNumber = centimeterToYard(number);
        else if (UnitSingularPlural.KILOMETER_SOP(currentUnit))
            convertedNumber = kilometerToYard(number);
        else if (UnitSingularPlural.METER_SOP(currentUnit))
            convertedNumber = meterToYard(number);
        else if (UnitSingularPlural.MILLIMETER_SOP(currentUnit))
            convertedNumber = millimeterToYard(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit from the listed length units to mile/miles
     * Every method returns the converted number in double.
     */
    private double footToYard(double footNumber){
        return footNumber / 3;
    }

    private double inchToYard(double inchNumber){
        return inchNumber / 36;
    }

    private double mileToYard(double mileNumber){
        return mileNumber * 1760;
    }

    private double centimeterToYard(double centimeterNumber){
        return centimeterNumber / 91.44;
    }

    private double kilometerToYard(double kilometerNumber){
        return kilometerNumber * 1094;
    }

    private double meterToYard(double meterNumber){
        return meterNumber * 1.094;
    }

    private double millimeterToYard(double millimeterNumber){
        return millimeterNumber / 914;
    }

}
