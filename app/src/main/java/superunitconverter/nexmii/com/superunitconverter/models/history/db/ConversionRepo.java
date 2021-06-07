package superunitconverter.nexmii.com.superunitconverter.models.history.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.Conversion;

/**
 * ConversionRepo class. A class used to access the Conversion database and its operations
 * All operations must run through secondary threads (not in the main thread)
 */
public class ConversionRepo {

    //Properties:
    private final ConversionDao mConversionDao;
    private final LiveData<List<Conversion>> allConversions;

    public ConversionRepo(Application application){
        ConversionDatabase conversionDatabase = ConversionDatabase.getInstance(application);
        mConversionDao = conversionDatabase.getConversionDao();
        allConversions = mConversionDao.getAllConversions();
    }

    public void save(Conversion conversion){
        new Thread(()-> mConversionDao.save(conversion)).start();
    }

    public void delete(Conversion conversion){
        new Thread(()-> mConversionDao.delete(conversion)).start();
    }

    public void deleteAllConversions(){
        new Thread(mConversionDao::deleteAllConversions).start();
    }

    public LiveData<List<Conversion>> getAllConversions(){
        return allConversions;
    }
}
