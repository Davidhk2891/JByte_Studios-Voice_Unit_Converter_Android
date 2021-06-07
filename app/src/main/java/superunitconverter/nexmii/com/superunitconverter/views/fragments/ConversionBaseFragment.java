package superunitconverter.nexmii.com.superunitconverter.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

import superunitconverter.nexmii.com.superunitconverter.R;
import superunitconverter.nexmii.com.superunitconverter.util.AppConstants;
import superunitconverter.nexmii.com.superunitconverter.util.animations.AppAnimations;
import superunitconverter.nexmii.com.superunitconverter.views.WebViewActivity;
import superunitconverter.nexmii.com.superunitconverter.views.innerfragments.ManualConversionFrag;
import superunitconverter.nexmii.com.superunitconverter.views.innerfragments.VoiceConversionFrag;

/**
 * Conversion Base Fragment. Base class.
 * Handles the ViewPager2 logic for alternating between its two
 * child fragments: VoiceConversionFrag and ManualConversionFrag
 * It also sets up the title animation for the words 'VOICE' and 'OLDIE'
 * and the HUD
 */
public class ConversionBaseFragment extends Fragment {

    //Properties:
    private static final String TAG = "ConversionBaseFragment";
    private TextView mTvConversionTypeTitle;
    private ViewPager2 mainNavViewPager;
    private ViewPager2 viewPager2;
    private ImageButton mBtnVoiceConvert;
    private ImageButton mBtnManualConvert;
    private ImageButton mBtnHistory;
    private ImageButton mBtnCC;
    private ImageView mIvMore;

    private AppAnimations mAppAnimations;

    private boolean readyToAnimate = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        //Instantiate objects
        View view = inflater.inflate(R.layout.fragment_base_conversion, container, false);

        viewsInit(view);
        viewPagerInitialization();
        buttonsNavigation();
        initializeOptionsMenu();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Method for initializing all the relevant views for the Fragment
     */
    private void viewsInit(View v){
        mTvConversionTypeTitle = v.findViewById(R.id.tvConversionTypeTitle);
        viewPager2 = v.findViewById(R.id.mainVNavViewPager);
        mAppAnimations = new AppAnimations(getActivity());
        if (getActivity() != null) {
            mTvConversionTypeTitle.setText(getActivity().getString(R.string.voice));
        }
        View hudContainer = v.findViewById(R.id.hud_container);
        mBtnVoiceConvert = v.findViewById(R.id.btnVoiceConvert);
        mBtnManualConvert = v.findViewById(R.id.btnManualConvert);
        mBtnHistory = hudContainer.findViewById(R.id.btnHistory);
        mBtnCC = hudContainer.findViewById(R.id.btnTables);
        mainNavViewPager = getActivity().findViewById(R.id.mainHNavViewPager);
        mIvMore = v.findViewById(R.id.ivMore);
    }

    /**
     * Method for setting up and initializing all menu options as well as the Popup menu
     */
    private void initializeOptionsMenu(){
        if (getContext() != null) {
            mIvMore.setOnClickListener(v -> {
                PopupMenu popup = new PopupMenu(getContext(), mIvMore);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(item -> {
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    Log.i(TAG, item.getTitle().toString());
                    intent.putExtra(AppConstants.WEBVIEW_BUNDLE_KEY, item.getTitle().toString());
                    startActivity(intent);
                    return false;
                });
                popup.show();
            });
        }
    }

    /**
     * Method for controlling the HUD side button's actions.
     */
    private void buttonsNavigation(){
        mBtnHistory.setOnClickListener(v-> mainNavViewPager.setCurrentItem(2));
        mBtnCC.setOnClickListener(v-> mainNavViewPager.setCurrentItem(0));
    }

    /**
     * Method for initializing the vertical ViewPager2 for moving across both VoiceConversionFrag
     * and ManualConversionFrag.
     */
    private void viewPagerInitialization(){
        // Instantiate a ViewPager2 and a PagerAdapter.
        FragmentStateAdapter pagerAdapter = new ConversionBaseFragment.ScreenVSlidePagerAdapter(getActivity());
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setOffscreenPageLimit(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (readyToAnimate) {
                    if (getActivity() != null) {
                        if (position == 0) {
                            handleViewsAnim(getActivity().getString(R.string.voice)
                                    , mBtnManualConvert, mBtnVoiceConvert);
                        } else {
                            handleViewsAnim(getActivity().getString(R.string.oldie)
                                    , mBtnVoiceConvert, mBtnManualConvert);
                        }
                    }
                }
                readyToAnimate = true;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        viewPager2.setAdapter(pagerAdapter);
    }

    private void handleViewsAnim(String newText, ImageButton oldIb, ImageButton newIb){
        mAppAnimations.fadeOut(mTvConversionTypeTitle, () -> {
            mTvConversionTypeTitle.setText(newText);
            mAppAnimations.fadeIn(mTvConversionTypeTitle);
        });
        newIb.setVisibility(View.VISIBLE);
        newIb.setClickable(true);
        oldIb.setVisibility(View.INVISIBLE);
        oldIb.setClickable(false);
    }

    /**
     * A simple pager adapter that calls on three fragments.
     */
    private static class ScreenVSlidePagerAdapter extends FragmentStateAdapter {

        ArrayList<Fragment> mFragments;

        public ScreenVSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
            mFragments = new ArrayList<>();
            mFragments.add(new VoiceConversionFrag());
            mFragments.add(new ManualConversionFrag());
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

    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getContext(), "base frag", Toast.LENGTH_SHORT).show();
    }
}