package superunitconverter.nexmii.com.superunitconverter.models.conversion.inputalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitConstants;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitLists;
import superunitconverter.nexmii.com.superunitconverter.util.Logger;

/**
 * KeywordsExtractor class for Currencies for Measurements is a Singleton class.
 * Its main job is to collect the user input from the Currencies TextManager class.
 * and return a Hashmap with the three main components of the conversion. The number to be converted,
 * The currency to convert from and the currency to convert to
 */
public final class KeywordsExtractorC {

    private final Logger mLogger;

    private static KeywordsExtractorC instance;

    private KeywordsExtractorC(){
        mLogger = Logger.getLoggerInstance();
    }

    public static KeywordsExtractorC getInstance(){
        if (instance == null)
            instance = new KeywordsExtractorC();
        return instance;
    }

    private List<String> lcValuesList;

    private final HashMap<String, String> keywords = new HashMap<>();

    /**
     *
     * @param userInput -> The String collected from the user
     * @return a Hashmap of String sets which contains all the necessary data to send to
     * fixer.io API and return the conversion back to the app
     */
    public HashMap<String, String> extractCurrencyKeywords(String userInput, KeyWordExtractorCInterface keci){

        UnitLists unitLists = UnitLists.getInstance();

        List<String> wordsList = new ArrayList<>(Arrays.asList(userInput.split(" ")));

        List<String> dollarCurrencies = new ArrayList<>();
        List<String> poundCurrencies = new ArrayList<>();

        unitLists.getCurrenciesList((Map<String, String> cMap)->{

            String dollarFull = "US dollar";
            String dollar = "dollar";
            String USD = "USD";
            String poundFull = "British pounds sterling";
            String pound = "pound";
            String GBP = "GBP";

            String unitWholeValue = "";
            String unitWholeValueFrom = "";
            String unitWholeValueTo = "";

            String unitToConvert = "";
            String convertFromUnit = "";
            String convertToUnit = "";

            boolean localCheck = false;

            Map<String, String> localCMap;
            localCMap = cMap;

            //iterate through the user input
            //ORIGINAL UNIT -> convertFrom
            mLogger.infoLog("------------------------------FROM_UNIT--------------------------------", "RAN");
            for (int i = 0; i < wordsList.size(); i++) {
                mLogger.infoLog("word_AFTER_1", "RAN - " + wordsList.get(i));
                for (Map.Entry<String, String> entry : localCMap.entrySet()) {
                    String lcValue = entry.getValue().toLowerCase();
                    lcValuesList = new ArrayList<>(Arrays.asList(lcValue.split(" ")));

                    //Extract currencies that contain the word dollar
                    if (lcValue.contains(dollar))
                        dollarCurrencies.add(lcValue);
                    if (wordsList.get(i).contains(dollar)) {
                        mLogger.infoLog("word_AFTER_1_US_DOLLAR", "RAN");
                        //we are talking about dollars
                        for (int j = 0; j < dollarCurrencies.size(); j++) {
                            if (!wordsList.get(i).contains(dollarCurrencies.get(j))) {
                                //it only contains the word dollar/dollars. The user is referring
                                //to US dollars
                                unitWholeValue = wordsList.get(i);
                                unitWholeValueFrom = dollarFull;
                                convertFromUnit = USD;
                                if (!localCheck) {
                                    mLogger.infoLog("word_AFTER_1_FROM_INPUT", "united states " + wordsList.get(i));
                                    mLogger.infoLog("word_AFTER_1_FROM_LIST", "united states " + unitWholeValue);
                                    mLogger.infoLog("word_AFTER_1_FROM_CODE", convertFromUnit);
                                    localCheck = true;
                                }
                            }
                        }
                        break;
                    }

                    //Extract currencies that contain the word pound
                    if (lcValue.contains(pound))
                        poundCurrencies.add(lcValue);
                    if (wordsList.get(i).contains(pound)){
                        mLogger.infoLog("word_AFTER_1_UK_POUND", "RAN");
                        //we are talking about pounds
                        for (int k = 0; k < poundCurrencies.size(); k++){
                            if (!wordsList.get(i).contains(poundCurrencies.get(k))){
                                //it only contains the word pound/pounds. The user is referring
                                //to UK pounds
                                unitWholeValue = wordsList.get(i);
                                convertFromUnit = GBP;
                                unitWholeValueFrom = poundFull;
                                if (!localCheck) {
                                    mLogger.infoLog("word_AFTER_1_FROM_INPUT", "British " + wordsList.get(i));
                                    mLogger.infoLog("word_AFTER_1_FROM_LIST", "British " + unitWholeValue);
                                    mLogger.infoLog("word_AFTER_1_FROM_CODE", convertFromUnit);
                                    localCheck = true;
                                }
                            }
                        }
                        break;
                    }

                    //Extract any other currency
                    for (String lcWord : lcValuesList){
                        if (lcWord.equalsIgnoreCase(wordsList.get(i))
                                || lcWord.equalsIgnoreCase(wordsList.get(i).substring(0,wordsList.get(i).length() - 1))){

                            mLogger.infoLog("word_AFTER_1_OTHER_CURR", "RAN");
                            unitWholeValue = lcValue;
                            convertFromUnit = entry.getKey();
                            unitWholeValueFrom = entry.getValue();
                            mLogger.infoLog("word_AFTER_1_FROM_INPUT", wordsList.get(i));
                            mLogger.infoLog("word_AFTER_1_FROM_LIST", unitWholeValue);
                            mLogger.infoLog("word_AFTER_1_FROM_CODE", convertFromUnit);

                            break;

                        }
                    }
                }
                if (!convertFromUnit.isEmpty()) break;
            }
            //Delete all the relevant words from convertFrom unit
            List<String> wordsToDelete = new ArrayList<>();
            List<String> wordsToDeleteFrom = new ArrayList<>(Arrays.asList(unitWholeValue.split(" ")));
            for (String word : wordsToDeleteFrom){
                mLogger.infoLog("word_AFTER_1_TO_DELETE_1", "RAN: " + word);
                for (String inputWord : wordsList) {
                    if (word.equalsIgnoreCase(inputWord)
                            || word.equalsIgnoreCase(inputWord.substring(0, inputWord.length() - 1))) {

                        mLogger.infoLog("word_AFTER_1_TO_DELETE_1_1", "RAN: " + inputWord);
                        wordsToDelete.add(inputWord);

                    }
                }
            }
            mLogger.infoLog("word_AFTER_TO_DELETE", wordsToDelete.toString());
            for (String word : wordsToDelete){
                wordsList.remove(word);
            }
            mLogger.infoLog("word_AFTER_WHAT_REMAINS", wordsList.toString());
            localCheck = false;
            mLogger.infoLog("-----------------------------------------------------------------------", "RAN");

            //iterate through the user input
            //ORIGINAL UNIT -> convertFrom
            mLogger.infoLog("-------------------------------TO_UNIT---------------------------------", "RAN");
            for (int i = 0; i < wordsList.size(); i++) {
                mLogger.infoLog("word_AFTER_2", "RAN - " + wordsList.get(i));
                for (Map.Entry<String, String> entry : localCMap.entrySet()) {
                    String lcValue = entry.getValue().toLowerCase();
                    lcValuesList = new ArrayList<>(Arrays.asList(lcValue.split(" ")));

                    //Extract currencies that contain the word dollar
                    if (lcValue.contains(dollar))
                        dollarCurrencies.add(lcValue);
                    if (wordsList.get(i).contains(dollar)) {
                        mLogger.infoLog("word_AFTER_2_US_DOLLAR", "RAN");
                        //we are talking about dollars
                        for (int j = 0; j < dollarCurrencies.size(); j++) {
                            if (!wordsList.get(i).contains(dollarCurrencies.get(j))) {
                                //it only contains the word dollar/dollars. The user is referring
                                //to US dollars
                                unitWholeValue = wordsList.get(i);
                                convertToUnit = USD;
                                unitWholeValueTo = dollarFull;
                                if (!localCheck) {
                                    mLogger.infoLog("word_AFTER_2_FROM_INPUT", "united states " + wordsList.get(i));
                                    mLogger.infoLog("word_AFTER_2_FROM_LIST", "united states " + unitWholeValue);
                                    mLogger.infoLog("word_AFTER_1_FROM_CODE", convertToUnit);
                                    localCheck = true;
                                }
                            }
                        }
                        break;
                    }

                    //Extract currencies that contain the word pound
                    if (lcValue.contains(pound))
                        poundCurrencies.add(lcValue);
                    if (wordsList.get(i).contains(pound)){
                        mLogger.infoLog("word_AFTER_2_UK_POUND", "RAN");
                        //we are talking about pounds
                        for (int k = 0; k < poundCurrencies.size(); k++){
                            if (!wordsList.get(i).contains(poundCurrencies.get(k))){
                                //it only contains the word pound/pounds. The user is referring
                                //to UK pounds
                                unitWholeValue = wordsList.get(i);
                                convertToUnit = GBP;
                                unitWholeValueTo = poundFull;
                                if (!localCheck) {
                                    mLogger.infoLog("word_AFTER_2_FROM_INPUT", "British " + wordsList.get(i));
                                    mLogger.infoLog("word_AFTER_2_FROM_LIST", "British " + unitWholeValue);
                                    mLogger.infoLog("word_AFTER_1_FROM_CODE", convertToUnit);
                                    localCheck = true;
                                }
                            }
                        }
                        break;
                    }

                    //Extract any other currency
                    for (String lcWord : lcValuesList){
                        if (lcWord.equalsIgnoreCase(wordsList.get(i))
                                || lcWord.equalsIgnoreCase(wordsList.get(i).substring(0,wordsList.get(i).length() - 1))){

                            mLogger.infoLog("word_AFTER_2_OTHER_CURR", "RAN");
                            unitWholeValue = lcValue;
                            convertToUnit = entry.getKey();
                            unitWholeValueTo = entry.getValue();
                            mLogger.infoLog("word_AFTER_2_FROM_INPUT", wordsList.get(i));
                            mLogger.infoLog("word_AFTER_2_FROM_LIST", unitWholeValue);
                            mLogger.infoLog("word_AFTER_1_FROM_CODE", convertToUnit);
                            break;

                        }
                    }
                }
                if (!convertToUnit.isEmpty()) break;
            }
            mLogger.infoLog("word_AFTER_WHAT_REMAINS ", userInput);
            mLogger.infoLog("-----------------------------------------------------------------------", "RAN");

            //iterate through the user input
            //NUMBER -> number
            mLogger.infoLog("--------------------------------NUMBER---------------------------------", "RAN");
            int[] numbers = {1,2,3,4,5,6,7,8,9,0};
            List<String> convertedNums = new ArrayList<>();
            for (int j = 0; j < wordsList.size(); j++){
                mLogger.infoLog("word_AFTER_3_NUMBER", "RAN - " + wordsList.get(j));
                for (int i = 0; i < numbers.length; i++){
                    convertedNums.add(String.valueOf(numbers[i]));
                    if (wordsList.get(j).contains(convertedNums.get(i))){
                        mLogger.infoLog("word_AFTER_3_NUMBER_FROM_INPUT", wordsList.get(j));
                        unitToConvert = wordsList.get(j);
                        break;
                    }
                }
            }
            //Handle "a"
            if ((!unitWholeValueFrom.isEmpty() && !unitWholeValueTo.isEmpty()) && (unitToConvert.isEmpty()))
                unitToConvert = "1";
            mLogger.infoLog("-----------------------------------------------------------------------", "RAN");

            mLogger.infoLog("--------------------------------RESULT---------------------------------", "RAN");
            mLogger.infoLog("word_AFTER_FINAL_NUMBER", unitToConvert);
            mLogger.infoLog("word_AFTER_FINAL_CONVERT_FROM", convertFromUnit);
            mLogger.infoLog("word_AFTER_FINAL_CONVERT_TO", convertToUnit);
            mLogger.infoLog("word_AFTER_FINAL_CONVERT_FROM_VALUE", unitWholeValueFrom);
            mLogger.infoLog("word_AFTER_FINAL_CONVERT_TO_VALUE", unitWholeValueTo);

            //Add sets to Hashmap
            keywords.put(UnitConstants.NUMBER, unitToConvert);
            keywords.put(UnitConstants.CONVERT_FROM, convertFromUnit);
            keywords.put(UnitConstants.CONVERT_TO, convertToUnit);
            keywords.put(UnitConstants.CONVERT_FROM_VALUE, unitWholeValueFrom);
            keywords.put(UnitConstants.CONVERT_TO_VALUE, unitWholeValueTo);
            mLogger.infoLog("-----------------------------------------------------------------------", "RAN");

            keci.onResult();
            //localCheck = false;
        }, null);
        return keywords;
    }

    public interface KeyWordExtractorCInterface{
        void onResult();
    }

}
