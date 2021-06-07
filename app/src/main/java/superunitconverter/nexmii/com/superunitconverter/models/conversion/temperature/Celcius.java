package superunitconverter.nexmii.com.superunitconverter.models.conversion.temperature;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Celcius class. Has the logic needed to convert from the other two temperature units to celcius
 * and implements the common convertTo method from the conversionStrategy interface
 */
public class Celcius implements ConversionStrategy {

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
        if (UnitSingularPlural.FAHRENHEIT_SOP(currentUnit))
            convertedNumber = fahreinheitToCelcius(currentNumber);
        else if (UnitSingularPlural.KELVIN_SOP(currentUnit))
            convertedNumber = kelvinToCelcius(currentNumber);

        return convertedNumber;
    }

    /**
     * Arithmetic operations for doing the Fahreinheit -> Celcius conversion
     * @param fahreinheitNumber -> The number is treated as Fahreinheit in the equation
     * @return returns the Fahreinheit equivalent in Celcius
     */
    private double fahreinheitToCelcius(double fahreinheitNumber){
        return (fahreinheitNumber - 32) * 5/9;
    }

    /**
     * Arithmetic operations for doing the Kelvin -> Celcius conversion
     * @param kelvinNumber -> The number is treated as Kelvin in the equation
     * @return returns the Kelvin equivalent in Celcius
     */
    private double kelvinToCelcius(double kelvinNumber){
        return kelvinNumber - 273.15;
    }
}
