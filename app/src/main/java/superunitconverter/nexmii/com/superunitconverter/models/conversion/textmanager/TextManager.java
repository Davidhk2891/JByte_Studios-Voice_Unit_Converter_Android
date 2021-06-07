package superunitconverter.nexmii.com.superunitconverter.models.conversion.textmanager;

import java.util.HashMap;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.inputalgorithms.KeywordsExtractorC;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.inputalgorithms.KeywordsExtractorM;

/**
 * TextManager parent class. Inherited by all TextManager classes to perform
 * all input handling operations.
 */
public abstract class TextManager {

    /**
     * Calls the KeywordExtractorM for measurement conversions
     * @return returns the only available instance of the class
     */
    public KeywordsExtractorM getKeywordsExtractorM() {
        return KeywordsExtractorM.getInstance();
    }

    /**
     * Calls the KeywordExtractorC for live currency conversions
     * @return returns the only available instance of the class
     */
    public KeywordsExtractorC getKeywordsExtractorC(){
        return KeywordsExtractorC.getInstance();
    }

    /**
     * Used to instantiate keywords collection
     * @param userInput user input from speech or manual input
     */
    public abstract void keywordsCollectorInit(String userInput);

    public abstract HashMap<String, String> manageText(String userInput);

}
