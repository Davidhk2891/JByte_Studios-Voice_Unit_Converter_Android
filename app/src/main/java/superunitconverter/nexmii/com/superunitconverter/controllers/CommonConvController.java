package superunitconverter.nexmii.com.superunitconverter.controllers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import superunitconverter.nexmii.com.superunitconverter.models.ConversionAccessRepo;
import superunitconverter.nexmii.com.superunitconverter.models.commonconv.CommonConv;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitConstants;

/**
 * The controller class for the Common Conversion Fragment. It is responsible for setting up the conversions
 * that are to be set up in the fragment and applying their proper conversions by using the ConversionAccessRepo
 * and updating the Recycler view from the fragment using a LivaData object.
 */
public class CommonConvController extends AndroidViewModel {

    private final ConversionAccessRepo mConversionAccessRepo;
    private final List<ItemToConvert> commonConvListNoResult;
    private final List<CommonConv> commonConvListResult;
    private final MutableLiveData<List<CommonConv>> mMutableLiveCCList;

    //constructor
    public CommonConvController(@NonNull Application application) {
        super(application);
        mConversionAccessRepo = new ConversionAccessRepo(application);
        commonConvListNoResult = new ArrayList<>();
        commonConvListResult = new ArrayList<>();
        mMutableLiveCCList = new MutableLiveData<>();
        runCommonConversions();
    }

    /**
     * Runs the userInputHandler method from the ConversionAccessRepo in order to convert the preset conversions
     * using the addCommonConversions() method
     */
    private void runCommonConversions(){
        addCommonConversions();
        for (ItemToConvert item : commonConvListNoResult){
            String fixedInput = item.getFixedNum() + " " + item.getOriginalUnit() + " to " + item.getTargetUnit();
            mConversionAccessRepo.userInputHandler(fixedInput, new ConversionAccessRepo.InputHandlerInterface() {
                @Override
                public void onInputHandledSuccessfully(String category, String number, String convertedNumber, String fromUnit, String toUnit, String date) {
                    applyDisplayOperations(category, number, convertedNumber, fromUnit, toUnit, date);
                }

                @Override
                public void onInputHandledFailed(String error) {
                    //There should be no exceptions here for now.
                }
            });
        }
        mMutableLiveCCList.postValue(commonConvListResult);
    }

    /**
     * Adds CommonConv POJOs to CommonConv data structure
     */
    private void applyDisplayOperations(String category, String number, String convertedNumber, String fromUnit, String toUnit, String date){
        commonConvListResult.add(new CommonConv(category, number, fromUnit, convertedNumber, toUnit, date));
    }

    /**
     * Adds the preset conversions (later to be converted) to an ArrayList of type ItemToConvert (an inner class in this class)
     */
    private void addCommonConversions(){
        commonConvListNoResult.add(new ItemToConvert(UnitConstants.FIXED_NUMBER, UnitConstants.CUP, UnitConstants.MILLILITER));
        commonConvListNoResult.add(new ItemToConvert(UnitConstants.FIXED_NUMBER, UnitConstants.MILE, UnitConstants.KILOMETER));
        commonConvListNoResult.add(new ItemToConvert(UnitConstants.FIXED_NUMBER, UnitConstants.GRAM, UnitConstants.OUNCE));
        commonConvListNoResult.add(new ItemToConvert(UnitConstants.FIXED_NUMBER, UnitConstants.CELSIUS, UnitConstants.FAHRENHEIT));
        commonConvListNoResult.add(new ItemToConvert(UnitConstants.FIXED_NUMBER, UnitConstants.GALLON, UnitConstants.LITER));
        commonConvListNoResult.add(new ItemToConvert(UnitConstants.FIXED_NUMBER, UnitConstants.METER, UnitConstants.FEET));
    }

    /**
     * Getter for the CommonConversions MutableLiveData.
     * @return -> returns the list in the form of LiveData
     */
    public MutableLiveData<List<CommonConv>> getAllCommonConversions(){
        return mMutableLiveCCList;
    }

    /**
     * Inner class for creating new Common Conversion items to be added to the Common conversions list without
     * result, which then are processed through the Conversion Access Repo.
     */
    private static class ItemToConvert{
        private final String fixedNum;
        private final String originalUnit;
        private final String targetUnit;

        public ItemToConvert(String fixedNum, String originalUnit, String targetUnit) {
            this.fixedNum = fixedNum;
            this.originalUnit = originalUnit;
            this.targetUnit = targetUnit;
        }

        public String getFixedNum() {
            return fixedNum;
        }

        public String getOriginalUnit() {
            return originalUnit;
        }

        public String getTargetUnit() {
            return targetUnit;
        }
    }
}
