package superunitconverter.nexmii.com.superunitconverter.util.cloudstorage;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import superunitconverter.nexmii.com.superunitconverter.util.RandomStringGenerator;

/**
 * This Singleton class takes the input from the user, and upon printing an output, it saves it
 * to Firestore (if the device has internet connection)
 */
public class InputRecorder {

    private static final String TAG = "InputRecorder";

    private static InputRecorder instance;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private InputRecorder(){}

    public static synchronized InputRecorder getInstance(){
        if (instance == null)
            instance = new InputRecorder();
        return instance;
    }

    /**
     * noSQL Database:
     * Collection(Table): UserInput
     * Document(Row): Unique identifier
     * Fields(Columns):
     *      - user input (String)
     *      - location (String)
     *      - phone model (String)
     *      - android OS (String)
     *      - is conversion OK (boolean)
     */
    public void setInputToSave(String userInput, String location, String phoneModel
            ,String androidOS, boolean isConvesionOK){
        RecordedInput recordedInput = new RecordedInput(userInput,location
                ,phoneModel,androidOS,isConvesionOK);
        Map<String, Object> setInput = new HashMap<>();
        setInput.put(FirestoreConstants.FIELD_USER_INPUT,recordedInput.getUserInput());
        setInput.put(FirestoreConstants.FIELD_LOCATION,recordedInput.getLocation());
        setInput.put(FirestoreConstants.FIELD_PHONE_MODEL,recordedInput.getPhoneModel());
        setInput.put(FirestoreConstants.FIELD_ANDROID_OS,recordedInput.getAndroidOS());
        setInput.put(FirestoreConstants.FIELD_IS_CONVERSION_OK,recordedInput.isConversionOk());
        saveInputToFirestore(setInput, recordedInput.getUserInput(), String.valueOf(isConvesionOK));
    }

    private void saveInputToFirestore(Map<String, Object> recordedInput, String userInput,
                                      String isConversionOK){
        db
                .collection(FirestoreConstants.COLLECTION_USER_INPUT)
                .document(RandomStringGenerator.getInstance()
                        .generateRandomString(4)+ " - " + isConversionOK.toUpperCase()
                        + " - " + userInput)
                .set(recordedInput)
                .addOnSuccessListener(unused -> Log.i(TAG, "Success: Input recorded"))
                .addOnFailureListener(e -> Log.i(TAG, "Failure: Input not recorded - " + e.getMessage()));
    }
}
