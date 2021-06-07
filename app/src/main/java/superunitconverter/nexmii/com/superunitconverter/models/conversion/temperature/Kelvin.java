package superunitconverter.nexmii.com.superunitconverter.models.conversion.temperature;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Kelvin class. Has the logic needed to convert from the other two temperature units to Kelvin
 * and implements the common convertTo method from the conversionStrategy interface
 */
public class Kelvin implements ConversionStrategy {

    private double convertedNumber = 0;

    /**
     * Method from the implemented interface which allows all Unit classes to perform their conversion
     * logic
     * @param currentNumber - The number that needs to be converted
     * @param currentUnit -> The unit detected from the String captured by the Notes-to-text logic
     * @return returns the converted number
     */
    @Override
    public double convertTo(double currentNumber, String currentUnit) {
        if (UnitSingularPlural.CELSIUS_SOP(currentUnit))
            convertedNumber = celciusToKelvin(currentNumber);
        else if (UnitSingularPlural.FAHRENHEIT_SOP(currentUnit))
            convertedNumber = fahrenheitToKelvin(currentNumber);

        return convertedNumber;
    }

    /**
     * Arithmetic operations for doing the Celcius -> Kelvin conversion
     * @param celciusNumber -> The number is treated as Celcius in the equation
     * @return returns the Celcius equivalent in Kelvin
     */
    private double celciusToKelvin(double celciusNumber){
        //23.54°C + 273.15 = 296.69K
        return celciusNumber + 273.15;
    }

    /**
     * Arithmetic operations for doing the Fahreinheit -> Kelvin conversion
     * @param fahrenheitNumber -> The number is treated as Fahreinheit in the equation
     * @return returns the Fahrenheit equivalent in Kelvin
     */
    private double fahrenheitToKelvin(double fahrenheitNumber){
        //(23.54°F − 32) × 5/9 + 273.15 = 268.45K
        return (fahrenheitNumber - 32) * (5.0/9.0) + 273.15;
    }
}
