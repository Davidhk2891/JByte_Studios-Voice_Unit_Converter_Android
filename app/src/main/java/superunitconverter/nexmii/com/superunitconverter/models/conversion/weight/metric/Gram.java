package superunitconverter.nexmii.com.superunitconverter.models.conversion.weight.metric;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Gram class. Has the logic needed to convert from the other weight units to Grams and
 * implements the common convertTo method from the conversion interface
 */
public class Gram implements ConversionStrategy {

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
            convertedNumber = ounceToGram(number);
        else if (UnitSingularPlural.POUND_SOP(currentUnit))
            convertedNumber = poundToGram(number);
        else if (UnitSingularPlural.KILOGRAM_SOP(currentUnit))
            convertedNumber = kilogramToGram(number);
        else if (UnitSingularPlural.MILLIGRAM_SOP(currentUnit))
            convertedNumber = milligramToGram(number);
        else if (UnitSingularPlural.TONNE_SOP(currentUnit))
            convertedNumber = tonneToGram(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit from the listed weight units to
     * Gram / Grams. Every method returns the converted number in double
     */

    private double ounceToGram(double ounceNumber){
        return ounceNumber * 28.35;
    }

    private double poundToGram(double poundNumber){
        return poundNumber * 454;
    }

    private double kilogramToGram(double kilogramNumber){
        return kilogramNumber * 1000;
    }

    private double milligramToGram(double milligramNumber){
        return milligramNumber / 1000;
    }

    private double tonneToGram(double tonneNumber){
        return tonneNumber * 1_000_000;
    }

}
