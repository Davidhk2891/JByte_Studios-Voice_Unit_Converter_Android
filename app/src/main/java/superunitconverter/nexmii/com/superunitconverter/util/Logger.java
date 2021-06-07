package superunitconverter.nexmii.com.superunitconverter.util;

import android.util.Log;

/**
 * Singleton Logger class for logs all accross the app instead of individual logs
 */
public final class Logger {

    //Create an object of Logger
    private static final Logger loggerInstance = new Logger();

    //Make the constructor private so it cannot be instantiated
    private Logger(){}

    //Get the only object available
    public static Logger getLoggerInstance(){
        return loggerInstance;
    }

    //Info logger:
    public void infoLog(String prefix, String message){
        Log.i(prefix, message);
    }

    //Error logger:
    public void errorLog(String prefix, String message){
        Log.e(prefix, message);
    }

    //Debugger:
    //mLogger.infoLog("CHECK_STUFF", "check: " + );

}
