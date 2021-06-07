package superunitconverter.nexmii.com.superunitconverter.util;

import android.os.CountDownTimer;

/**
 * Singleton custom Timer class. Used for multiple purposes
 */
public class CustomTimer {

    private static CustomTimer sharedInstance;

    private CustomTimer(){

    }

    public static synchronized CustomTimer getSharedInstance(){
        if (sharedInstance == null)
            sharedInstance = new CustomTimer();
        return  sharedInstance;
    }

    /**
     *
     * @param customTime time to be set for the timer
     * @param cti callback for when timer is done
     */
    public void setTimer(int customTime, CustomTimerInterface cti){
        new CountDownTimer(customTime, 1000) {
            public void onTick(long millisUntilFinished){

            }

            public void onFinish(){
                cti.onTimerFinished();
            }
        }.start();
    }

    /**
     * Callback for timer when time is finished
     */
    public interface CustomTimerInterface{
        void onTimerFinished();
    }

}
