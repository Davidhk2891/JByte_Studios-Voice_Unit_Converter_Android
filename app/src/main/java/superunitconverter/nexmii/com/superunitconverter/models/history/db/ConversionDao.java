package superunitconverter.nexmii.com.superunitconverter.models.history.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import superunitconverter.nexmii.com.superunitconverter.models.conversion.objects.Conversion;

/**
 * Data Access Object interface for the Conversion object
 */
@Dao
public interface ConversionDao {

    /**
     * save() method for saving a conversion after this one was printed
     */
    @Insert
    void save(Conversion c);

    /**
     * Method for pulling all saved conversions from the local SQLite database
     * @return returns the list with all past saved conversions
     */
    @Query("SELECT * FROM Conversion ORDER BY id DESC")
    LiveData<List<Conversion>> getAllConversions();

    /**
     * Delete a single conversion
     * @param c Conversion object
     */
    @Delete
    void delete(Conversion c);

    /**
     * Delete all conversions
     */
    @Query("DELETE FROM Conversion")
    void deleteAllConversions();

}
