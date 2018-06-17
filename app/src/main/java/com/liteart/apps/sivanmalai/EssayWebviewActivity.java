package com.liteart.apps.sivanmalai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.liteart.apps.sivanmalai.R;


public class EssayWebviewActivity extends Activity {
Button btn_pre,btn_next,btn_home;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay_webview);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1439926476174790~9143120480");
        AdView mAdView = (AdView) findViewById(R.id.adview_essay_html);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        btn_pre=(Button)findViewById(R.id.previous);
        btn_next=(Button)findViewById(R.id.next);
        btn_home=(Button)findViewById(R.id.about_bharathidasan);
        final Intent intent = getIntent();
        final String [] stringArray = intent.getStringArrayExtra("string-array");
        final int position = intent.getIntExtra("position", 0);
        final String url = intent.getStringExtra("url");
        if(stringArray!=null) {
            if (position == 0)
                btn_pre.setVisibility(View.GONE);
            if (position == stringArray.length - 1)
                btn_next.setVisibility(View.GONE);
        }
        String uri=null;
        try {
            uri = stringArray[position] + ".html";

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        webView = (WebView)findViewById(R.id.webView1);

        WebSettings webSetting = webView.getSettings();
        //webSetting.setBuiltInZoomControls(true);
        webSetting.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        if(uri!=null)
            webView.loadUrl("file:///android_asset/"+uri);
        else {
            btn_pre.setVisibility(View.GONE);
            btn_next.setVisibility(View.GONE);
            webView.loadUrl("file:///android_asset/" + url + ".html");
        }
        webSetting.setDefaultFontSize(15);
        btn_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii=new Intent(getApplicationContext(), EssayWebviewActivity.class);
                ii.putExtra("position", position-1);

                ii.putExtra("string-array", stringArray);
                startActivity(ii);
                finish();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii=new Intent(getApplicationContext(), EssayWebviewActivity.class);
                ii.putExtra("position", position+1);

                ii.putExtra("string-array", stringArray);
                startActivity(ii);
                finish();
            }
        });
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }

    private class WebViewClient extends android.webkit.WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}