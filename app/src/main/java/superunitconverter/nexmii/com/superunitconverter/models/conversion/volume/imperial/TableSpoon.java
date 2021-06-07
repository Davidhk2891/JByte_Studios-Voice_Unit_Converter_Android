package superunitconverter.nexmii.com.superunitconverter.models.conversion.volume.imperial;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * TableSpoon class. Has the logic needed to convert from the other volume units to Tablespoon
 * and implements the common convertTo method from the conversion interface
 */
public class TableSpoon implements ConversionStrategy {

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
            convertedNumber = cupToTableSpoon(number);
        else if (UnitSingularPlural.FLUID_OUNCE_SOP(currentUnit))
            convertedNumber = fluidOunceToTableSpoon(number);
        else if (UnitSingularPlural.GALLON_SOP(currentUnit))
            convertedNumber = gallonToTableSpoon(number);
        else if (UnitSingularPlural.PINT_SOP(currentUnit))
            convertedNumber = pintToTableSpoon(number);
        else if (UnitSingularPlural.CUBIC_METER_SOP(currentUnit))
            convertedNumber = cubicMeterToTableSpoon(number);
        else if (UnitSingularPlural.LITER_SOP(currentUnit))
            convertedNumber = literToTableSpoon(number);
        else if (UnitSingularPlural.MILLILITER_SOP(currentUnit))
            convertedNumber = milliliterToTableSpoon(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit from the listed volume units to
     * TableSpoon / TableSpoons every method returns the converted number in double.
     */

    private double cupToTableSpoon(double cupNumber){
        return cupNumber * 16.231;
    }

    private double fluidOunceToTableSpoon(double fluidOunceNumber){
        return fluidOunceNumber * 2;
    }

    private double gallonToTableSpoon(double gallonNumber){
        return gallonNumber * 307;
    }

    private double pintToTableSpoon(double pintNumber){
        return pintNumber * 32; //US liquid pint
    }

    private double cubicMeterToTableSpoon(double cubicMeterNumber){
        return cubicMeterNumber * 67628;
    }

    private double literToTableSpoon(double literNumber){
        return literNumber * 67.628;
    }

    private double milliliterToTableSpoon(double milliliterNumber){
        return milliliterNumber / 14.787;
    }

}
