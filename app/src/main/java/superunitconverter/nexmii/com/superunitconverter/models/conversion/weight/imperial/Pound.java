package superunitconverter.nexmii.com.superunitconverter.models.conversion.weight.imperial;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Pound class. Has the logic needed to convert from the other weight units to Pounds and
 * implements the common convertTo method from the conversion interface
 */
public class Pound implements ConversionStrategy {

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
        if (UnitSingularPlural.OUNCE_SOP(currentUnit))
            convertedNumber = ounceToPound(number);
        else if (UnitSingularPlural.GRAM_SOP(currentUnit))
            convertedNumber = gramToPound(number);
        else if (UnitSingularPlural.KILOGRAM_SOP(currentUnit))
            convertedNumber = kilogramToPound(number);
        else if (UnitSingularPlural.MILLIGRAM_SOP(currentUnit))
            convertedNumber = milligramToPound(number);
        else if (UnitSingularPlural.TONNE_SOP(currentUnit))
            convertedNumber = tonneToPound(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit from the listed weight units to
     * Ounce / Ounces. Every method returns the converted number in double
     */

    private double ounceToPound(double ounceNumber){
        return ounceNumber / 16;
    }

    private double gramToPound(double gramNumber){
        return gramNumber / 454;
    }

    private double kilogramToPound(double kilogramNumber){
        return kilogramNumber * 2.205;
    }

    private double milligramToPound(double milligramNumber){
        return milligramNumber / 453_592;
    }

    private double tonneToPound(double tonneNumber){
        return tonneNumber * 2205;
    }

}
