package superunitconverter.nexmii.com.superunitconverter.controllers;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import java.util.Locale;

import superunitconverter.nexmii.com.superunitconverter.models.ConversionAccessRepo;
import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.Conversion;
import superunitconverter.nexmii.com.superunitconverter.models.history.db.ConversionRepo;
import superunitconverter.nexmii.com.superunitconverter.util.cloudstorage.InputRecorder;
import superunitconverter.nexmii.com.superunitconverter.views.innerfragments.ConversionBaseView;
import superunitconverter.nexmii.com.superunitconverter.views.innerfragments.UserInputForwarderInterface;

/**
 * Controller for VoiceConversionFrag. As this software follows the MVC pattern for organizing
 * and distributing tasks.
 */
public class ConversionController {

    //Properties
    private final Context mContext;
    private final Activity mActivity;

    private final InputRecorder mInputRecorder;

    //ctor:
    public ConversionController(Context ctx, Activity act){
        mContext = ctx;
        mActivity = act;
        mInputRecorder = InputRecorder.getInstance();
    }

    /**
     * This method serves as an interface for its activity to call this controller
     * @param cbv -> interface that serves as a call back
     */
    public void userInputManager(ConversionBaseView cbv){
        cbv.onUserInputClicked();
    }

    /**
     * This method simply passes the user input for further processing
     * @param userInput -> holds the string from the user input
     */
    public void userInputForwarder(String userInput, UserInputForwarderInterface uifi){

        ConversionAccessRepo conversionAccessRepo = new ConversionAccessRepo(mContext);
        conversionAccessRepo.userInputHandler(userInput, new ConversionAccessRepo.InputHandlerInterface() {
            @Override
            public void onInputHandledSuccessfully(String category, String number, String convertedNumber, String fromUnit, String toUnit, String date) {
                uifi.onUserTextParsed(category, number, convertedNumber, fromUnit, toUnit, date, null);
                saveInputToCloud(userInput, true);
                if (date == null)
                    saveConversion(category,number,convertedNumber,fromUnit,toUnit);
                else
                    saveConversion(category,number,convertedNumber,fromUnit,toUnit,date);
            }

            @Override
            public void onInputHandledFailed(String error) {
                saveInputToCloud(userInput, false);
                uifi.onUserTextParsed(null, null, null, null, null, null, error);
            }
        });
    }

    /**
     * Method for saving the conversion if this one was successful
     */
    private void saveConversion(String category, String number, String convertedNumber, String fromUnit, String toUnit, String date){
        if (mContext != null){
            Conversion conversion = new Conversion(fromUnit, number, toUnit, convertedNumber, category, date);
            saveToDB(conversion);
        }
    }

    /**
     *  Overloaded method for saving the conversion if this one was successful
     */
    private void saveConversion(String category, String number, String convertedNumber, String fromUnit, String toUnit){
        if (mContext != null){
            Conversion conversion = new Conversion(fromUnit, number, toUnit, convertedNumber, category, null);
            saveToDB(conversion);
        }
    }

    /**
     * Method for saving the conversion object to Room DB through Conversion Repo
     * @param conversion object to be saved
     */
    private void saveToDB(Conversion conversion){
        ConversionRepo mConversionRepo = new ConversionRepo(mActivity.getApplication());
        mConversionRepo.save(conversion);
    }

    /**
     * Save user input to Firestore using specified parameters.
     */
    private void saveInputToCloud(String userInput, boolean isConversionOk){
        mInputRecorder.setInputToSave(userInput, String.valueOf(Locale.getDefault())
                , Build.BRAND + " " + Build.MODEL, String.valueOf(Build.VERSION.SDK_INT)
                , isConversionOk);
    }
}
