package superunitconverter.nexmii.com.superunitconverter.controllers;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * Controller class for the WebViewActivity. It will handle all the operations for initializing
 * the webview with the speficied URL. This should be used by any activity or fragment that wants
 * to forward the user to the web.
 */
public class WebViewController {

    private final WebView webView;
    private final ProgressBar progressBar;

    //constructor
    public WebViewController(WebView webView, ProgressBar progressBar){
        this.webView = webView;
        this.progressBar = progressBar;
    }

    /**
     * Fires up the webview and loads its url passed on as a parameter
     * @param url -> url to be used in the webview
     */
    public void initializeWebView(String url){
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        progressBar.animate();
    }

    /**
     * WebView client inner class for handling WebView listeners for typical Web page events such as
     * on page loaded etc
     */
    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //Hide loading animation
            progressBar.setVisibility(View.GONE);
        }
    }
}
