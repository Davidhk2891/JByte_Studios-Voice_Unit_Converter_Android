package superunitconverter.nexmii.com.superunitconverter.views.innerfragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

import superunitconverter.nexmii.com.superunitconverter.R;
import superunitconverter.nexmii.com.superunitconverter.controllers.ConversionController;
import superunitconverter.nexmii.com.superunitconverter.helpers.ErrorUserAssistant;
import superunitconverter.nexmii.com.superunitconverter.util.ResultColor;
import superunitconverter.nexmii.com.superunitconverter.util.StringSpanner;
import superunitconverter.nexmii.com.superunitconverter.util.animations.ConversionAnimations;
import superunitconverter.nexmii.com.superunitconverter.util.animations.EqualizerAnimations;

/**
 * ConversionFragment is the class that handles all of the following:
 *
 * - Initializing all relevant fields to the class.
 * - The accepting of user input.
 * - Printing out output.
 * - Hnadling flow control on different states based from the SpeechRecognition object and the mic button view.
 * - handling the Lottie Equalizer animation in relation to the satus of of the SpeechRecognition object and the mic button view.
 * - Hnadling the background color of the category selected for the result of the conversion.
 * - Animation the positive and negative result.
 * - Displaying the positive and negative result.
 * - Permissions for phone's voice input.
 * - Handling all SpeechRecognition operations.
 *
 * THIS IS THE LONGEST CLASS. HAS ABOUT 530 LINES. NEEDS IMPROVEMENT
 */
public class VoiceConversionFrag extends Fragment {

    //Properties:
    public static final Integer RECORD_AUDIO_REQUEST_CODE = 1;

    //Classes
    private EqualizerAnimations mEqualizerAnimations;
    private ConversionAnimations conversionAnimations;
    private ConversionController mConversionController;

    //Views
    private ConstraintLayout mVoiceConstraintMain;
    private TextView mTvInput;
    private ViewPager2 viewPager2Child;
    private ImageButton mBtnConvertChild;
    private ImageButton mBtnHistoryChild;
    private ImageButton mBtnCCChild;
    private LottieAnimationView mEqualizerLogo;

    // - Views: Positive output
    private LinearLayout resultContainerView;
    private LinearLayout convertToData;
    private TextView convertFromNumber;
    private TextView convertFromUnit;
    private TextView convertToNumber;
    private TextView convertToUnit;
    private TextView asOf;

    // - Views: Negative output (Error)
    private LinearLayout errorContainerView;
    private TextView mTvErrorTitle;
    private TextView mTvErrorMessage;

    // Android Object: SpeechRecognizer
    private SpeechRecognizer mSpeechRecognizer;
    private Intent speechRecognizerIntent;

    //Strings
    private String input;

    //booleans
    private boolean inputBtnPressed = false;
    private boolean resultsGiven = false;
    private boolean errorGiven = false;
    private boolean speechEngaged = false;

    public VoiceConversionFrag() {
        // Required empty public constructor
    }

    /**
     * Fragment's onCreateView: Third necessary overriden method for initializing a fragment which comes from a main navigation host, which is attached to an activiy.
     * It goes like this: onAttach() -> onCreate() -> onCreateView() -> onActivityCreated() -> onStart() - > onResume() - >>Fragment running<<
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        //Instantiate objects
        View v = inflater.inflate(R.layout.fragment_voice_conversion, container, false);
        fieldsInit(v);
        setSpeechRecognizer();
        prepResult();
        goToManualConverter();
        return v;
    }

    /**
     * Fragment's onCreateView: Fourth necessary overriden method for initializing a fragment which comes from a main navigation host, which is attached to an activiy.
     * It goes like this: onAttach() -> onCreate() -> onCreateView() -> onActivityCreated() -> onStart() - > onResume() - >>Fragment running<<
     * I assume, that this is equals in some way to onActivityCreated() since I cannot find onViewCreated() anywhere on the fragment life cycle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //call actions
        inputClick();
    }

    /**
     * Fragment's onCreateView: second necessary overriden method for initializing a fragment which comes from a main navigation host, which is attached to an activiy.
     * It goes like this: onAttach() -> onCreate() -> onCreateView() -> onActivityCreated() -> onStart() - > onResume() - >>Fragment running<<
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Initializes fields: Views, other objects, global Strings and global primitive variables. Gets called by onCreate()
     */
    private void fieldsInit(View v) {
        mConversionController = new ConversionController(getContext(), getActivity());
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity());
        conversionAnimations = new ConversionAnimations(getContext());
        mEqualizerAnimations = EqualizerAnimations.getInstance();

        mTvInput = v.findViewById(R.id.tvInput);

        if (getActivity() != null) {
            viewPager2Child = getActivity().findViewById(R.id.mainVNavViewPager);
            mBtnConvertChild = getActivity().findViewById(R.id.btnVoiceConvert);
            mBtnHistoryChild = getActivity().findViewById(R.id.btnHistory);
            mBtnCCChild = getActivity().findViewById(R.id.btnTables);
        }

        mVoiceConstraintMain = v.findViewById(R.id.voiceConstraintMain);
        mEqualizerLogo = v.findViewById(R.id.equalizerLogo);
        resultContainerView = v.findViewById(R.id.output_container);
        convertFromNumber = resultContainerView.findViewById(R.id.convert_from_number);
        convertFromUnit = resultContainerView.findViewById(R.id.convert_from_unit);
        convertToNumber = resultContainerView.findViewById(R.id.convert_to_number);
        convertToUnit = resultContainerView.findViewById(R.id.convert_to_unit);
        convertToData = resultContainerView.findViewById(R.id.convert_to_data);
        asOf = resultContainerView.findViewById(R.id.as_of);

        errorContainerView = v.findViewById(R.id.error_container);
        mTvErrorTitle = errorContainerView.findViewById(R.id.tvErrorTitle);
        mTvErrorMessage = errorContainerView.findViewById(R.id.tvErrorMessage);
    }

    /**
     * Method which listens for Button input.
     * Calls SpeechRecognizer object and activates it to listen for user speech input.
     * Inside the lambda callback, it runs the checkSelfPermission() to see if voice listener
     * permission was granted or not.
     * Below that, it checks if the button is currently pressed. If it is, it will run stopListening().
     * Otherwise, it will run startListening
     */
    private void inputClick() {
        mBtnConvertChild.setOnClickListener(view -> {
            Log.i("CONVERSION_FRAG", "voice");
            //Check self-permission
            checkSelfPermission();
            //Call SpeechRecognizer
            if (!inputBtnPressed)
                startListening();
            else
                stopListening();
        });
    }

    /**
     * Runs all the necessary processes needed for the proper listening process of the app:
     * - Checks if there were results from a previous conversion. If so, whether it was a positive or
     *   negative result, it will reset its layout and animations back to their original position
     * - Sets the mic button to black background, green mic
     * - Sets input text to empty
     * - Sets input hint to "Listening..."
     * - Calls mSpeechRecognizer.startListeneing() for listening for voice input
     * - Sets boolean inputBtnPressed to true
     */
    private void startListening(){
        if (resultsGiven) {
            resetPositiveResultsBack();
            resultsGiven = false;
        }else if (errorGiven) {
            resetNegativeResultsBack();
            errorGiven = false;
        }
        micBlack();
        screenEnabled(false);
        mTvInput.setText("");
        mTvInput.setHint("Listening...");
        mSpeechRecognizer.startListening(speechRecognizerIntent);
        inputBtnPressed = true;
    }

    /**
     * Runs all the necessary processes needed to stop the voice listening process of the app:
     * - Checks boolean speechEngaged. If its true, then the app is currently listening to voice.
     *   If so, stop the animation with the stopAnimation method
     * - Calls mSpeechRecognizer.stopListening() to stop listening for voice input
     * - Sets the mic button to green background, black mic
     * - Sets input text to empty
     * - Sets input hint to empty
     * - Sets boolean inputBtnPressed to false
     * - Sets boolean speechEngaged to false
     * - calls the buttonEnabled method for enabling the mic button in case it was disabled
     */
    private void stopListening(){
        if (speechEngaged)
            mEqualizerAnimations.stopAnimation(mEqualizerLogo);
        micGreen();
        mSpeechRecognizer.stopListening();
        mTvInput.setText("");
        mTvInput.setHint("");
        inputBtnPressed = false;
        speechEngaged = false;
        screenEnabled(true);
        buttonEnabled(true);
    }

    /**
     * Calls and sets the mic button to green background and black mic
     */
    private void micGreen(){
        mBtnConvertChild.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.micgreen));}

    /**
     * Calls and sets the mic button to black background and green mic
     */
    private void micBlack(){
        mBtnConvertChild.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.micblack));}

    /**
     * This method initiates the SpeechRecognizer object and calls all its methods from that class
     * which are necessary for properly transcribing the user's input speech into text:
     * From the overriden anonymous class, there are nine methods. Out of which, the following are used at the moment:
     * - onBeginningOfSpeech(): Fires up when the system detects sound coming into the phone's mic. It runs the following:
     *    + Sets speechEngaged to true
     *    + runs the PlayAnimation() from the EqualizerAnimations class. It fires up the equalizer animation
     * - onEndOfSpeech(): Fires up when the system no longer detects sound coming into the phone's mic. It runs the following:
     *    + Sets the mic button to green background, black mic
     *    + Sets boolean inputBtnPressed to false
     *    + Sets boolean speechEngaged to false
     *    + calls the buttonEnabled method for enabling the mic button in case it was disabled
     *    + runs the stopAnimation() from the EqualizerAnimation class. It stops the equalizer animation if speechEngaged is true
     * - onError(): Fires up when the system could not detect any speech coming in, or some failure occurred while trying to detect the speech:
     *    + Sets the mic button to green background, black mic
     *    + Sets input text to empty
     *    + Sets input hint to empty
     *    + Sets the boolean inputBtnPressed to false
     *    + Calls the buttonEnabled method for enabling the mic button in case it was disabled
     *    + runs the stopAnimation() from the EqualizerAnimation class. It stops the equalizer animation if speechEngaged is true
     * - onResults(): Fires up when the SpeechRecognizer got some input and its done transcrabing it. Collects it all on a bundle object from which
     *   an ArrayList gets its data:
     *    + initializes a String ArrayList for the data collected from the speech input
     *    + Collects the data, puts in a String called 'input'
     *    + Fires up the runConversionFromDataReceived() if the data received isn't null
     *    + Sets boolean inputBtnPressed to false
     *    + Calls the buttonEnabled method for enabling the mic button in case it was disabled
     * - onPartialResults(): Fires up gradually as the SpeechRecognition object receives data and puts it in a bundle as long as its able to transcribe it:
     *    + runs a few operations as long as 'data' isn't null
     *    + Sets input to lowercase
     *    + prints data to mTvInput
     */
    private void setSpeechRecognizer() {
        String language = "en-US";
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, language);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES, language);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, language);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, language);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_RESULTS, language);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                speechEngaged = true;
                mEqualizerAnimations.PlayAnimation(mEqualizerLogo);
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                micGreen();
                inputBtnPressed = false;
                speechEngaged = false;
                buttonEnabled(true);
                screenEnabled(true);
                mEqualizerAnimations.stopAnimation(mEqualizerLogo);
            }

            @Override
            public void onError(int i) {
                micGreen();
                mTvInput.setText("");
                mTvInput.setHint("");
                inputBtnPressed = false;
                buttonEnabled(true);
                screenEnabled(true);
                if (speechEngaged)
                    mEqualizerAnimations.stopAnimation(mEqualizerLogo);
            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (data != null) {
                    input = data.get(0).toLowerCase();
                    mTvInput.setText(input);
                    runConversionFromDataReceived();
                }else{
                    micGreen();
                    buttonEnabled(true);
                    screenEnabled(true);
                }
                inputBtnPressed = false;
                buttonEnabled(true);
                screenEnabled(true);
            }

            @Override
            public void onPartialResults(Bundle bundle) {
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (data != null) {
                    buttonEnabled(false);
                    screenEnabled(true);
                    input = data.get(0).toLowerCase();
                    mTvInput.setText(input);
                }
            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
    }

    /**
     * This method initializes the process of converting the user input if approved.
     * If it's wrong for any reason, the system will let the user know.
     * If it's correct, then the userTextForwarder() method will be called from
     * ConversionController
     */
    private void runConversionFromDataReceived() {
        mConversionController.userInputManager(() -> {
            micGreen();
            mConversionController.userInputForwarder(input, (String category, String number
                    , String convertedNumber, String fromUnit, String toUnit, String date, String eCode) -> {
                if (eCode != null){
                    //There is an error with the conversion
                    printNegativeResults(eCode);
                    showNegativeResults();
                    errorGiven = true;
                }else {
                    //There are no errors. All clear for conversion
                    printPositiveResults(number, convertedNumber, fromUnit, toUnit, category, date);
                    showPositiveResults(category);
                    resultsGiven = true;
                }
            });
        });
    }

    /**
     * Prints the positive result of the conversion. Meaning the conversion was successful and everything worked alright
     * @param number original number that needs conversion
     * @param convertedNumber the result from the conversion. The converted number in the target unit
     * @param fromUnit the orginal unit to convert from
     * @param toUnit the target unit
     */
    private void printPositiveResults(String number, String convertedNumber, String fromUnit, String toUnit, String category, String date){
        convertFromNumber.setText(number);
        convertFromUnit.setText(fromUnit);
        convertToNumber.setText(convertedNumber);
        convertToUnit.setText(toUnit);
        if (getContext() != null) {
            if (category.equals(getContext().getResources().getString(R.string.category_currency))){
                asOf.setVisibility(View.VISIBLE);
                String conversionDate = getContext().getResources().getString(R.string.as_of) + " " + date;
                asOf.setText(conversionDate);
            }else{
                asOf.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * Controls the flow of displaying the positive result, including instantiating the view and running
     * its animations
     */
    private void showPositiveResults(String category){
        resultContainerView.setVisibility(View.VISIBLE);
        ResultColor.getInstance().convertToBgColor(getContext(), category, convertToData);
        conversionAnimations.animateResult(mTvInput, resultContainerView);
    }

    /**
     * Resets the positive result back to its original position and hidden from sight.
     * This means the user is converting something else
     */
    private void resetPositiveResultsBack(){
        conversionAnimations.animateRevertBackResult(mTvInput, resultContainerView);
        resetResults();
        resultsGiven = false;
    }

    /**
     * Prints the error in case there was one. This is the end point of the entire error system in the app.
     * This is where it ends. It prints to the user something went wrong. On the background, the correct message of what
     * happened is also fired.
     * @param message the error message which depends on what Exception or flow control error was triggered.
     */
    private void printNegativeResults(String message){
        ErrorUserAssistant.getInstance().addErrorsCommited();
        if (ErrorUserAssistant.getInstance().getErrorsCommited() >= 2){
            //Add message in error message to suggest user to go to oldie converter
            mTvErrorTitle.setText(getResources().getString(R.string.error));
            String text = getResources().getString(R.string.cannot_con_try_oldie);
            mTvErrorMessage.setText(StringSpanner.getInstance().coloredString(text, "#2076E8"
                    ,18,0,true));
        } else {
            mTvErrorTitle.setText(getResources().getString(R.string.error));
            mTvErrorMessage.setText(message);
        }
    }

    /**
     * Controls the flow of displaying the negative result.
     */
    private void showNegativeResults(){
        errorContainerView.setVisibility(View.VISIBLE);
        conversionAnimations.animateError(mTvInput, errorContainerView);
    }

    /**
     * Resets the negative result back to its original position and hidden from sight.
     * This means the user is converting something else
     */
    private void resetNegativeResultsBack(){
        conversionAnimations.animateRevertBackError(mTvInput, errorContainerView);
        resetResults();
        errorGiven = false;
    }

    /**
     * Click on the message from the Error output and the user will be taken to the manual converter.
     * This happens after the second failed conversion.
     */
    private void goToManualConverter(){
        mTvErrorMessage.setOnClickListener(v->{
            if (mTvErrorMessage.getText().toString().equals(getResources()
                    .getString(R.string.cannot_con_try_oldie)))
                viewPager2Child.setCurrentItem(1);
        });
    }

    /**
     * Resets all TextViews to empty.
     * When this happens it means the user is converting something else
     */
    private void resetResults(){
        mTvInput.setText("");
        mTvInput.setHint("");
        convertFromNumber.setText("");
        convertFromUnit.setText("");
        convertToNumber.setText("");
        convertToUnit.setText("");
        mTvErrorTitle.setText("");
        mTvErrorMessage.setText("");
    }

    /**
     * Presets both the positive and negative output containers.
     * Hides them from sight
     */
    private void prepResult(){
        conversionAnimations.outputInstantFadeOut(resultContainerView);
        conversionAnimations.outputInstantFadeOut(errorContainerView);
    }

    /**
     * Enables or disables the mic button
     * Mainly for when the user is converting and done to
     * avoid clicking on it again while they are at it
     * @param clickable -> can the button be clicked or not.
     */
    private void buttonEnabled(boolean clickable){
        mBtnConvertChild.setEnabled(clickable);
        mBtnConvertChild.setClickable(clickable);
    }

    /**
     * Enables or disables the Main View an all its inner views.
     * Used when the listener for the SpeechRecognizer is activated or
     * deactivated. Prevents from scrolling the ViewPager when transcrabing
     * user input.
     * @param clickable -> can the screen and its views be clicked or not.
     */
    private void screenEnabled(boolean clickable){
        mVoiceConstraintMain.setEnabled(clickable);
        mVoiceConstraintMain.setClickable(clickable);
        viewPager2Child.setEnabled(clickable);
        viewPager2Child.setClickable(clickable);
        mBtnHistoryChild.setEnabled(clickable);
        mBtnHistoryChild.setClickable(clickable);
        mBtnCCChild.setEnabled(clickable);
        mBtnCCChild.setClickable(clickable);
        if (getActivity() != null) {
            if (!clickable)
                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        , WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            else
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    //Permissions
    /**
     * Does an auto-check of the permission.
     * If granted in the past, then checkPermission() won't run
     * Else, checkPermission() will run
     */
    private void checkSelfPermission() {
        if (getActivity() != null)
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                checkPermission();
            }
    }

    /**
     * Fires up the permission approval or denial dialog
     */
    private void checkPermission() {
        if (getActivity() != null) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_REQUEST_CODE);
        }
    }

    /**
     * Android's overriden method for checking if the permission was finally granted
     * If so, an action can take place, in the app's case, we want it to start listening for audio input.
     * Sets the boolean inputBtnPressed to true since i'd simulate the mic button was pressed, since the
     * app is now listening.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RECORD_AUDIO_REQUEST_CODE && grantResults.length > 0 ){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission granted
                startListening();
                inputBtnPressed = true;
            }
        }
    }

    /**
     * Fragment's common onDestroyView() method.
     * Not mandatory to override but necessary in this case
     * to destry the SpeechRecognizer object if the fragment is destroy
     * and avoid any potential memory leak.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSpeechRecognizer.destroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getContext(), "voice frag 5", Toast.LENGTH_SHORT).show();
        voiceResetAll();
    }

    public void voiceResetAll() {
        if (resultsGiven)
            resetPositiveResultsBack();
        if (errorGiven)
            resetNegativeResultsBack();
    }
}