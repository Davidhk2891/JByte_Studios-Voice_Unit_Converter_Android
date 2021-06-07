package superunitconverter.nexmii.com.superunitconverter.models.conversion.length.metric;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Millimeter class. Has the logic needed to convert from the other length units to Millimeter and
 * implements the common convertTo method from the conversion interface
 */
public class Millimeter implements ConversionStrategy {

    private double convertedNumber = 0;

    /**
     * Method from the implemented interface which allowed all the Unit classes to perform their
     * conversion logic.
     * @param number -> The number from the String captured by the Notes-to-text logic
     * @param currentUnit -> The unit detected from the String captured by the Notes-to-text logic
     * @return returns the converted number
     */
    @Override
    public double convertTo(double number, String currentUnit) {
        if (UnitSingularPlural.FOOT_SOP(currentUnit))
            convertedNumber = footToMillimeter(number);
        else if (UnitSingularPlural.INCH_SOP(currentUnit))
            convertedNumber = inchToMillimeter(number);
        else if (UnitSingularPlural.MILE_SOP(currentUnit))
            convertedNumber = mileToMillimeter(number);
        else if (UnitSingularPlural.YARD_SOP(currentUnit))
            convertedNumber = yardToMillimeter(number);
        else if (UnitSingularPlural.CENTIMETER_SOP(currentUnit))
            convertedNumber = centimeterToMillimeter(number);
        else if (UnitSingularPlural.KILOMETER_SOP(currentUnit))
            convertedNumber = kilometerToMillimeter(number);
        else if (UnitSingularPlural.METER_SOP(currentUnit))
            convertedNumber = meterToMillimeter(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit from the listed length units to
     * Millimeter/Millimeters.
     * Every method returns the converted number in double
     */
    private double footToMillimeter(double footNumber){
        return footNumber * 305;
    }

    private double inchToMillimeter(double inchNumber){
        return inchNumber * 25.4;
    }

    private double mileToMillimeter(double mileNumber){
        return mileNumber * 1_609_000;
    }

    private double yardToMillimeter(double yardToMillimeter){
        return yardToMillimeter * 914;
    }

    private double centimeterToMillimeter(double centimeterNumber){
        return centimeterNumber * 10;
    }

    private double kilometerToMillimeter(double kilometerNumber){
        return kilometerNumber * 1_000_000;
    }

    private double meterToMillimeter(double meterNumber){
        return meterNumber * 1000;
    }

}
