package superunitconverter.nexmii.com.superunitconverter.models.conversion.weight.metric;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Tonne class. Has the logic needed to convert from the other weight units to Tonnes and
 * implements the common convertTo method from the conversion interface
 */
public class Tonne implements ConversionStrategy {

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
            convertedNumber = ounceToTonne(number);
        else if (UnitSingularPlural.POUND_SOP(currentUnit))
            convertedNumber = poundToTonne(number);
        else if (UnitSingularPlural.GRAM_SOP(currentUnit))
            convertedNumber = gramToTonne(number);
        else if (UnitSingularPlural.KILOGRAM_SOP(currentUnit))
            convertedNumber = kilogramToTonne(number);
        else if (UnitSingularPlural.MILLIGRAM_SOP(currentUnit))
            convertedNumber = milligramToTonne(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit from the listed weight units to
     * Tonne / Tonnes. Every method returns the converted number in double
     */

    private double ounceToTonne(double ounceNumber){
        return ounceNumber / 35274;
    }

    private double poundToTonne(double poundNumber){
        return poundNumber / 2205;
    }

    private double gramToTonne(double gramNumber){
        return gramNumber / 1_000_000;
    }

    private double kilogramToTonne(double kilogramNumber){
        return kilogramNumber / 1000;
    }

    private double milligramToTonne(double milligramNumber){
        return  milligramNumber / 1_000_000_000;
    }

}
