package com.liteart.apps.sivanmalai;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liteart.apps.sivanmalai.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utharavu_porul extends AppCompatActivity {
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
    RecyclerView recyclerView;
    ArrayList<CommonModelPetti> list;
    ImageView pettiimg;
    private utharavuRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pettimain);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mInterstitialAd = new InterstitialAd(Utharavu_porul.this);
                mInterstitialAd.setAdUnitId(getString(R.string.interid1));
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
        } , 5000);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1439926476174790~5913211087");
        AdView mAdView = (AdView) findViewById(R.id.adView_pettipage);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pettiimg=(ImageView)findViewById(R.id.pettimgage) ;
        Glide.with(this).load("https://liteartapps.com/android_production/android_murugan/sivanmalai/image.jpg")
                .placeholder(R.drawable.appicon)
                .error(R.drawable.appicon)
                .skipMemoryCache(true)
                .into(pettiimg);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        try {
            list = new ArrayList<CommonModelPetti>();
            SharedPreferences pref = getApplicationContext().getSharedPreferences("dataPref", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = pref.getString("listpetti", null);
            Type type = new TypeToken<ArrayList<CommonModelPetti>>() {
            }.getType();
            list = gson.fromJson(json, type);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            adapter = new utharavuRecyclerAdapter(list, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
        catch (Exception e)
        {
            Log.e("Exception News",e.toString());
        }


    }
}
