package superunitconverter.nexmii.com.superunitconverter.models.conversion.volume.imperial;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.ConversionStrategy;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitSingularPlural;

/**
 * FluidOunce class. Has the logic needed to convert from the other volume units to Fluid Ounce
 * and implements the common convertTo method from the conversion interface
 */
public class FluidOunce implements ConversionStrategy {

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
            convertedNumber = cupToFluidOunce(number);
        else if (UnitSingularPlural.GALLON_SOP(currentUnit))
            convertedNumber = gallonToFluidOunce(number);
        else if (UnitSingularPlural.PINT_SOP(currentUnit))
            convertedNumber = pintToFluidOunce(number);
        else if (UnitSingularPlural.TABLE_SPOON_SOP(currentUnit))
            convertedNumber = tableSpoonToFluidOunce(number);
        else if (UnitSingularPlural.CUBIC_METER_SOP(currentUnit))
            convertedNumber = cubicMeterToFluidOunce(number);
        else if (UnitSingularPlural.LITER_SOP(currentUnit))
            convertedNumber = literToFluidOunce(number);
        else if (UnitSingularPlural.MILLILITER_SOP(currentUnit))
            convertedNumber = milliliterToFluidOunce(number);
        return convertedNumber;
    }

    /**
     * All methods for performing the conversion from any unit from the listed volume units to
     * Cup/Cups every method returns the converted number in double.
     */

    private double cupToFluidOunce(double cupNumber){
        return cupNumber * 8.115;
    }

    private double gallonToFluidOunce(double gallonNumber){
        return gallonNumber * 154;
    }

    private double pintToFluidOunce(double pintNumber){
        return pintNumber * 16; //US liquid pint
    }

    private double tableSpoonToFluidOunce(double tableSpoonNumber){
        return tableSpoonNumber / 2;
    }

    private double cubicMeterToFluidOunce(double cubicMeterNumber){
        return cubicMeterNumber * 33814;
    }

    private double literToFluidOunce(double literNumber){
        return literNumber * 33.814;
    }

    private double milliliterToFluidOunce(double milliliterNumber){
        return milliliterNumber / 29.574;
    }

}
