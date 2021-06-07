package superunitconverter.nexmii.com.superunitconverter.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import superunitconverter.nexmii.com.superunitconverter.helpers.ErrorAnalyzer;
import superunitconverter.nexmii.com.superunitconverter.helpers.ErrorLogMessages;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.textmanager.CurrencyTextManager;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.textmanager.LengthTextManager;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.textmanager.TemperatureTextManager;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.textmanager.TextManager;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.textmanager.VolumeTextManager;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.textmanager.WeightTextManager;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitConstants;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitLists;
import superunitconverter.nexmii.com.superunitconverter.util.InternetChecker;

/**
 * This class will get the text input from the user and hold it.
 * It should understand what type of conversion category the text belongs to.
 * After that, it will assign it to the correct category (Temperature, length, etc...)
 */
public final class CategoryManager {

    //Properties:
    private final Context context;
    private final UnitLists mUnitLists;
    private HashMap<String, String> _finalResultSet;
    private boolean keywordsFound = false;
    private boolean currencyConversion = false;
    public String _errorCode;

    //ctor
    public CategoryManager(Context context){
        this.context = context;
        _finalResultSet = new HashMap<>();
        mUnitLists = UnitLists.getInstance();
    }

    /**
     *
     * @param userInput -> The method recieves the String from the user input from the Controller.
     *                  With it, it classifies it according to its category. Depending on the
     *                  category, it fires up the respective method which initializes the necessary
     *                  prcessing for the conversion that belongs to that category
     *
     * Has an interface that passes a HashMap<String, String> which comes from the respective TextManager class.
     * These are: TemperatureTextManager, LengthTextManager, WeightTextManager, VolumeTextManager
     * and CurrencyTextManager
     *
     */
    public void stringInputCategoryManager(String userInput
            , CategoryInterface categoryInterface){

        //It is necessary for the checks to be in this order
        if (!keywordsFound)
            checkCategoryTemp(userInput);
        if (!keywordsFound)
            checkCategoryVolume(userInput);
        if (!keywordsFound)
            checkCategoryLength(userInput);
        if (!keywordsFound)
            checkCategoryWeight(userInput);
        if (!keywordsFound) {
            /*
             * Default. Anything that isn't, ends up here.
             * Good to know for handling bugs.
             */
            checkCategoryCurrency(userInput, categoryInterface);
        }

        if (keywordsFound && !currencyConversion) {
            categoryInterface.onKeywordsCollected(_finalResultSet, _errorCode);
        }

        keywordsFound = false;
    }

    private void checkCategoryTemp(String userInput){
        for (String tempUnit : mUnitLists.getTempUnitList()) {
            if (userInput.contains(tempUnit)){
                TextManager tempTextManager = TemperatureTextManager.getInstance();
                _finalResultSet = tempTextManager.manageText(userInput);
                _finalResultSet.put(UnitConstants.CATEGORY, UnitConstants.TEMPERATURE);
                keywordsFound = true;
                break;
            }
        }
    }

    private void checkCategoryLength(String userInput){
        for (String lengthUnit : mUnitLists.getLengthUnitList()){
            if (userInput.contains(lengthUnit)){
                TextManager lengthTextManager = LengthTextManager.getInstance();
                _finalResultSet = lengthTextManager.manageText(userInput);
                _finalResultSet.put(UnitConstants.CATEGORY, UnitConstants.LENGTH);
                keywordsFound = true;
                break;
            }
        }
    }

    private void checkCategoryVolume(String userInput){
        for (String volumeUnit : mUnitLists.getVolumeUnitList()){
            if (userInput.contains(volumeUnit)){
                TextManager volumeTextManager = VolumeTextManager.getInstance();
                _finalResultSet = volumeTextManager.manageText(userInput);
                _finalResultSet.put(UnitConstants.CATEGORY, UnitConstants.VOLUME);
                keywordsFound = true;
                break;
            }
        }
    }

    private void checkCategoryWeight(String userInput){
        if (userInput.contains(UnitConstants.POUND)){
            List<String> inputWords = new ArrayList<>(Arrays.asList(userInput.split(" ")));
            for (String word : inputWords){
                if (word.equalsIgnoreCase(UnitConstants.POUND)) {
                    inputWords.remove(UnitConstants.POUND);
                    break;
                }else if (word.equalsIgnoreCase(UnitConstants.POUNDS)) {
                    inputWords.remove(UnitConstants.POUNDS);
                    break;
                }
            }
            for (String weightUnit : mUnitLists.getWeightUnitList()){
                for (String word : inputWords){
                    if (weightUnit.equalsIgnoreCase(word)){
                        TextManager weightTextManager = WeightTextManager.getInstance();
                        _finalResultSet = weightTextManager.manageText(userInput);
                        _finalResultSet.put(UnitConstants.CATEGORY, UnitConstants.WEIGHT);
                        keywordsFound = true;
                        break;
                    }
                }
                if (keywordsFound)break;
            }
        }else {
            for (String weightUnit : mUnitLists.getWeightUnitList()) {
                if (userInput.contains(weightUnit)) {
                    TextManager weightTextManager = WeightTextManager.getInstance();
                    _finalResultSet = weightTextManager.manageText(userInput);
                    _finalResultSet.put(UnitConstants.CATEGORY, UnitConstants.WEIGHT);
                    keywordsFound = true;
                    break;
                }
            }
        }
    }

    private void checkCategoryCurrency(String userInput, CategoryInterface ci){
        if (!keywordsFound){
            mUnitLists.getCurrenciesList((currenciesList -> {
                InternetChecker.getInstance().isThereInternet(context, new InternetChecker
                        .InternetCheckerInterface() {
                    @Override
                    public void onInternetStatusOK() {
                        CurrencyTextManager currencyTextManager = CurrencyTextManager.getInstance();
                        currencyTextManager.manageText(userInput, (finalResultSet, errorCode) -> {
                            if (errorCode != null) {
                                _errorCode = errorCode;
                                ci.onKeywordsCollected(null, _errorCode);
                            } else {
                                _finalResultSet = finalResultSet;
                                _finalResultSet.put(UnitConstants.CATEGORY, UnitConstants.CURRENCY);
                                keywordsFound = true;
                                currencyConversion = true;
                                ci.onKeywordsCollected(finalResultSet, null);
                            }
                        });
                    }

                    @Override
                    public void onInternetStatusNOTOK() {

                    }
                });
            }), () -> {
                //Currencies internet check
                //No current internet connection detected
                ci.onKeywordsCollected(null, ErrorAnalyzer.getInstance()
                        .analyzeError(context, ErrorLogMessages.API_CALL_FAILURE));
            });
        }
    }

    public interface CategoryInterface{
        void onKeywordsCollected(HashMap<String, String> finalResultSet, String errorCode);
    }
}
