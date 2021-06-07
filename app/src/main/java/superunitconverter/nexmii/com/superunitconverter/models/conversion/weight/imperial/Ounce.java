package superunitconverter.nexmii.com.superunitconverter.models.conversion.weight.imperial;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Ounce class. Has the logic needed to convert from the other weight units to Ounces and
 * implements the common convertTo method from the conversion interface
 */
public class Ounce implements ConversionStrategy {

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
        if (UnitSingularPlural.POUND_SOP(currentUnit))
            convertedNumber = poundToOunce(number);
        else if (UnitSingularPlural.GRAM_SOP(currentUnit))
            convertedNumber = gramToOunce(number);
        else if (UnitSingularPlural.KILOGRAM_SOP(currentUnit))
            convertedNumber = kilogramToOunce(number);
        else if (UnitSingularPlural.MILLIGRAM_SOP(currentUnit))
            convertedNumber = milligramToOunce(number);
        else if (UnitSingularPlural.TONNE_SOP(currentUnit))
            convertedNumber = tonneToOunce(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit from the listed weight units to
     * Ounce / Ounces. Every method returns the converted number in double
     */

    private double poundToOunce(double poundNumber){
        return poundNumber * 16;
    }

    private double gramToOunce(double gramNumber){
        return gramNumber / 28.35;
    }

    private double kilogramToOunce(double kilogramNumber){
        return kilogramNumber * 35.274;
    }

    private double milligramToOunce(double milligramNumber){
        return milligramNumber / 28350;
    }

    private double tonneToOunce(double tonneNumber){
        return tonneNumber * 35274;
    }

}
