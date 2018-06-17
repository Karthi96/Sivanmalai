package com.liteart.apps.sivanmalai;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liteart.apps.sivanmalai.R;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class NewsListActivity extends AppCompatActivity {

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
    ProgressDialog progressDialog;
    ArrayList<String> news_array, sorted_news_array, spilted_array;
    RecyclerView recyclerView;
    ArrayList<CommonModel> list;
    private RecyclerviewAdaptor adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        setTitle(getString(R.string.news));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                 mInterstitialAd = new InterstitialAd(NewsListActivity.this);
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
        } , 5000);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        AdView mAdView = (AdView) findViewById(R.id.adViewNewsListPage);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
try {
    list = new ArrayList<CommonModel>();
    SharedPreferences pref = getApplicationContext().getSharedPreferences("dataPref", MODE_PRIVATE);
    Gson gson = new Gson();
    String json = pref.getString("listnews", null);
    Type type = new TypeToken<ArrayList<CommonModel>>() {
    }.getType();
    list = gson.fromJson(json, type);
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
    llm.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(llm);
    adapter = new RecyclerviewAdaptor(list, getApplicationContext());
    recyclerView.setAdapter(adapter);
}
catch (Exception e)
{
    Log.e("Exception News",e.toString());
}
         //  Snackbar.make(recyclerView,Html.fromHtml("<font color=\"#ffffff\">Please Connect to the Internet</font>"), Snackbar.LENGTH_INDEFINITE).show();



    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}