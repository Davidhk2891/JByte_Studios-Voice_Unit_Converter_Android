package superunitconverter.nexmii.com.superunitconverter.models.conversion.volume.metric;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * CubicMeter class. Has the logic needed to convert from the other volume units to Cubic meter
 * and implements the common convertTo method from the conversion interface
 */
public class CubicMeter implements ConversionStrategy {

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
            convertedNumber = cupToCubicMeter(number);
        else if (UnitSingularPlural.FLUID_OUNCE_SOP(currentUnit))
            convertedNumber = fluidOunceToCubicMeter(number);
        else if (UnitSingularPlural.GALLON_SOP(currentUnit))
            convertedNumber = gallonToCubicMeter(number);
        else if (UnitSingularPlural.PINT_SOP(currentUnit))
            convertedNumber = pintToCubicMeter(number);
        else if (UnitSingularPlural.TABLE_SPOON_SOP(currentUnit))
            convertedNumber = tableSpoonToCubicMeter(number);
        else if (UnitSingularPlural.LITER_SOP(currentUnit))
            convertedNumber = literToCubicMeter(number);
        else if (UnitSingularPlural.MILLILITER_SOP(currentUnit))
            convertedNumber = milliliterToCubicMeter(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit from the listed volume units to
     * Cubic meter / Cubic meters every method returns the converted number in double.
     */

    private double cupToCubicMeter(double cupNumber){
        return cupNumber / 4167;
    }

    private double fluidOunceToCubicMeter(double fluidOunceNumber){
        return fluidOunceNumber / 33814;
    }

    private double gallonToCubicMeter(double gallonNumber){
        return gallonNumber / 220;
    }

    private double pintToCubicMeter(double pintNumber){
        return pintNumber / 2113; //US liquid pint
    }

    private double tableSpoonToCubicMeter(double tableSpoonNumber){
        return  tableSpoonNumber / 67628;
    }

    private double literToCubicMeter(double literNumber){
        return literNumber / 1000;
    }

    private double milliliterToCubicMeter(double milliliterNumber){
        return milliliterNumber / 1_000_000;
    }

}
