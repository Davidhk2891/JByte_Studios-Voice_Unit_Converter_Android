package superunitconverter.nexmii.com.superunitconverter.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Singleton class. Its sole purpose is to check if the device has a current and solid internet
 * connection.
 */
public final class InternetChecker {

    private static final String LOG_TAG = "InternetChecker.java";
    private static final String INTERNET_CHECK_URL = "http://clients3.google.com/generate_204";
    private static InternetChecker instance;

    private InternetChecker(){}

    public synchronized static InternetChecker getInstance(){
        if (instance == null)
            instance = new InternetChecker();

            return instance;
    }

    public void isThereInternet(Context context, InternetCheckerInterface ici) {
        new Thread(){
            public void run(){
                if (isNetworkAvailable(context)) {
                    try {
                        HttpURLConnection urlc = (HttpURLConnection)
                                (new URL(INTERNET_CHECK_URL)
                                        .openConnection());
                        urlc.setRequestProperty("User-Agent", "Test");
                        urlc.setRequestProperty("Connection", "close");
                        urlc.setConnectTimeout(1500);
                        urlc.connect();
                        if(urlc.getResponseCode() == 204 && urlc.getContentLength() == 0) {
                            Log.i(LOG_TAG, "there is internet");
                            ici.onInternetStatusOK();
                        } else {
                            Log.i(LOG_TAG, "there is no internet");
                            ici.onInternetStatusNOTOK();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        ici.onInternetStatusNOTOK();
                    }
                } else {
                    Log.i(LOG_TAG, "there is no internet");
                    ici.onInternetStatusNOTOK();
                }
            }
        }.start();
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public interface InternetCheckerInterface{
        void onInternetStatusOK();
        void onInternetStatusNOTOK();
    }

}
