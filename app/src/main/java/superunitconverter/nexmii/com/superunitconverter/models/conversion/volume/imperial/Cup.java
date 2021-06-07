package superunitconverter.nexmii.com.superunitconverter.models.conversion.volume.imperial;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * Cup class. Has the logic needed to convert from the other volume units to Cup and implements
 * the common convertTo method from the conversion interface
 */
public class Cup implements ConversionStrategy {

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
        if (UnitSingularPlural.FLUID_OUNCE_SOP(currentUnit))
            convertedNumber = fluidOunceToCup(number);
        else if (UnitSingularPlural.GALLON_SOP(currentUnit))
            convertedNumber = gallonToCup(number);
        else if (UnitSingularPlural.PINT_SOP(currentUnit))
            convertedNumber = pintToCup(number);
        else if (UnitSingularPlural.TABLE_SPOON_SOP(currentUnit))
            convertedNumber = tableSpoonToCup(number);
        else if (UnitSingularPlural.CUBIC_METER_SOP(currentUnit))
            convertedNumber = cubicMeterToCup(number);
        else if (UnitSingularPlural.LITER_SOP(currentUnit))
            convertedNumber = literToCup(number);
        else if (UnitSingularPlural.MILLILITER_SOP(currentUnit))
            convertedNumber = milliliterToCup(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit from the listed volume units to
     * Cup/Cups every method returns the converted number in double.
     */

    private double fluidOunceToCup(double fluidOunceNumber){
        return fluidOunceNumber / 8.115;
    }

    private double gallonToCup(double gallonNumber){
        return gallonNumber * 18.942;
    }

    private double pintToCup(double pintNumber){
        return pintNumber * 1.972; //US liquid pint
    }

    private double tableSpoonToCup(double tableSpoonNumber){
        return tableSpoonNumber / 16.231;
    }

    private double cubicMeterToCup(double cubicMeterNumber){
        return cubicMeterNumber * 4167;
    }

    private double literToCup(double literNumber){
        return literNumber * 4.167;
    }

    private double milliliterToCup(double milliliterNumber){
        return milliliterNumber / 240;
    }
}
