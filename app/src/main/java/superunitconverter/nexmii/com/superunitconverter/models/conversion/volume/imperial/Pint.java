package superunitconverter.nexmii.com.superunitconverter.models.conversion.volume.imperial;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Pint class. Has the logic needed to convert from the other volume units to pint
 * and implements the common convertTo method from the conversion interface
 */
public class Pint implements ConversionStrategy {

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
        if (UnitSingularPlural.CUP_SOP(currentUnit))
            convertedNumber = cupToPint(number);
        else if (UnitSingularPlural.FLUID_OUNCE_SOP(currentUnit))
            convertedNumber = fluidOunceToPint(number);
        else if (UnitSingularPlural.GALLON_SOP(currentUnit))
            convertedNumber = gallonToPint(number);
        else if (UnitSingularPlural.TABLE_SPOON_SOP(currentUnit))
            convertedNumber = tableSpoonToPint(number);
        else if (UnitSingularPlural.CUBIC_METER_SOP(currentUnit))
            convertedNumber = cubicMeterToPint(number);
        else if (UnitSingularPlural.LITER_SOP(currentUnit))
            convertedNumber = literToPint(number);
        else if (UnitSingularPlural.MILLILITER_SOP(currentUnit))
            convertedNumber = milliliterToPint(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit from the listed volume units to
     * Pint/Pints every method returns the converted number in double.
     */

    private double cupToPint(double cupNumber){
        return cupNumber / 1.972;
    }

    private double fluidOunceToPint(double fluidOunceNumber){
        return fluidOunceNumber /16;
    }

    private double gallonToPint(double gallonNumber){
        return gallonNumber * 9.608;
    }

    private double tableSpoonToPint(double tableSpoonNumber){
        return tableSpoonNumber /32;
    }

    private double cubicMeterToPint(double cubicMeterNumber){
        return cubicMeterNumber * 2113;
    }

    private double literToPint(double literNumber){
        return literNumber * 2.113;
    }

    private double milliliterToPint(double milliliterNumber){
        return milliliterNumber / 473;
    }

}
