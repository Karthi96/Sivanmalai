package com.liteart.apps.sivanmalai;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.liteart.apps.sivanmalai.R;

/*
 * Demo of creating an application to open any URL inside the application and clicking on any link from that URl
should not open Native browser but  that URL should open in the same screen.

- Load WebView with progress bar
 */
public class WebviewBrowser extends Activity {
    /** Called when the activity is first created. */

    WebView web;
    ProgressBar progressBar;
    Button btn_youtubeapp;
    private static final long REPEAT_TIME = 1000 * 60;
    InterstitialAd mInterstitialAd;

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    public void displayInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_browser);
        //MobileAds.initialize(getApplicationContext(), getString(R.string.app_id));
        AdView mAdView = (AdView) findViewById(R.id.adView_webview);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
                mInterstitialAd = new InterstitialAd(WebviewBrowser.this);
                mInterstitialAd.setAdUnitId(getString(R.string.interid));
                AdRequest adRequest1 = new AdRequest.Builder()
                        .build();
                mInterstitialAd.loadAd(adRequest1);
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override

                    public void onAdLoaded() {
                        displayInterstitial();
                    }
                });

        web = (WebView) findViewById(R.id.webview01);
        btn_youtubeapp = (Button) findViewById(R.id.youtubeapp);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        web.setWebViewClient(new myWebClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setSupportZoom(true);
        web.getSettings().setDisplayZoomControls(true);
        final String url_webview=getIntent().getStringExtra("url");
        String type=getIntent().getStringExtra("type");
        try {
            if (url_webview.endsWith(".pdf")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setDataAndType(Uri.parse("http://docs.google.com/viewer?url=" + url_webview), "text/html");

                startActivity(intent);
            } else if (type.equalsIgnoreCase("html")) {
                btn_youtubeapp.setVisibility(View.GONE);
                web.loadUrl(url_webview);
            } else
                web.loadUrl(url_webview);
        }
        catch (Exception e)
        {

        }
        btn_youtubeapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url_webview));
                intent.putExtra("force_fullscreen",true);
                startActivity(intent);
            }
        });
    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
            if(url.endsWith(".pdf"))
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setDataAndType(Uri.parse( "http://docs.google.com/viewer?url=" + url), "text/html");

                startActivity(intent);
            }
            else
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            progressBar.setVisibility(View.GONE);
        }
    }

    // To handle "Back" key press event for WebView to go back to previous screen.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
