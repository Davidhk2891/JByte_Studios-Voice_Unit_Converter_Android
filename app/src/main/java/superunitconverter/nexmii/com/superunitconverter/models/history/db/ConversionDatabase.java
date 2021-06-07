package superunitconverter.nexmii.com.superunitconverter.models.history.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.Conversion;

/**
 * Database object. Builds the databases and its conversion table (entity)
 */
@Database(version = 1, entities = {Conversion.class}, exportSchema = false)
public abstract class ConversionDatabase extends RoomDatabase {

    private static ConversionDatabase instance;

    //Get DAO object as an abstract method
    public abstract ConversionDao getConversionDao();

    //ConversionDatabase ctor has to be public if this is to be an abstract class. Looks like that.

    public static synchronized ConversionDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ConversionDatabase.class, "conversion-db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
