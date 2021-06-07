package superunitconverter.nexmii.com.superunitconverter.controllers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.Conversion;
import superunitconverter.nexmii.com.superunitconverter.models.history.db.ConversionRepo;

/**
 * Controller class for HistoryFragment.java.
 */
public class HistoryController extends AndroidViewModel {

    private final ConversionRepo mConversionRepo;
    private final LiveData<List<Conversion>> mConversionLiveData;

    //Ctor
    public HistoryController(@NonNull Application application){
        super(application);
        mConversionRepo = new ConversionRepo(application);
        mConversionLiveData = mConversionRepo.getAllConversions();
    }

    /**
     * delete method for deleting an individual conversion
     */
    public void delete(Conversion conversion){
        mConversionRepo.delete(conversion);
    }

    //Won't be used yet
    public void deleteAll(){
        mConversionRepo.deleteAllConversions();
    }

    /**
     * Method for getting all saved conversions. The data comes from ConversionRepo.class
     * @return returns a list of all past saved conversions
     */
    public LiveData<List<Conversion>> getAllConversions(){
        return mConversionLiveData;
    }

}
