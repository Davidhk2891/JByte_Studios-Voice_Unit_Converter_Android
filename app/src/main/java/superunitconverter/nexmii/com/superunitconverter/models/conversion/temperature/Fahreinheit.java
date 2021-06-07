package superunitconverter.nexmii.com.superunitconverter.models.conversion.temperature;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Fahreinheit class. Has the logic needed to convert from the other two temperature units to Fahrenheit
 * and implements the common convertTo method from the conversionStrategy interface
 */
public class Fahreinheit implements ConversionStrategy {

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
            convertedNumber = celciusToFahrenheit(currentNumber);
        else if (UnitSingularPlural.KELVIN_SOP(currentUnit))
            convertedNumber = kelvinToFahrenheit(currentNumber);

        return convertedNumber;
    }

    /**
     * Arithmetic operations for doing the Celcius -> Fahrenheit conversion
     * @param celciusNumber -> The number is treated as Celcius in the equation
     * @return returns the Celcius equivalent in Fahrenheit
     */
    private double celciusToFahrenheit(double celciusNumber){
        //(23.54°C × 9/5) + 32 = 74.372°F
        return (celciusNumber * 9.0/5.0) + 32;
    }

    /**
     * Arithmetic operations for doing the Kelvin -> Fahrenheit conversion
     * @param kelvinNumber -> The number is treated as Kelvin in the equation
     * @return returns the Kelvin equivalent in Fahrenheit
     */
    private double kelvinToFahrenheit(double kelvinNumber){
        //(23.54K − 273.15) × 9/5 + 32 = -417.3°F
        return (kelvinNumber - 273.15) * 9.0/5.0 + 32;
    }

}
