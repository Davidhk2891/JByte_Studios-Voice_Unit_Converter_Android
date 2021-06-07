package superunitconverter.nexmii.com.superunitconverter.models.conversion.weight.metric;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Milligram class. Has the logic needed to convert from the other weight units to Milligrams and
 * implements the common convertTo method from the conversion interface
 */
public class Milligram implements ConversionStrategy {

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
            convertedNumber = ounceToMilligram(number);
        else if (UnitSingularPlural.POUND_SOP(currentUnit))
            convertedNumber = poundToMilligram(number);
        else if (UnitSingularPlural.GRAM_SOP(currentUnit))
            convertedNumber = gramToMilligram(number);
        else if (UnitSingularPlural.KILOGRAM_SOP(currentUnit))
            convertedNumber = kilogramToMilligram(number);
        else if (UnitSingularPlural.TONNE_SOP(currentUnit))
            convertedNumber = tonneToMilligram(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit from the listed weight units to
     * Milligram / Milligrams. Every method returns the converted number in double
     */

    private double ounceToMilligram(double ounceNumber){
        return ounceNumber * 28350;
    }

    private double poundToMilligram(double poundNumber){
        return poundNumber * 453592;
    }

    private double gramToMilligram(double gramNumber){
        return gramNumber * 1000;
    }

    private double kilogramToMilligram(double kilogramNumber){
        return kilogramNumber * 1_000_000;
    }

    private double tonneToMilligram(double tonneNumber){
        return tonneNumber * 1_000_000_000;
    }

}
