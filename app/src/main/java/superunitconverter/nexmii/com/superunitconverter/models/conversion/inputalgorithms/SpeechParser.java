package superunitconverter.nexmii.com.superunitconverter.models.conversion.inputalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import superunitconverter.nexmii.com.superunitconverter.helpers.ErrorInterface;
import superunitconverter.nexmii.com.superunitconverter.helpers.ErrorLogMessages;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitConstants;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitLists;
import superunitconverter.nexmii.com.superunitconverter.util.Logger;

/**
 * This Singleton class reads the user's voice input and parses it to the correct units needed
 * to run the conversion.
 * For example, if it reads 'mm', as the user's input comes through, then it will convert that to
 * millimeters
 */
public final class SpeechParser {

    private static SpeechParser instance;
    private final Logger mLogger;

    private SpeechParser(){
        mLogger = Logger.getLoggerInstance();
    }

    public static SpeechParser getInstance(){
        if (instance == null) instance = new SpeechParser();
        return instance;
    }

    /**
     * @param userInput the direct input from the SpeechRecognizer object
     * @return -> returns an update String with the symbols parsed into proper words the keyword
     * engines(algorithms) can understand.
     *
     * This algorithm is quite tricky and its open to a lot of modifications.
     * When it receives the user input from the SpeechRecognizer, it will look for(in an orderly manner),
     * the dollar sign (representing that the conversion includes the US dollar) attached to the number
     * and it will remove it so the number can be isolated. Then, it will look for the word 'in' right
     * after the number(which indicates its an inches-to-something conversion) and it will parse it immediatly
     * to 'inch' or 'inches'. Once this is done, it will look for the other symbols to parse to their regular words
     * excluding 'in' since it was already parsed. All of this while taking into account whether the coin to convert
     * is 1 or more than 1 coin (so it can add the 's' at the end).
     *
     * The algorithm appears to rely heavily in the Locale being en or en-US or any other english based language.
     *
     * Throwables to cover: indexOutOfBounds, nullPointerException, NumberFormatException
     *
     */
    public String parseWordFromInput(String userInput, ErrorInterface ei){
        ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(userInput.split(" ")));
        StringBuilder newInput = new StringBuilder();
        String number = "";
        for (int i = 0; i < wordsList.size(); i++){

            int[] nums = {1,2,3,4,5,6,7,8,9};
            for (int num : nums) {
                if (wordsList.get(i).contains(String.valueOf(num))) {
                    number = wordsList.get(i);

                    //HANDLE COMMAS
                    if (wordsList.get(i).contains(",")){
                        mLogger.infoLog("NEW_INPUT_COMMA_SEARCH", "IS: " + wordsList.get(i));
                        String numberNoComma = wordsList.get(i).replace(",", "");
                        mLogger.infoLog("NEW_INPUT_COMMA_SEARCH_BEFORE", "IS: " + numberNoComma);
                        number = numberNoComma;
                        wordsList.set(i, numberNoComma);
                    }
                    mLogger.infoLog("NEW_INPUT_COMMA_SEARCH_AFTER", "IS: " + wordsList);

                    //HANDLE DOLLAR OR OTHER SYMBOLS STICKED TO THE NUMBER
                    for (Map.Entry<String, String> symbolEntry : UnitLists.getInstance().getUnitSymbolsList().entrySet()){
                        if (number.contains(symbolEntry.getKey())) {
                            try {
                                mLogger.infoLog("NEW_INPUT_COMMA_SEARCH_AFTER_2", "IS: " + wordsList);
                                number = number.replace(symbolEntry.getKey(), "");
                                wordsList.set(i, number);
                                if (Integer.parseInt(number) > 1)
                                    //more than 1 dollar or symbols
                                    wordsList.add(i + 1, symbolEntry.getValue() + "s");
                                else {
                                    //just 1 dollar or symbol
                                    wordsList.add(i + 1, symbolEntry.getValue());
                                }
                            }catch(NumberFormatException e){
                                mLogger.infoLog("NEW_INPUT_ERROR", "IS: 1 - " + e.getMessage());
                                ei.onErrorCalled(ErrorLogMessages.CANNOT_READ_NUMBER);
                                return null;
                            }
                        }
                    }
                    number = number.trim();
                    mLogger.infoLog("NEW_INPUT_NUMBER_NEW_LIST", "IS: " + wordsList);

                    //HANDLE INCHES(only parses to 'in' if its right after the number)
                    try {
                        if (wordsList.get(i + 1).equalsIgnoreCase(UnitConstants.INCH_SYMBOL)) {
                            if (Integer.parseInt(number) > 1) {
                                //more than 1 inch
                                wordsList.set(i + 1, UnitConstants.INCHES);
                            } else {
                                //1 inch
                                wordsList.set(i + 1, UnitConstants.INCH);
                            }
                        }
                    }catch(IndexOutOfBoundsException | NumberFormatException e){
                        mLogger.infoLog("NEW_INPUT_ERROR", "IS: 2 - " + e.getMessage());
                        ei.onErrorCalled(ErrorLogMessages.UNKNOWN_ERROR);
                        return null;
                    }
                }
            }

            for (Map.Entry<String, String> symbolEntry : UnitLists.getInstance().getUnitSymbolsList().entrySet()){
                //HANDLE SYMBOLS
                if (wordsList.get(i).equalsIgnoreCase(symbolEntry.getKey())){
                    if (!wordsList.get(i).equalsIgnoreCase(UnitConstants.INCH_SYMBOL)) {
                        try {
                            if (Double.parseDouble(number) > 1) {
                                //more than 1 coins
                                if (!wordsList.get(i).equalsIgnoreCase(UnitConstants.SHEQELS_SYMBOL)
                                        && !wordsList.get(i).equalsIgnoreCase(UnitConstants.MILLION_SYMBOL)
                                        && !wordsList.get(i).equalsIgnoreCase(UnitConstants.BILLION_SYMBOL)
                                        && !wordsList.get(i).equalsIgnoreCase(UnitConstants.TRILLION_SYMBOL)
                                        && !wordsList.get(i).equalsIgnoreCase(UnitConstants.QUADRILLION_SYMBOL)
                                        && !wordsList.get(i).equalsIgnoreCase(UnitConstants.CELSIUS_SYMBOL)
                                        && !wordsList.get(i).equalsIgnoreCase(UnitConstants.FAHRENHEIT_SYMBOL)
                                        && !wordsList.get(i).equalsIgnoreCase(UnitConstants.KELVIN_SYMBOL)) {
                                    wordsList.set(i, symbolEntry.getValue() + "s");
                                } else {
                                    wordsList.set(i, symbolEntry.getValue());
                                }
                            } else {
                                //just 1 coin
                                wordsList.set(i, symbolEntry.getValue());
                            }
                        }catch(NumberFormatException e){
                            mLogger.infoLog("NEW_INPUT_ERROR", "IS: 3 - " + e.getMessage());
                            ei.onErrorCalled(ErrorLogMessages.UNKNOWN_ERROR);
                            return null;
                        }
                    }
                    //HANDLE ILLIONS
                    if (wordsList.get(i).equalsIgnoreCase(UnitConstants.MILLION_LITERAL)
                            || wordsList.get(i).equalsIgnoreCase(UnitConstants.BILLION_LITERAL)
                            || wordsList.get(i).equalsIgnoreCase(UnitConstants.TRILLION_LITERAL)
                            || wordsList.get(i).equalsIgnoreCase(UnitConstants.QUADRILLION_LITERAL)){
                        String newFullNum = number + wordsList.get(i);
                        wordsList.set(i, newFullNum);
                        wordsList.remove(number);
                    }
                }
            }
        }

        for (int i = 0; i < wordsList.size(); i++){
            newInput.append(wordsList.get(i)).append(" ");
        }

        mLogger.infoLog("NEW_INPUT", "IS: " + newInput.toString());
        return newInput.toString();
    }
}
