package superunitconverter.nexmii.com.superunitconverter.views;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.ads.AdView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import superunitconverter.nexmii.com.superunitconverter.R;
import superunitconverter.nexmii.com.superunitconverter.util.AdManager;
import superunitconverter.nexmii.com.superunitconverter.util.InternetChecker;
import superunitconverter.nexmii.com.superunitconverter.views.fragments.ConversionBaseFragment;
import superunitconverter.nexmii.com.superunitconverter.views.fragments.HistoryFragment;
import superunitconverter.nexmii.com.superunitconverter.views.fragments.CommonConvFragment;

/**
 * App's main and only class. Handles the mainly the ViewPager2 navigation for all its three fragments.
 */
public class MainActivity extends FragmentActivity{

    private static final String TAG = "MainActivity";
    private ConstraintLayout mMainLayout;
    private ViewPager2 viewPager;
    private AdView mAdViewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeStatusBarColor();
        viewsInitialization();
        checkInternetConnection();
        initializeAdView();
    }

    /**
     * Method for changing the phone's status bar color. Should be placed somewhere else
     */
    private void changeStatusBarColor(){
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black, null));
    }

    /**
     * Method for initializing App's main navigation view pager and performing all its inner
     * operations
     */
    private void viewsInitialization(){
        // Instantiate a ViewPager2 and a PagerAdapter.
        mMainLayout = findViewById(R.id.mainLayout);
        viewPager = findViewById(R.id.mainHNavViewPager);
        mAdViewMain = findViewById(R.id.adView_main);
        FragmentStateAdapter pagerAdapter = new ScreenHSlidePagerAdapter(this);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1, false);
        viewPager.setOffscreenPageLimit(2);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    Log.i(TAG, "Common Conversions");
                } else if (position == 1) {
                    Log.i(TAG, "Conversion");
                } else {
                    Log.i(TAG, "History");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 1) {
            // If the user is currently looking at the main conversion activity, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else if (viewPager.getCurrentItem() == 2) {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        } else if (viewPager.getCurrentItem() == 0) {
            viewPager.setCurrentItem(1);
        }
    }

    /**
     * Method fired up in MainActivity.java for knowing if the device currently has a good internet
     * connection.
     */
    private void checkInternetConnection(){
        Log.i(TAG, "Ran");
        InternetChecker.getInstance().isThereInternet(MainActivity.this,
                new InternetChecker.InternetCheckerInterface() {
            @Override
            public void onInternetStatusOK() {
                //Internet connection detected
                Log.i(TAG, "Internet connection detected");
            }

            @Override
            public void onInternetStatusNOTOK() {
                //No internet connection detected
                Log.i(TAG, "No internet connection detected");
                Snackbar snackbar = Snackbar
                        .make(mMainLayout, getResources().getString(R.string.no_internet)
                                , Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(getResources().getString(R.string.dismiss),
                        v -> snackbar.dismiss());
                snackbar.show();
            }
        });
    }

    /**
     * A simple pager adapter that calls on three fragments.
     */
    private static class ScreenHSlidePagerAdapter extends FragmentStateAdapter {

        ArrayList<Fragment> mFragments;

        public ScreenHSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
            mFragments = new ArrayList<>();
            mFragments.add(new CommonConvFragment());
            mFragments.add(new ConversionBaseFragment());
            mFragments.add(new HistoryFragment());
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getItemCount() {
            return mFragments.size();
        }
    }

    private void initializeAdView(){
        AdManager adManager = new AdManager();
        adManager.initializeAppEnvironment(mAdViewMain);
    }
}