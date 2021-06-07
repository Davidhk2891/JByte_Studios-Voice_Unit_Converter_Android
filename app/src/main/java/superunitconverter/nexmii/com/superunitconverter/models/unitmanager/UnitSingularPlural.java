package superunitconverter.nexmii.com.superunitconverter.models.unitmanager;

public final class UnitSingularPlural {

    //temperature
    public static boolean FAHRENHEIT_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.FAHRENHEIT);
    }
    public static boolean CELSIUS_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.CELSIUS);
    }
    public static boolean KELVIN_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.KELVIN) || currentUnit.equalsIgnoreCase(UnitConstants.KELVINS);
    }
    //----------//

    //length
    public static boolean MILLIMETER_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.MILLIMETER) || currentUnit.equalsIgnoreCase(UnitConstants.MILLIMETERS);
    }
    public static boolean CENTIMETER_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.CENTIMETER) || currentUnit.equalsIgnoreCase(UnitConstants.CENTIMETERS);
    }
    public static boolean METER_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.METER) || currentUnit.equalsIgnoreCase(UnitConstants.METERS);
    }
    public static boolean KILOMETER_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.KILOMETER) || currentUnit.equalsIgnoreCase(UnitConstants.KILOMETERS);
    }
    public static boolean INCH_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.INCH) || currentUnit.equalsIgnoreCase(UnitConstants.INCHES);
    }
    public static boolean FOOT_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.FOOT) || currentUnit.equalsIgnoreCase(UnitConstants.FEET);
    }
    public static boolean YARD_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.YARD) || currentUnit.equalsIgnoreCase(UnitConstants.YARDS);
    }
    public static boolean MILE_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.MILE) || currentUnit.equalsIgnoreCase(UnitConstants.MILES);
    }
    //-----//

    //weight
    public static boolean OUNCE_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.OUNCE) || currentUnit.equalsIgnoreCase(UnitConstants.OUNCES);
    }

    public static boolean POUND_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.POUND) || currentUnit.equalsIgnoreCase(UnitConstants.POUNDS);
    }

    public static boolean GRAM_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.GRAM) || currentUnit.equalsIgnoreCase(UnitConstants.GRAMS);
    }

    public static boolean KILOGRAM_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.KILOGRAM) || currentUnit.equalsIgnoreCase(UnitConstants.KILOGRAMS)
                || currentUnit.equalsIgnoreCase(UnitConstants.KILO) || currentUnit.equalsIgnoreCase(UnitConstants.KILOS);
    }

    public static boolean MILLIGRAM_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.MILLIGRAM) || currentUnit.equalsIgnoreCase(UnitConstants.MILLIGRAMS);
    }

    public static boolean TONNE_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.TON) || currentUnit.equalsIgnoreCase(UnitConstants.TONS);
    }
    //-----//

    //volume
    public static boolean CUP_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.CUP) || currentUnit.equalsIgnoreCase(UnitConstants.CUPS);
    }

    public static boolean FLUID_OUNCE_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.FLUID_OUNCE) || currentUnit.equalsIgnoreCase(UnitConstants.FLUID_OUNCES);
    }

    public static boolean GALLON_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.GALLON) || currentUnit.equalsIgnoreCase(UnitConstants.GALLONS);
    }

    public static boolean PINT_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.PINT) || currentUnit.equalsIgnoreCase(UnitConstants.PINTS);
    }

    public static boolean TABLE_SPOON_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.TABLE_SPOON) || currentUnit.equalsIgnoreCase(UnitConstants.TABLE_SPOONS)
                || currentUnit.equalsIgnoreCase(UnitConstants.TABLE_SPOON_2) || currentUnit.equalsIgnoreCase(UnitConstants.TABLE_SPOONS_2);
    }

    public static boolean CUBIC_METER_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.CUBIC_METER) || currentUnit.equalsIgnoreCase(UnitConstants.CUBIC_METERS);
    }

    public static boolean LITER_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.LITER) || currentUnit.equalsIgnoreCase(UnitConstants.LITERS);
    }

    public static boolean MILLILITER_SOP(String currentUnit){
        return currentUnit.equalsIgnoreCase(UnitConstants.MILLILITER) || currentUnit.equalsIgnoreCase(UnitConstants.MILLILITERS);
    }
    //-----//

}
