package superunitconverter.nexmii.com.superunitconverter.models;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

import superunitconverter.nexmii.com.superunitconverter.R;
import superunitconverter.nexmii.com.superunitconverter.helpers.ErrorAnalyzer;
import superunitconverter.nexmii.com.superunitconverter.helpers.ErrorLogMessages;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.inputalgorithms.SpeechParser;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.inputalgorithms.TextFlip;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitConstants;
import superunitconverter.nexmii.com.superunitconverter.util.CurrentDate;
import superunitconverter.nexmii.com.superunitconverter.util.RoundNumber;

/**
 * Class that serves as the main and sole entry point to the conversion operation models regardless
 * of where it's being accessed from. Any controller that requires conversion can access this class.
 * - This class will also serve repo functionality in future versions. If conversions can be done from the internet,
 * it will attempt to implement these, which can grant a wider variety of conversions.
 */
public class ConversionAccessRepo {

    //Properties
    private static final String TAG = "ConversionAccessRepo";

    private final Context mContext;

    private String number;
    private String convertedNumber;
    private String fromUnit;
    private String toUnit;
    private String category;
    private String date;

    private final ErrorAnalyzer mErrorAnalyzer;
    private boolean errorFound = false;

    public ConversionAccessRepo(Context ctx){
        mContext = ctx;
        mErrorAnalyzer = ErrorAnalyzer.getInstance();
    }

    /**
     * Method which servers as the main access point to the App's conversion logic
     * In short, it takes user input (through the first parameter) and
     * spits out an output through a local interface.
     * The output can be either the successful conversion or an error message.
     */
    public void userInputHandler(String userInput, InputHandlerInterface ihi){
        errorFound = false;
        if (!userInput.isEmpty()) {
            CategoryManager categoryManager = new CategoryManager(mContext);
            RoundNumber roundNumber = RoundNumber.getInstance();
            String parsedInput = SpeechParser.getInstance().parseWordFromInput(TextFlip.getInstance().checkAndFlip(userInput.toLowerCase())
                    , (errorCode) -> {
                        //Handle error display for user: MANY POSSIBLE ERRORS
                        ihi.onInputHandledFailed(mErrorAnalyzer.analyzeError(mContext,errorCode));
                        errorFound = true;
                    });
            if (parsedInput != null){
                categoryManager.stringInputCategoryManager(parsedInput, (HashMap<String, String> finalResultSet, String eCode) -> {
                    //observer pattern. it takes time to get result because of currency API. Below code needs to execute on a callback
                    if (eCode != null) {
                        //Handle other types of errors display for user: {}
                        ihi.onInputHandledFailed(mErrorAnalyzer.analyzeError(mContext, eCode));
                        errorFound = true;
                    }
                    if (!errorFound) {
                        if (finalResultSet != null) {
                            category = finalResultSet.get(UnitConstants.CATEGORY);
                            number = roundNumber.roundedResult(finalResultSet.get(UnitConstants.NUMBER)
                                    , (errorCode) -> {
                                        //Handle error display for user: CANNOT ROUND NUMBER
                                        ihi.onInputHandledFailed(mErrorAnalyzer.analyzeError(mContext, errorCode));
                                        errorFound = true;
                                    });
                            convertedNumber = roundNumber.roundedResult(finalResultSet.get(UnitConstants.CONVERTED_NUMBER)
                                    , (errorCode) -> {
                                        //Handle error display for user: CANNOT ROUND NUMBER
                                        ihi.onInputHandledFailed(mErrorAnalyzer.analyzeError(mContext, errorCode));
                                        errorFound = true;
                                    });
                            Log.d(TAG, "userInputHandler: conver numb: " + convertedNumber);
                            if (category.equals(UnitConstants.CURRENCY)) {
                                fromUnit = finalResultSet.get(UnitConstants.CONVERT_FROM_VALUE);
                                Log.d(TAG, "userInputHandler: from unit curr: " + fromUnit);
                                toUnit = finalResultSet.get(UnitConstants.CONVERT_TO_VALUE);
                                Log.d(TAG, "userInputHandler: to unit curr: " + toUnit);
                            } else {
                                fromUnit = finalResultSet.get(UnitConstants.CONVERT_FROM);
                                Log.d(TAG, "userInputHandler: from unit: " + fromUnit);
                                toUnit = finalResultSet.get(UnitConstants.CONVERT_TO);
                                Log.d(TAG, "userInputHandler: to unit: " + toUnit);
                            }
                            if ((toUnit == null) || (convertedNumber.equals(String.valueOf(0)) && toUnit.isEmpty())){
                                //If converted number is 0 and the target unit is empty or null
                                ihi.onInputHandledFailed(mErrorAnalyzer.analyzeError(mContext, ErrorLogMessages.CANNOT_READ_NUMBER));
                                errorFound = true;
                            }
                            if (!errorFound) {
                                //If all went well
                                if (category.equals(mContext.getResources().getString(R.string.category_currency))) {
                                    date = CurrentDate.getSharedCDInstance().currentDate();
                                    ihi.onInputHandledSuccessfully(category,number,convertedNumber,fromUnit,toUnit,date);
                                } else {
                                    ihi.onInputHandledSuccessfully(category,number,convertedNumber,fromUnit,toUnit,null);
                                }
                            }
                        }
                    }
                });
            }
        } else {
            //Handle error display for user: USER INPUT IS EMPTY
            String errorCode = ErrorLogMessages.USER_EMPTY_INPUT;
            ihi.onInputHandledFailed(mErrorAnalyzer.analyzeError(mContext, errorCode));
        }
    }

    /**
     * Local interface used by the userInputHandler() method.
     */
    public interface InputHandlerInterface{
        void onInputHandledSuccessfully(String category, String number, String convertedNumber, String fromUnit, String toUnit, String date);
        void onInputHandledFailed(String error);
    }
}
