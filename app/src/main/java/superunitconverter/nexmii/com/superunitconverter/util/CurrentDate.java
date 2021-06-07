package superunitconverter.nexmii.com.superunitconverter.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Singleton class. Has a single method which returns the current date in String.
 */
public class CurrentDate {

    private static CurrentDate sharedCDInstance;

    private CurrentDate(){

    }

    public static synchronized CurrentDate getSharedCDInstance(){
        if (sharedCDInstance == null){
            sharedCDInstance = new CurrentDate();
        }
        return sharedCDInstance;
    }

    /**
     * @return returns the phone's default current date
     */
    public String currentDate(){
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

}
