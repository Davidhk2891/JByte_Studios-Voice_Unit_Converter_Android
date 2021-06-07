package superunitconverter.nexmii.com.superunitconverter.models.conversion.inputalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitConstants;
import superunitconverter.nexmii.com.superunitconverter.util.Logger;

/**
 * KeywordsExtractor class for Measurements is a Singleton class.
 * Its main job is to collect the user input from a TextManager class (Temperature, length, etc)
 * and return a Hashmap with the three main components of the conversion. The number to be converted,
 * The unit to convert from and the unit to convert to
 */
public final class KeywordsExtractorM {

    private final Logger mLogger;

    private static KeywordsExtractorM instance;

    private KeywordsExtractorM(){
        mLogger = Logger.getLoggerInstance();
    }

    public static KeywordsExtractorM getInstance(){
        if (instance == null)
            instance = new KeywordsExtractorM();
        return instance;
    }

    private final HashMap<String, String> keywords = new HashMap<>();

    /**
     *
     * @param userInput -> The String collected from the user
     * @param unitLabels -> Which unit labels (In Array shape) are needed for the extraction
     * @return -> returns a HashMap of String values which contains a set of the number to be
     * converted, the original unit and the target unit.
     */
    public HashMap<String, String> exctractMeasurementKeywords(String userInput, List<String> unitLabels){

        List<String> wordsList = new ArrayList<>(Arrays.asList(userInput.split(" ")));
        String unitToConvert = "";
        String convertFromUnit = "";
        String convertToUnit = "";

        for (int i = 0; i < wordsList.size(); i++){
            mLogger.infoLog("word:", wordsList.get(i));
            for (int j = 0; j < unitLabels.size(); j++){
                if (wordsList.get(i).equalsIgnoreCase(unitLabels.get(j))){
                    mLogger.infoLog("word_temp_1",wordsList.get(i));
                    convertFromUnit = wordsList.get(i);
                    break;
                }
            }
            if (wordsList.get(i).equalsIgnoreCase(convertFromUnit)) break;
        }
        wordsList.remove(convertFromUnit);

        for (int i = 0; i < wordsList.size(); i++) {
            mLogger.infoLog("word_after:", wordsList.get(i));
            for (int j = 0; j < unitLabels.size(); j++){
                if (wordsList.get(i).equalsIgnoreCase(unitLabels.get(j))){
                    mLogger.infoLog("word_temp_2",wordsList.get(i));
                    convertToUnit = wordsList.get(i);
                    break;
                }
            }
            if (wordsList.get(i).equalsIgnoreCase(convertToUnit)) break;
        }
        wordsList.remove(convertToUnit);

        int[] numbers = {1,2,3,4,5,6,7,8,9,0};
        List<String> convertedNums = new ArrayList<>();
        for (int j = 0; j < wordsList.size(); j++){
            mLogger.infoLog("word_after_2", wordsList.get(j));
            for (int i = 0; i < numbers.length; i++){
                convertedNums.add(String.valueOf(numbers[i]));
                if (wordsList.get(j).contains(convertedNums.get(i))){
                    String number = wordsList.get(j);
                    if (wordsList.get(j).contains("°")) {
                        //For temperature specifically
                        number = wordsList.get(j).replace("°", "").trim();
                    }
                    mLogger.infoLog("word_number", number);
                    unitToConvert = number;
                    break;
                }
            }
        }

        //Handle "a"
        if ((!convertFromUnit.isEmpty() && !convertToUnit.isEmpty()) && (unitToConvert.isEmpty()))
            unitToConvert = "1";

        wordsList.remove(unitToConvert);

        keywords.put(UnitConstants.NUMBER, unitToConvert);
        keywords.put(UnitConstants.CONVERT_FROM, convertFromUnit);
        keywords.put(UnitConstants.CONVERT_TO, convertToUnit);

        return keywords;
    }
}
