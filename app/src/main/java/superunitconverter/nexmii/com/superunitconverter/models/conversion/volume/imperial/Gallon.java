package superunitconverter.nexmii.com.superunitconverter.models.conversion.volume.imperial;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Gallon class. Has the logic needed to convert from the other volume units to Gallon
 * and implements the common convertTo method from the conversion interface
 */
public class Gallon implements ConversionStrategy {

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
            convertedNumber = cupToGallon(number);
        else if (UnitSingularPlural.FLUID_OUNCE_SOP(currentUnit))
            convertedNumber = fluidOunceToGallon(number);
        else if (UnitSingularPlural.PINT_SOP(currentUnit))
            convertedNumber = pintToGallon(number);
        else if (UnitSingularPlural.TABLE_SPOON_SOP(currentUnit))
            convertedNumber = tableSpoonToGallon(number);
        else if (UnitSingularPlural.CUBIC_METER_SOP(currentUnit))
            convertedNumber = cubicMeterToGallon(number);
        else if (UnitSingularPlural.LITER_SOP(currentUnit))
            convertedNumber = literToGallon(number);
        else if (UnitSingularPlural.MILLILITER_SOP(currentUnit))
            convertedNumber = milliliterToGallon(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit from the listed volume units to
     * Gallon/Gallons every method returns the converted number in double.
     */

    private double cupToGallon(double cupNumber){
        return cupNumber / 18.942;
    }

    private double fluidOunceToGallon(double fluidOunceNumber){
        return fluidOunceNumber / 154;
    }

    private double pintToGallon(double pintNumber){
        return pintNumber / 9.608; //US liquid pint
    }

    private double tableSpoonToGallon(double tableSpoonNumber){
        return tableSpoonNumber / 307;
    }

    private double cubicMeterToGallon(double cubicMeterNumber){
        return cubicMeterNumber * 220;
    }

    private double literToGallon(double literNumber){
        return literNumber / 4.546;
    }

    private double milliliterToGallon(double milliliterNumber){
        return  milliliterNumber / 4546;
    }

}
