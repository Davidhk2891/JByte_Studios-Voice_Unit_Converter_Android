package superunitconverter.nexmii.com.superunitconverter.models.conversion.volume.metric;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Liter class. Has the logic needed to convert from the other volume units to liter
 * and implements the common convertTo method from the conversion interface
 */
public class Liter implements ConversionStrategy {

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
            convertedNumber = cupToLiter(number);
        else if (UnitSingularPlural.FLUID_OUNCE_SOP(currentUnit))
            convertedNumber = fluidOunceToLiter(number);
        else if (UnitSingularPlural.GALLON_SOP(currentUnit))
            convertedNumber = gallonToLiter(number);
        else if (UnitSingularPlural.PINT_SOP(currentUnit))
            convertedNumber = pintToLiter(number);
        else if (UnitSingularPlural.TABLE_SPOON_SOP(currentUnit))
            convertedNumber = tableSpoonToLiter(number);
        else if (UnitSingularPlural.CUBIC_METER_SOP(currentUnit))
            convertedNumber = cubicMeterToLiter(number);
        else if (UnitSingularPlural.MILLILITER_SOP(currentUnit))
            convertedNumber = milliliterToLiter(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit from the listed volume units to
     * liter / liters every method returns the converted number in double.
     */

    private double cupToLiter(double cupNumber){
        return cupNumber / 4.167;
    }

    private double fluidOunceToLiter(double fluidOunceNumber){
        return fluidOunceNumber / 33.814;
    }

    private double gallonToLiter(double gallonNumber){
        return gallonNumber * 4.546;
    }

    private double pintToLiter(double pintNumber){
        return pintNumber / 2.113; //US liquid pint
    }

    private double tableSpoonToLiter(double tableSpoonNumber){
        return tableSpoonNumber / 67.628;
    }

    private double cubicMeterToLiter(double cubicMeterNumber){
        return cubicMeterNumber * 1000;
    }

    private double milliliterToLiter(double milliliterNumber){
        return milliliterNumber / 1000;
    }

}
