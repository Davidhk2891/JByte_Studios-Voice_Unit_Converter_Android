package superunitconverter.nexmii.com.superunitconverter;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

public class App extends Application {

    /**
     * Called when the application is starting, before any activity, service, or receiver objects
     * (excluding content provider) have been created. Implementations should be as quick as possible
     * (for example using lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity, service, or receiver in a
     * process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();
        adsInitialization();
    }

    /**
     * This method is for use in emulated process environments. It will never be called on a production
     * Android device, where processes are removed by simply killing them; no user code (including this callback)
     * is executed when doing so.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * Method for initialising ads in the app
     */
    private void adsInitialization(){
        MobileAds.initialize(this, initializationStatus -> {});
    }
}
