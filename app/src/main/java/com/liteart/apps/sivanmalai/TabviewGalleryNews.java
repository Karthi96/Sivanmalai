package com.liteart.apps.sivanmalai;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.liteart.apps.sivanmalai.R;


public class TabviewGalleryNews extends AppCompatActivity
{
    TabLayout tabLayout;
    ViewPager viewPager;
    int id;
    PagerAdapterStroriesTab adapter;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText("கதைகள்"));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.essay)));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MobileAds.initialize(getApplicationContext(), "ca-app-pub-1439926476174790~9143120480");
                mInterstitialAd = new InterstitialAd(TabviewGalleryNews.this);
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
        //tabLayout.scrollTo();
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.pager);

       adapter = new PagerAdapterStroriesTab(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
