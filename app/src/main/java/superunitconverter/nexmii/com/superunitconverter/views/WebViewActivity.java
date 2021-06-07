package superunitconverter.nexmii.com.superunitconverter.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;

import superunitconverter.nexmii.com.superunitconverter.R;
import superunitconverter.nexmii.com.superunitconverter.controllers.WebViewController;
import superunitconverter.nexmii.com.superunitconverter.util.AdManager;
import superunitconverter.nexmii.com.superunitconverter.util.AppConstants;

/**
 * This activity's sole purpose is to fire up a webview to wither open the help article for what
 * units are supported in the app or the about article that describes that the app does.
 */
public class WebViewActivity extends AppCompatActivity {

    //properties
    private ActionBar actionBar;
    private static final String TAG = "WebViewActivity";
    private String receivedHelp;
    private WebView webView;
    private ProgressBar progressBarRound;
    private AdView mAdViewWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        initializeViews();
        initializeSupportActionBar();
        getData();
        initializeCustomWebView();
        initializeAdView();

    }

    /**
     * Method for initializing all relevant views
     */
    private void initializeViews(){
        webView = findViewById(R.id.webview);
        progressBarRound = findViewById(R.id.progressBarRound);
        mAdViewWebView = findViewById(R.id.adView_web_view);
    }

    /**
     * Initializes App's support action bar and its back button
     */
    private void initializeSupportActionBar(){
        actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Gets the data from MainActvitiy which is the name for the page to get from JByte Studios
     */
    private void getData(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            receivedHelp = bundle.getString(AppConstants.WEBVIEW_BUNDLE_KEY);
            if (receivedHelp != null && actionBar != null) {
                Log.i(TAG, receivedHelp);
                actionBar.setTitle(receivedHelp);
            }
        } else {
            finish();
            String msg = getResources().getString(R.string.something_went_wrong);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Initializes the WebView
     */
    private void initializeCustomWebView(){
        WebViewController webViewController = new WebViewController(webView, progressBarRound);
        if (receivedHelp.equals(getResources().getString(R.string.menu_units)))
            webViewController.initializeWebView(AppConstants.WEBVIEW_URL_UNITS);
        else if (receivedHelp.equals(getResources().getString(R.string.menu_about)))
            webViewController.initializeWebView(AppConstants.WEBVIEW_URL_ABOUT);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeAdView(){
        AdManager adManager = new AdManager();
        adManager.initializeAppEnvironment(mAdViewWebView);
    }
}