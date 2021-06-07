package superunitconverter.nexmii.com.superunitconverter.views.innerfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import superunitconverter.nexmii.com.superunitconverter.R;
import superunitconverter.nexmii.com.superunitconverter.controllers.ConversionController;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitConstants;
import superunitconverter.nexmii.com.superunitconverter.util.ResultColor;
import superunitconverter.nexmii.com.superunitconverter.util.animations.ConversionAnimations;
import superunitconverter.nexmii.com.superunitconverter.util.modals.SelectorDialogBox;

/**
 * Inner fragment. Child of ConversionBaseFragment.
 * Handles displaying data into the views
 */
public class ManualConversionFrag extends Fragment {

    //Properties:
    private LinearLayout manualConvCategorySection;
    private TextView manualConvCategoryItem;
    private LinearLayout manualConvAmountSection;
    private EditText manualConvAmountItem;
    private TextView manualConvOriginalUnit;
    private TextView manualConvTargetUnit;
    private String to;
    private String category;
    private String originalUnit;
    private String targetUnit;
    private String input, output;

    // Views: Conversion hud
    private ImageButton mBtnConvertChild;

    // - Views: Positive output
    private View resultContainerView;
    private LinearLayout convertToData;
    private TextView convertFromNumber;
    private TextView convertFromUnit;
    private TextView convertToNumber;
    private TextView convertToUnit;
    private TextView asOf;
    private boolean resultsGiven = false;
    private boolean errorGiven = false;

    // - Views: Negative output (Error)
    private View errorContainerView;
    private TextView mTvErrorTitle;
    private TextView mTvErrorMessage;

    //Project classes
    private ConversionController mConversionController;
    private SelectorDialogBox mSelectorDialogBox;
    private ConversionAnimations mConversionAnimations;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_manual_conversion, container, false);

        //Call actions
        initFields(view);
        hideOutputTemplatesOnStart();
        fieldClickManager();
        defaultConversionSet();
        inputClick();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Method for initializing all fields relevant to this class
     */
    private void initFields(View v){
        //manual input hud
        // - Views: Manual input hud
        View manualHudContainer = v.findViewById(R.id.manual_hud_container);
        manualConvCategorySection = manualHudContainer.findViewById(R.id.manual_conv_category_section);
        manualConvCategoryItem = manualHudContainer.findViewById(R.id.manual_conv_category_item);
        manualConvAmountSection = manualHudContainer.findViewById(R.id.manual_conv_amount_section);
        manualConvAmountItem = manualHudContainer.findViewById(R.id.manual_conv_amount_item);
        manualConvOriginalUnit = manualHudContainer.findViewById(R.id.manual_conv_original_unit);
        manualConvTargetUnit = manualHudContainer.findViewById(R.id.manual_conv_target_unit);

        //conversion hud
        if (getActivity() != null)
            mBtnConvertChild = getActivity().findViewById(R.id.btnManualConvert);

        //positive output template
        resultContainerView = v.findViewById(R.id.manual_hud_output_container);
        convertToData = resultContainerView.findViewById(R.id.convert_to_data);
        convertFromNumber = resultContainerView.findViewById(R.id.convert_from_number);
        convertFromUnit = resultContainerView.findViewById(R.id.convert_from_unit);
        convertToNumber = resultContainerView.findViewById(R.id.convert_to_number);
        convertToUnit = resultContainerView.findViewById(R.id.convert_to_unit);
        asOf = resultContainerView.findViewById(R.id.as_of);

        //negative output template
        errorContainerView = v.findViewById(R.id.manual_hud_error_container);
        mTvErrorTitle = errorContainerView.findViewById(R.id.tvErrorTitle);
        mTvErrorMessage = errorContainerView.findViewById(R.id.tvErrorMessage);

        //Project classes
        mConversionController = new ConversionController(getContext(), getActivity());
        mSelectorDialogBox = SelectorDialogBox.getDialogBoxInstance();
        mConversionAnimations = new ConversionAnimations(getContext());

        //Other
        to = getResources().getString(R.string.to);
    }

    //----RUN CONVERSION----//
    private void defaultConversionSet(){
        manualConvCategoryItem.setText(UnitConstants.TEMPERATURE);
        manualConvCategoryItem.setTextColor(ResultColor.getInstance().generalColorSelector
                (getContext(), UnitConstants.TEMPERATURE));
        collectInput();
    }

    private void collectInput(){
        category = manualConvCategoryItem.getText().toString();
        originalUnit = manualConvOriginalUnit.getText().toString();
        targetUnit = manualConvTargetUnit.getText().toString();
        String amount = manualConvAmountItem.getText().toString();
        input = category + " " + amount + " " + originalUnit + " " + to + " " + targetUnit;
    }

    /**
     * Method that listens for the convert button input.
     * Once pressed on this case, it checks if we are standing on the manual conversion fragment.
     * If so, it collects the input string collected from all 4 text fields that represent
     * the category, amount, original unit and target unit.
     * Once it has the String, it sends it to the ConversionAccessRepo and returns the conversion
     * result.
     */
    private void inputClick(){
        mBtnConvertChild.setOnClickListener(view -> {
            collectInput();
            if (input != null && !input.isEmpty()) {
                if (!input.equals(output))
                    runConversionFromDataReceived(input);
            }
        });
    }

    /**
     * This method initializes the process of converting the user input if approved.
     * If it's wrong for any reason, the system will let the user know.
     * If it's correct, the the userInputForwarder will be called from the ConversionController
     * @param userInput user input originated from the concatenation of all strings
     * from the manual conversion
     */
    private void runConversionFromDataReceived(String userInput){
        mConversionController.userInputManager(()-> mConversionController.userInputForwarder(userInput,
                (category, number, convertedNumber, fromUnit, toUnit, date, errorMsg) -> {
                    if (errorMsg != null) {
                        //There is an error with the conversion
                        if (errorGiven)
                            revertNegativeResultsBack();
                        printNegativeResults(errorMsg);
                        showNegativeResults();
                        errorGiven = true;
                    } else {
                        //There are no errors. All clear for conversion
                        if (resultsGiven)
                            revertPositiveResultsBack();

                        printPositiveResults(number, convertedNumber, fromUnit, toUnit, category, date);
                        showPositiveResults();
                        resultsGiven = true;
                    }
        }));
    }

    //----DRAW MENUS----//
    private void fieldClickManager(){
        manualConvCategorySection.setOnClickListener(v ->
                displayDialogBoxMenu(R.string.choose_category));
        manualConvAmountSection.setOnClickListener(v -> {
            //open edit text
        });
        manualConvOriginalUnit.setOnClickListener(v ->
                displayDialogBoxMenu(R.string.original_unit));
        manualConvTargetUnit.setOnClickListener(v ->
                displayDialogBoxMenu(R.string.target_unit));
    }

    private void displayDialogBoxMenu(int stringResource){
        if (getContext() != null) {
            if (stringResource == R.string.choose_category) {
                mSelectorDialogBox.buildMenuDialogBox(getContext()
                        , getActivity(), getContext().getResources().getString(stringResource)
                        , null, clickedItem -> {
                    if (clickedItem != null) {
                        category = clickedItem;
                        manualConvCategoryItem.setText(category);
                        manualConvCategoryItem.setTextColor(ResultColor.getInstance()
                                .generalColorSelector(getContext(), category));
                        mSelectorDialogBox.displayedSubMenuFromCorrectCategory(category,
                                this::displayDefaultUnits);
                        mSelectorDialogBox.getDialog().hide();
                    }
                });
            } else {
                mSelectorDialogBox.buildMenuDialogBox(getContext()
                        , getActivity(), getContext().getResources().getString(stringResource)
                        , manualConvCategoryItem.getText().toString(), clickedItem -> {
                            if (stringResource == R.string.original_unit){
                                originalUnit = clickedItem;
                                setCheckedUnit(manualConvOriginalUnit, manualConvTargetUnit, originalUnit);
                            } else {
                                targetUnit = clickedItem;
                                setCheckedUnit(manualConvTargetUnit, manualConvOriginalUnit, targetUnit);
                            }
                        });
            }
        }
    }

    private void displayDefaultUnits(List<String> menu){
        manualConvOriginalUnit.setText(menu.get(0));
        manualConvTargetUnit.setText(menu.get(1));
    }

    private void setCheckedUnit(TextView currentTv, TextView checkedTv, String clickedItem){
        if (checkedTv.getText().toString().equals(clickedItem)) {
            Toast.makeText(getContext(), getResources().getString(R.string.cannot_convert_to_Same)
                    , Toast.LENGTH_SHORT).show();
        } else {
            currentTv.setText(clickedItem);
            mSelectorDialogBox.getDialog().hide();
        }
    }

    //----OUTPUTS----//
    private void hideOutputTemplatesOnStart(){
        resultContainerView.setAlpha(0f);
        errorContainerView.setAlpha(0f);
    }

    //positive
    private void printPositiveResults(String number, String convertedNumber, String fromUnit, String toUnit, String category, String date){
        convertFromNumber.setText(number);
        convertFromUnit.setText(fromUnit);
        convertToNumber.setText(convertedNumber);
        convertToUnit.setText(toUnit);
        output = category + " " + manualConvAmountItem.getText().toString() + " " + originalUnit + " " + to + " " + targetUnit;
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

    private void showPositiveResults(){
        ResultColor.getInstance().convertToBgColor(getContext(), category, convertToData);
        mConversionAnimations.animateResult(resultContainerView);
    }

    private void revertPositiveResultsBack(){
        mConversionAnimations.animateRevertBackResult(resultContainerView);
        resetResults();
        output = "";
        resultsGiven = false;
    }

    private void resetResults(){
        convertFromNumber.setText("");
        convertFromUnit.setText("");
        convertToNumber.setText("");
        convertToUnit.setText("");
        mTvErrorTitle.setText("");
        mTvErrorMessage.setText("");
    }

    //negative
    private void printNegativeResults(String message){
        mTvErrorTitle.setText(getResources().getString(R.string.error));
        mTvErrorMessage.setText(message);
    }

    private void showNegativeResults(){
        mConversionAnimations.animateError(errorContainerView);
    }

    private void revertNegativeResultsBack(){
        mConversionAnimations.animateRevertBackResult(errorContainerView);
        resetResults();
        errorGiven = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getContext(), "manual frag 3", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getContext(), "manual frag 4", Toast.LENGTH_SHORT).show();
        manualResetAll();
    }

    public void manualResetAll() {
        if (resultsGiven)
            revertPositiveResultsBack();
        if (errorGiven)
            revertNegativeResultsBack();
    }
}