package com.liteart.apps.sivanmalai;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.liteart.apps.sivanmalai.R;

import java.util.ArrayList;

public class EssayActivity extends AppCompatActivity {

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;
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
        setContentView(R.layout.activity_essay);
        mainListView = (ListView) findViewById( R.id.mainListView );
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1439926476174790~9143120480");
        AdView mAdView = (AdView) findViewById(R.id.adView_essay1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MobileAds.initialize(getApplicationContext(), "ca-app-pub-1439926476174790~9143120480");
                mInterstitialAd = new InterstitialAd(EssayActivity.this);
                mInterstitialAd.setAdUnitId(getString(R.string.interid));
                AdRequest adRequest = new AdRequest.Builder()
                        .build();
                mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override

                    public void onAdLoaded() {
                        displayInterstitial();
                    }
                });

            }
        } , 9000);
        final Intent intent = getIntent();
        final String [] titles = intent.getStringArrayExtra("string-array");
        final String name=intent.getStringExtra("name");
        if(name.equalsIgnoreCase("essay") ) {
            setTitle(getString(R.string.essay));
        }
        else if(name.equalsIgnoreCase("story")) {
            setTitle(getString(R.string.stories));
        }
        else if(name.equalsIgnoreCase("kanthapuranam") ) {
            setTitle(getString(R.string.kantha_puranam));
        }
        else if(name.equalsIgnoreCase("articles"))
        {
            setTitle(getString(R.string.articles));
        }

        ArrayList<String> titlesList = new ArrayList<String>();
        int j=1;
        for(String i:titles)
        {
            titlesList.add(String.valueOf(j++)+" . "+i);
        }
       // titlesList.addAll( Arrays.asList(titles) );

        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, titlesList);

        mainListView.setAdapter( listAdapter );
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(name.equalsIgnoreCase("essay") ) {
                    setTitle(getString(R.string.essay));
                    Intent ii=new Intent(getApplicationContext(), EssayWebviewActivity.class);
                    ii.putExtra("position", position);
                    ii.putExtra("string-array", titles);
                    startActivity(ii);
                }
                else if(name.equalsIgnoreCase("story")) {
                    setTitle(getString(R.string.stories));
                    Intent ii=new Intent(getApplicationContext(), EssayWebviewActivity.class);
                    ii.putExtra("position", position);
                    ii.putExtra("string-array", titles);
                    startActivity(ii);
                }
                else if(name.equalsIgnoreCase("kanthapuranam") ) {
                    setTitle(getString(R.string.kantha_puranam));
                    Intent ii=new Intent(getApplicationContext(), WebviewBrowser.class);
                    ii.putExtra("url", StringArrays.kanthapuranam[position]);
                    ii.putExtra("type","html");
                    startActivity(ii);
                }
                else if(name.equalsIgnoreCase("articles"))
                {
                    setTitle(getString(R.string.articles));
                    Intent ii=new Intent(getApplicationContext(), WebviewBrowser.class);
                    ii.putExtra("url", StringArrays.articles[position]);
                    ii.putExtra("type","html");
                    startActivity(ii);
                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
