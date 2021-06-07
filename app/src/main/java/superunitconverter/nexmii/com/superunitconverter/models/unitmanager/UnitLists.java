package superunitconverter.nexmii.com.superunitconverter.models.unitmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.currency.CurrencyRepo;

/**
 * This singleton class holds lists of all the units from all the categories which can be used to
 * deduct what type of conversion the app needs to do
 */
public final class UnitLists {

    //Dynamic unit lists
    private final List<String> conversionsList = new ArrayList<>();

    private final List<String> tempUnitList = new ArrayList<>();
    private final List<String> lengthUnitList = new ArrayList<>();
    private final List<String> weightUnitList = new ArrayList<>();
    private final List<String> volumeUnitList = new ArrayList<>();
    private Map<String, String> currencyUnitList = new HashMap<>();
    private final HashMap<String,String> unitSymbols = new HashMap<>();

    //Exact unit lists
    private final List<String> exactSTempUnitList = new ArrayList<>();
    private final List<String> exactSLengthUnitList = new ArrayList<>();
    private final List<String> exactSWeightUnitList = new ArrayList<>();
    private final List<String> exactSVolumeUnitList = new ArrayList<>();


    //Create an object of UnitLists
    private static final UnitLists instance = new UnitLists();

    //Make the ctor private so it cannot be accessed
    private UnitLists(){}

    //Get the only object available
    public static UnitLists getInstance(){
        return instance;
    }

    //DYNAMIC UNIT LISTS

    public synchronized  List<String> getConversionsList(){
        conversionsList.add(UnitConstants.TEMPERATURE);
        conversionsList.add(UnitConstants.LENGTH);
        conversionsList.add(UnitConstants.WEIGHT);
        conversionsList.add(UnitConstants.VOLUME);
        conversionsList.add(UnitConstants.CURRENCY);
        return conversionsList;
    }

    public synchronized List<String> getTempUnitList(){
        //Singular
        tempUnitList.add(UnitConstants.CELSIUS);
        tempUnitList.add(UnitConstants.FAHRENHEIT);
        tempUnitList.add(UnitConstants.KELVIN);
        //Plural
        tempUnitList.add(UnitConstants.KELVINS);
        return tempUnitList;
    }

    public synchronized List<String> getLengthUnitList(){
        //Singular
        lengthUnitList.add(UnitConstants.MILLIMETER);
        lengthUnitList.add(UnitConstants.CENTIMETER);
        lengthUnitList.add(UnitConstants.METER);
        lengthUnitList.add(UnitConstants.KILOMETER);
        lengthUnitList.add(UnitConstants.INCH);
        lengthUnitList.add(UnitConstants.FOOT);
        lengthUnitList.add(UnitConstants.YARD);
        lengthUnitList.add(UnitConstants.MILE);
        //Plural
        lengthUnitList.add(UnitConstants.MILLIMETERS);
        lengthUnitList.add(UnitConstants.CENTIMETERS);
        lengthUnitList.add(UnitConstants.METERS);
        lengthUnitList.add(UnitConstants.KILOMETERS);
        lengthUnitList.add(UnitConstants.INCHES);
        lengthUnitList.add(UnitConstants.FEET);
        lengthUnitList.add(UnitConstants.YARDS);
        lengthUnitList.add(UnitConstants.MILES);
        return lengthUnitList;
    }

    public synchronized List<String> getWeightUnitList(){
        //Singular
        weightUnitList.add(UnitConstants.MILLIGRAM);
        weightUnitList.add(UnitConstants.GRAM);
        weightUnitList.add(UnitConstants.KILOGRAM);
        weightUnitList.add(UnitConstants.TON);
        weightUnitList.add(UnitConstants.OUNCE);
        weightUnitList.add(UnitConstants.POUND);
        //Plural
        weightUnitList.add(UnitConstants.MILLIGRAMS);
        weightUnitList.add(UnitConstants.GRAMS);
        weightUnitList.add(UnitConstants.KILOGRAMS);
        weightUnitList.add(UnitConstants.TONS);
        weightUnitList.add(UnitConstants.OUNCES);
        weightUnitList.add(UnitConstants.POUNDS);
        //other
        weightUnitList.add(UnitConstants.KILO);
        weightUnitList.add(UnitConstants.KILOS);
        return weightUnitList;
    }

    public synchronized List<String> getVolumeUnitList(){
        //Singular
        volumeUnitList.add(UnitConstants.CUP);
        volumeUnitList.add(UnitConstants.MILLILITER);
        volumeUnitList.add(UnitConstants.LITER);
        volumeUnitList.add(UnitConstants.FLUID_OUNCE);
        volumeUnitList.add(UnitConstants.PINT);
        volumeUnitList.add(UnitConstants.GALLON);
        volumeUnitList.add(UnitConstants.TABLE_SPOON);
        volumeUnitList.add(UnitConstants.TABLE_SPOON_2);
        volumeUnitList.add(UnitConstants.CUBIC_METER);
        //Plural
        volumeUnitList.add(UnitConstants.CUPS);
        volumeUnitList.add(UnitConstants.MILLILITERS);
        volumeUnitList.add(UnitConstants.LITERS);
        volumeUnitList.add(UnitConstants.FLUID_OUNCES);
        volumeUnitList.add(UnitConstants.PINTS);
        volumeUnitList.add(UnitConstants.GALLONS);
        volumeUnitList.add(UnitConstants.TABLE_SPOONS);
        volumeUnitList.add(UnitConstants.TABLE_SPOONS_2);
        volumeUnitList.add(UnitConstants.CUBIC_METERS);
        return volumeUnitList;
    }

    public synchronized void getCurrenciesList(CurrenciesInterface currenciesInterface,
                                               CurrenciesInternetInterface currenciesInternetInterface){
        CurrencyRepo currencyRepo = CurrencyRepo.getInstance();
        currencyRepo.getCurrencies(new CurrencyRepo.CurrenciesCallback() {
            @Override
            public void onResponse(Map<String, String> currencies, Exception e) {
                if (currencies != null){
                    currencyUnitList = currencies;
                    currenciesInterface.onCurrenciesRetrieved(currencyUnitList);
                }
            }

            @Override
            public void onFailure(String message) {
                currenciesInternetInterface.onCurrenciesNotRetrieved();
            }
        });
    }

    public interface CurrenciesInterface{
        void onCurrenciesRetrieved(Map<String, String> currenciesList);
    }

    public interface CurrenciesInternetInterface{
        void onCurrenciesNotRetrieved();
    }

    public HashMap<String, String> getUnitSymbolsList(){
        unitSymbols.put(UnitConstants.MILLION_SYMBOL, UnitConstants.MILLION_LITERAL);
        unitSymbols.put(UnitConstants.BILLION_SYMBOL, UnitConstants.BILLION_LITERAL);
        unitSymbols.put(UnitConstants.TRILLION_SYMBOL, UnitConstants.TRILLION_LITERAL);
        unitSymbols.put(UnitConstants.QUADRILLION_SYMBOL, UnitConstants.QUADRILLION_LITERAL);

        unitSymbols.put(UnitConstants.CELSIUS_SYMBOL, UnitConstants.CELSIUS_LITERAL);
        unitSymbols.put(UnitConstants.FAHRENHEIT_SYMBOL, UnitConstants.FAHRENHEIT_LITERAL);
        unitSymbols.put(UnitConstants.KELVIN_SYMBOL, UnitConstants.KELVIN_LITERAL);

        unitSymbols.put(UnitConstants.FOOT_SYMBOL, UnitConstants.FOOT_LITERAL);
        unitSymbols.put(UnitConstants.INCH_SYMBOL, UnitConstants.INCH_LITERAL);
        unitSymbols.put(UnitConstants.MILE_SYMBOL, UnitConstants.MILE_LITERAL);
        unitSymbols.put(UnitConstants.CENTIMETER_SYMBOL, UnitConstants.CENTIMETER_LITERAL);
        unitSymbols.put(UnitConstants.KILOMETER_SYMBOL, UnitConstants.KILOMETER_LITERAL);
        unitSymbols.put(UnitConstants.METER_SYMBOL, UnitConstants.METER_LITERAL);
        unitSymbols.put(UnitConstants.YARD_SYMBOL, UnitConstants.YARD_LITERAL);
        unitSymbols.put(UnitConstants.MILLIMETER_SYMBOL, UnitConstants.MILLIMETER_LITERAL);

        unitSymbols.put(UnitConstants.FLUID_OUNCE_SYMBOL, UnitConstants.FLUID_OUNCE_LITERAL);
        unitSymbols.put(UnitConstants.TABLE_SPOON_SYMBOL, UnitConstants.TABLE_SPOON_LITERAL);
        unitSymbols.put(UnitConstants.LITER_SYMBOL, UnitConstants.LITER_LITERAL);
        unitSymbols.put(UnitConstants.MILLILITER_SYMBOL, UnitConstants.MILLILITER_LITERAL);

        unitSymbols.put(UnitConstants.OUNCE_SYMBOL, UnitConstants.OUNCE_LITERAL);
        unitSymbols.put(UnitConstants.POUND_SYMBOL, UnitConstants.POUND_LITERAL);
        unitSymbols.put(UnitConstants.GRAM_SYMBOL, UnitConstants.GRAM_LITERAL);
        unitSymbols.put(UnitConstants.KILOGRAM_SYMBOL, UnitConstants.KILOGRAM_LITERAL);
        unitSymbols.put(UnitConstants.MILLIGRAM_SYMBOL, UnitConstants.MILLIGRAM_LITERAL);
        unitSymbols.put(UnitConstants.TON_SYMBOL, UnitConstants.TON_LITERAL);

        unitSymbols.put(UnitConstants.DOLLAR_SYMBOL, UnitConstants.DOLLAR_LITERAL);
        unitSymbols.put(UnitConstants.SHEQEL_SYMBOL, UnitConstants.SHEQEL_LITERAL);
        unitSymbols.put(UnitConstants.SHEQELS_SYMBOL, UnitConstants.SHEQELS_LITERAL);

        return unitSymbols;
    }

    //EXACT UNIT LISTS

    //Temperature
    public synchronized List<String> getExactSTempUnitList(){
        exactSTempUnitList.add(UnitConstants.CELSIUS);
        exactSTempUnitList.add(UnitConstants.FAHRENHEIT);
        exactSTempUnitList.add(UnitConstants.KELVIN);
        return exactSTempUnitList;
    }

    //Length
    public synchronized List<String> getExactSLengthUnitList(){
        exactSLengthUnitList.add(UnitConstants.MILLIMETER);
        exactSLengthUnitList.add(UnitConstants.CENTIMETER);
        exactSLengthUnitList.add(UnitConstants.METER);
        exactSLengthUnitList.add(UnitConstants.KILOMETER);
        exactSLengthUnitList.add(UnitConstants.INCH);
        exactSLengthUnitList.add(UnitConstants.FOOT);
        exactSLengthUnitList.add(UnitConstants.YARD);
        exactSLengthUnitList.add(UnitConstants.MILE);
        return exactSLengthUnitList;
    }

    //Weight
    public synchronized List<String> getExactSWeightUnitList(){
        exactSWeightUnitList.add(UnitConstants.MILLIGRAM);
        exactSWeightUnitList.add(UnitConstants.GRAM);
        exactSWeightUnitList.add(UnitConstants.KILOGRAM);
        exactSWeightUnitList.add(UnitConstants.TON);
        exactSWeightUnitList.add(UnitConstants.OUNCE);
        exactSWeightUnitList.add(UnitConstants.POUND);
        return exactSWeightUnitList;
    }

    //Volume
    public synchronized List<String> getExactSVolumeUnitList(){
        exactSVolumeUnitList.add(UnitConstants.CUP);
        exactSVolumeUnitList.add(UnitConstants.MILLILITER);
        exactSVolumeUnitList.add(UnitConstants.LITER);
        exactSVolumeUnitList.add(UnitConstants.FLUID_OUNCE_FULL);
        exactSVolumeUnitList.add(UnitConstants.PINT);
        exactSVolumeUnitList.add(UnitConstants.GALLON);
        exactSVolumeUnitList.add(UnitConstants.TABLE_SPOON);
        exactSVolumeUnitList.add(UnitConstants.CUBIC_METER_FULL);
        return exactSVolumeUnitList;
    }
}
