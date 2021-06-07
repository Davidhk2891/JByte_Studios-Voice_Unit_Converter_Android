package superunitconverter.nexmii.com.superunitconverter.models.conversion.volume.metric;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Milliliter class. Has the logic needed to convert from the other volume units to milliliter
 * and implements the common convertTo method from the conversion interface
 */
public class Milliliter implements ConversionStrategy {

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
            convertedNumber = cupToMilliliter(number);
        else if (UnitSingularPlural.FLUID_OUNCE_SOP(currentUnit))
            convertedNumber = fluidOunceToMilliliter(number);
        else if (UnitSingularPlural.GALLON_SOP(currentUnit))
            convertedNumber = gallonToMilliliter(number);
        else if (UnitSingularPlural.PINT_SOP(currentUnit))
            convertedNumber = pintToMilliliter(number);
        else if (UnitSingularPlural.TABLE_SPOON_SOP(currentUnit))
            convertedNumber = tableSpoonToMilliliter(number);
        else if (UnitSingularPlural.CUBIC_METER_SOP(currentUnit))
            convertedNumber = cubicMeterToMilliliter(number);
        else if (UnitSingularPlural.LITER_SOP(currentUnit))
            convertedNumber = literToMilliliter(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit from the listed volume units to
     * milliliter / milliliters every method returns the converted number in double.
     */

    private double cupToMilliliter(double cupNumber){
        return cupNumber * 240;
    }

    private double fluidOunceToMilliliter(double fluidOunceNumber){
        return fluidOunceNumber * 29.574;
    }

    private double gallonToMilliliter(double gallonNumber){
        return gallonNumber * 4546;
    }

    private double pintToMilliliter(double pintNumber){
        return pintNumber * 473; //US liquid pint
    }

    private double tableSpoonToMilliliter(double tableSpoonNumber){
        return tableSpoonNumber * 14.787;
    }

    private double cubicMeterToMilliliter(double cubicMeterNumber){
        return cubicMeterNumber * 1_000_000;
    }

    private double literToMilliliter(double literNumber){
        return literNumber * 1000;
    }

}
