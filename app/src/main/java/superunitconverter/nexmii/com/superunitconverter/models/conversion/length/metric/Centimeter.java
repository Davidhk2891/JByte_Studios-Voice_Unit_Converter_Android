package superunitconverter.nexmii.com.superunitconverter.models.conversion.length.metric;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Centimeter class. Has the logic needed to convert from the other length units to Centimeter and
 * implements the common convertTo method from the conversion interface
 */
public class Centimeter implements ConversionStrategy {

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
            convertedNumber = footToCentimeter(number);
        if (UnitSingularPlural.INCH_SOP(currentUnit))
            convertedNumber = inchToCentimeter(number);
        if (UnitSingularPlural.MILE_SOP(currentUnit))
            convertedNumber = mileToCentimeter(number);
        if (UnitSingularPlural.YARD_SOP(currentUnit))
            convertedNumber = yardToCentimeter(number);
        if (UnitSingularPlural.KILOMETER_SOP(currentUnit))
            convertedNumber = kilometerToCentimeter(number);
        if (UnitSingularPlural.METER_SOP(currentUnit))
            convertedNumber = meterToCentimeter(number);
        if (UnitSingularPlural.MILLIMETER_SOP(currentUnit))
            convertedNumber = millimeterToCentimeter(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit from the listed length units to
     * Centimeter/Centimeters every method returns the converted number in double.
     */
    private double footToCentimeter(double footNumber){
           return footNumber * 30.48;
    }

    private double inchToCentimeter(double inchNumber){
        return inchNumber * 2.54;
    }

    private double mileToCentimeter(double mileNumber){
        return mileNumber * 160_934;
    }

    private double yardToCentimeter(double yardNumber){
        return yardNumber * 91.44;
    }

    private double kilometerToCentimeter(double kilometerNumber){
        return kilometerNumber * 100_000;
    }

    private double meterToCentimeter(double meterNumber){
        return meterNumber * 100;
    }

    private double millimeterToCentimeter(double millimeterNumber){
        return millimeterNumber / 10;
    }

}
