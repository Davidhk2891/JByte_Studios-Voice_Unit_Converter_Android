package superunitconverter.nexmii.com.superunitconverter.models.conversion.weight.metric;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Kilogram class. Has the logic needed to convert from the other weight units to Kilograms and
 * implements the common convertTo method from the conversion interface
 */
public class Kilogram implements ConversionStrategy {

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
            convertedNumber = ounceToKilogram(number);
        else if (UnitSingularPlural.POUND_SOP(currentUnit))
            convertedNumber = poundToKilogram(number);
        else if (UnitSingularPlural.GRAM_SOP(currentUnit))
            convertedNumber = gramToKilogram(number);
        else if (UnitSingularPlural.MILLIGRAM_SOP(currentUnit))
            convertedNumber = milligramToKilogram(number);
        else if (UnitSingularPlural.TONNE_SOP(currentUnit))
            convertedNumber = tonneToKilogram(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit from the listed weight units to
     * Kilogram / Kilograms. Every method returns the converted number in double
     */

    private double ounceToKilogram(double ounceNumber){
        return ounceNumber / 35.274;
    }

    private double poundToKilogram(double poundNumber){
        return poundNumber / 2.205;
    }

    private double gramToKilogram(double gramNumber){
        return gramNumber / 1000;
    }

    private double milligramToKilogram(double milligramNumber){
        return milligramNumber / 1_000_000;
    }

    private double tonneToKilogram(double tonneNumber){
        return tonneNumber * 1000;
    }

}
