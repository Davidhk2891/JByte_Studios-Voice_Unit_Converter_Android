package superunitconverter.nexmii.com.superunitconverter.models.conversion.length.metric;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Kilometer class. Has the logic needed to convert from the other length units to Centimeter and
 * implements the common convertTo method from the conversion interface
 */
public class Kilometer implements ConversionStrategy {

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
            convertedNumber = footToKilometer(number);
        else if (UnitSingularPlural.INCH_SOP(currentUnit))
            convertedNumber = inchToKilometer(number);
        else if (UnitSingularPlural.MILE_SOP(currentUnit))
            convertedNumber = mileToKilometer(number);
        else if (UnitSingularPlural.YARD_SOP(currentUnit))
            convertedNumber = yardToKilometer(number);
        else if (UnitSingularPlural.CENTIMETER_SOP(currentUnit))
            convertedNumber = centimeterToKilometer(number);
        else if (UnitSingularPlural.METER_SOP(currentUnit))
            convertedNumber = meterToKilometer(number);
        else if (UnitSingularPlural.MILLIMETER_SOP(currentUnit))
            convertedNumber = millimeterToKilometer(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit from the listed length units to
     * Kilometer/Kilometers.
     * Every method returns the converted number in double.
     */
    private double footToKilometer(double footNumber){
        return footNumber / 3281;
    }

    private double inchToKilometer(double inchNumber){
        return inchNumber / 39370;
    }

    private double mileToKilometer(double mileNumber){
        return mileNumber * 1.609;
    }

    private double yardToKilometer(double yardNumber){
        return yardNumber / 1094;
    }

    private double centimeterToKilometer(double centimeterNumber){
        return centimeterNumber / 100_000;
    }

    private double meterToKilometer(double meterNumber){
        return meterNumber / 1000;
    }

    private double millimeterToKilometer(double millimeterNumber){
        return millimeterNumber / 1_000_000;
    }

}
