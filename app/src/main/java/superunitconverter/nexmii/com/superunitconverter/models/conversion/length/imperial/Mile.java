package superunitconverter.nexmii.com.superunitconverter.models.conversion.length.imperial;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Mile class. Has the logic needed to convert from the other length units to Mile and implements
 * the common convertTo method from the conversionStrategy interface
 */
public class Mile implements ConversionStrategy {

    private double convertedNumber = 0;

    /**
     * Method from the implemented interface which allows all the Unit classes to perform their
     * conversion logic
     * @param number -> The number from the String captured by the Notes-to-text logic
     * @param currentUnit -> The unit detected from the String captured by the Notes-to-text logic.
     *                    Needed for doing the correct conversion
     * @return returns the converted number
     */
    @Override
    public double convertTo(double number, String currentUnit) {
        if (UnitSingularPlural.FOOT_SOP(currentUnit))
            convertedNumber = footToMile(number);
        else if (UnitSingularPlural.INCH_SOP(currentUnit))
            convertedNumber = inchToMile(number);
        else if (UnitSingularPlural.YARD_SOP(currentUnit))
            convertedNumber = yardToMile(number);
        else if (UnitSingularPlural.CENTIMETER_SOP(currentUnit))
            convertedNumber = centimeterToMile(number);
        else if (UnitSingularPlural.KILOMETER_SOP(currentUnit))
            convertedNumber = kilometerToMile(number);
        else if (UnitSingularPlural.METER_SOP(currentUnit))
            convertedNumber = meterToMile(number);
        else if (UnitSingularPlural.MILLIMETER_SOP(currentUnit))
            convertedNumber = millimeterToMile(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit of the listed length units to mile/miles
     * Every method returns the converted number in double.
     */
    private double footToMile(double footNumber){
        return footNumber / 5280;
    }

    private double inchToMile(double inchNumber){
        return inchNumber / 63360;
    }

    private double yardToMile(double yardNumber){
        return yardNumber / 1760;
    }

    private double centimeterToMile(double centimeterNumber){
        return centimeterNumber / 160934;
    }

    private double kilometerToMile(double kilometerNumber){
        return kilometerNumber / 1.609;
    }

    private double meterToMile(double meterNumber){
        return meterNumber / 1609;
    }

    private double millimeterToMile(double millimeterNumber){
        return millimeterNumber / 1609_000;
    }

}
