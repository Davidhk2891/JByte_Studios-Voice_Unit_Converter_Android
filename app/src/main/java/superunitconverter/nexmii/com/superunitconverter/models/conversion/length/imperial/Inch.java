package superunitconverter.nexmii.com.superunitconverter.models.conversion.length.imperial;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Inch class. Has the logic needed to convert from the other length units to Inch and implements
 * the common convertTo method from the conversionStrategy interface
 */
public class Inch implements ConversionStrategy {

    private double convertedNumber = 0;

    /**
     * Method from the implemented interface which allows all the Unit classes to perform their
     * conversion logic
     * @param number -> The number from the String captured by the Notes-to-text logic
     * @param currentUnit -> The unit detected from the String captured by the Notes-to-text
     *                   logic. Needed for doing the correct conversion
     * @return returns the converted number
     */
    @Override
    public double convertTo(double number, String currentUnit) {
        if (UnitSingularPlural.FOOT_SOP(currentUnit))
            convertedNumber = footToInch(number);
        else if (UnitSingularPlural.MILE_SOP(currentUnit))
            convertedNumber = mileToInch(number);
        else if (UnitSingularPlural.YARD_SOP(currentUnit))
            convertedNumber = yardToInch(number);
        else if (UnitSingularPlural.CENTIMETER_SOP(currentUnit))
            convertedNumber = centimeterToInch(number);
        else if (UnitSingularPlural.KILOMETER_SOP(currentUnit))
            convertedNumber = kilometerToInch(number);
        else if (UnitSingularPlural.METER_SOP(currentUnit))
            convertedNumber = meterToInch(number);
        else if (UnitSingularPlural.MILLIMETER_SOP(currentUnit))
            convertedNumber = millimeterToInch(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any of the listed length units to inch/inches
     * Every method returns the converted number in double.
     */

    private Double footToInch(double footNumber){
        return footNumber * 12;
    }

    private Double mileToInch(double mileNumber){
        return mileNumber * 63360;
    }

    private Double yardToInch(double yardNumber){
        return yardNumber * 36;
    }

    private Double centimeterToInch(double centimeterNumber){
        return centimeterNumber / 2.54;
    }

    private Double kilometerToInch(double kilometerNumber){
        return kilometerNumber * 39370;
    }

    private Double meterToInch(double meterNumber){
        return meterNumber * 39.37;
    }

    private Double millimeterToInch(double millimeterNumber){
        return millimeterNumber / 25.4;
    }

}
