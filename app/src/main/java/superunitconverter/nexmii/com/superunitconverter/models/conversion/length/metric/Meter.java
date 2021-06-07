package superunitconverter.nexmii.com.superunitconverter.models.conversion.length.metric;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Meter class. Has the logic needed to convert from the other length units to Meter and implements
 * the common convertTo method from the conversion interface
 */
public class Meter implements ConversionStrategy {

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
            convertedNumber = footToMeter(number);
        else if (UnitSingularPlural.INCH_SOP(currentUnit))
            convertedNumber = inchToMeter(number);
        else if (UnitSingularPlural.MILE_SOP(currentUnit))
            convertedNumber = mileToMeter(number);
        else if (UnitSingularPlural.YARD_SOP(currentUnit))
            convertedNumber = yardToMeter(number);
        else if (UnitSingularPlural.CENTIMETER_SOP(currentUnit))
            convertedNumber = centimeterToMeter(number);
        else if (UnitSingularPlural.KILOMETER_SOP(currentUnit))
            convertedNumber = kilometerToMeter(number);
        else if (UnitSingularPlural.MILLIMETER_SOP(currentUnit))
            convertedNumber = millimeterToMeter(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion form any unit from the listed length units to
     * Meter/Meters.
     * Every method returns the converted number in double
     */
    private double footToMeter(double footNumber){
        return footNumber / 3.281;
    }

    private double inchToMeter(double inchNumber){
        return inchNumber / 39.37;
    }

    private double mileToMeter(double mileNumber){
        return mileNumber * 1609;
    }

    private double yardToMeter(double yardNumber){
        return yardNumber / 1.094;
    }

    private double centimeterToMeter(double centimeterNumber){
        return centimeterNumber / 100;
    }

    private double kilometerToMeter(double kilometerNumber){
        return kilometerNumber * 1000;
    }

    private double millimeterToMeter(double millimeterNumber){
        return millimeterNumber / 1000;
    }

}
