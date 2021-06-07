package superunitconverter.nexmii.com.superunitconverter.util;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Regular class for administrating the app's ads.
 * NOTE: The only relevant method that is not in this class is the intialize method for the ads.
 * It is located in App.java.
 */
public class AdManager {

    /**
     * Method for initializing an individual Banner Ad.
     * @param adView the relevant AdView that is going to be displayed on the relevant
     *               Activity/Fragment.
     */
    public void initializeAppEnvironment(AdView adView){
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

}
