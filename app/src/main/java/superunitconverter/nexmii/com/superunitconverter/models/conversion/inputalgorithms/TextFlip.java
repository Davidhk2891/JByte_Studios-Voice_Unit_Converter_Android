package superunitconverter.nexmii.com.superunitconverter.models.conversion.inputalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitLists;
import superunitconverter.nexmii.com.superunitconverter.util.Logger;

/**
 * This class purpose is to collect the text input from the speech and before it passes it on to the next algos, it will
 * check if the text is inverted and the conversion is asked in a way that isn't the correct one.
 * If so, it will attempt to flip the text into the format that the other algorithms can understand.
 * Singleton class.
 */
public final class TextFlip {

    private static TextFlip instance;

    private boolean flippingRequired = false;
    private boolean amountIndexLocated = false;
    private boolean unitBeforeLocated = false;

    private final Logger mLogger;

    private TextFlip(){
        mLogger = Logger.getLoggerInstance();
    }

    public static TextFlip getInstance(){
        if (instance == null) instance = new TextFlip();
        return instance;
    }

    /**
     * The flip method does what the method name says. It takes the user userInput and flips it in a way
     * that the next algos can understand.
     * @param userInput user userInput from SpeechRecognizer
     * @return the user userInput inverted in a way that the next algos can understand it
     */
    public String checkAndFlip(String userInput){
        //How do I correctly understand that the userInput is inverted?

        //Example: "How many kelvin is 20 degrees celsius"

        //How it should be: "how much is 20 celcius in kelvin"

        StringBuilder finalTextBuild = new StringBuilder();
        String finalTextString = "";
        String unit1 = "";
        String to = "to";
        int amountIndex = 0;

        List<String> allUnits = Stream.of(UnitLists.getInstance().getTempUnitList()
                ,UnitLists.getInstance().getLengthUnitList()
                ,UnitLists.getInstance().getVolumeUnitList()
                ,UnitLists.getInstance().getWeightUnitList())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        List<String> words = new ArrayList<>(Arrays.asList(userInput.split(" ")));

        //Handle "a"
        //TODO: How to handle "a" in text flipping

        //Main filter that defines that the sentence needs to be flipped:
        //If both units come after the number, then the sentence is OK (amountIndex < n)
        //If one unit is before the number, then this needs to be flipped (amountIndex > n)
        if (!amountIndexLocated) {
            int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            for (int i = 0; i < words.size(); i++) {
                for (int number : numbers) {
                    if (words.get(i).contains(String.valueOf(number))) {
                        amountIndex = i;
                        amountIndexLocated = true;
                        mLogger.infoLog("FLIPPED_STEP_1_OK", "YES");
                        break;
                    }
                }
            }
        }

        if (amountIndexLocated) {
            for (int i = 0; i < words.size(); i++) {
                for (int k = 0; k < allUnits.size(); k++) {
                    if (words.get(i).equalsIgnoreCase(allUnits.get(k))) {
                        //the word from the first word list is a unit
                        if (amountIndex > i){
                            //The sentence needs to be flipped!
                            flippingRequired = true;
                            mLogger.infoLog("FLIPPED_STEP_2_OK", "YES");
                            break;
                        }
                    }
                }
            }
        }

        if (flippingRequired){
            for (int i = 0; i < words.size(); i++){
                for (int j = 0; j < allUnits.size(); j++){
                    if (words.get(i).equalsIgnoreCase(allUnits.get(j))){
                        unit1 = words.get(i);
                        words.remove(unit1);
                        unitBeforeLocated = true;
                        break;
                    }
                }
                if (unitBeforeLocated) {
                    mLogger.infoLog("FLIPPED_STEP_3_OK", "YES");
                    break;
                }
            }
            words.add(to);
            words.add(unit1);
        }

        if (unitBeforeLocated) {
            for (String word : words){
                finalTextBuild.append(word).append(" ");
            }
            finalTextString = finalTextBuild.toString().trim();
            mLogger.infoLog("FLIPPED_FINAL", "YES - " + finalTextString);
            flippingRequired = false;
            amountIndexLocated = false;
            unitBeforeLocated = false;
            return finalTextString;
        }else{
            mLogger.infoLog("FLIPPED_FINAL", "NO - " + finalTextString + " - " + userInput);
            flippingRequired = false;
            amountIndexLocated = false;
            unitBeforeLocated = false;
            return userInput;
        }
    }
}
