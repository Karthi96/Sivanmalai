package com.liteart.apps.sivanmalai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.example.jean.jcplayer.JcAudio;
import com.example.jean.jcplayer.JcPlayerService;
import com.example.jean.jcplayer.JcPlayerView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liteart.apps.sivanmalai.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RadioFMActivity extends AppCompatActivity implements JcPlayerService.JcPlayerServiceListener {
    String button_name;
    ArrayList<String> urlList;
    JcPlayerView jcplayerView;
    ArrayList<JcAudio> jcAudios;
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
    boolean plays=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_fm);
        setTitle(R.string.fm);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        jcplayerView = (JcPlayerView) findViewById(R.id.jcplayer);
        AdView mAdView = (AdView) findViewById(R.id.raiofmActivity);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
try {
    recyclerView.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
    recyclerView.setLayoutManager(layoutManager);
    SharedPreferences pref = getApplicationContext().getSharedPreferences("dataPref", MODE_PRIVATE);
    Gson gson = new Gson();
    String json = pref.getString("titleListRadio", null);
    String jsonUrl = pref.getString("urlListRadio", null);
    Type type = new TypeToken<ArrayList<String>>() {
    }.getType();
    ArrayList<String> titleList = gson.fromJson(json, type);
    urlList = gson.fromJson(jsonUrl, type);

    jcAudios = new ArrayList<>();
    for (int i = 0; i < urlList.size(); i++) {
        jcAudios.add(i, JcAudio.createFromURL("Now Playing : " + titleList.get(i), urlList.get(i)));
        //  jcplayerView.initWithTitlePlaylist(jcAudios.get(i),"Now playing : "+titleList.get(i));

    }
    jcplayerView.initPlaylist(jcAudios);
        /*jcplayerView.initAnonPlaylist(jcAudios);*/
    // jcAudios.add(JcAudio.createFromURL(dataArray.get(position),urlList.get(position)));

    initViews(titleList);
}
catch (Exception e)
{
    e.printStackTrace();
}

    }
    private void initViews(final ArrayList<String> dataArray){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new DataAdapterFM(dataArray);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //   MobileAds.initialize(context, "ca-app-pub-1439926476174790~9143120480");
                            mInterstitialAd = new InterstitialAd(getApplicationContext());
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
                    },100 );
                    int position = rv.getChildAdapterPosition(child);
                    //ArrayList<JcAudio> jcAudios = new ArrayList<>();
                    JcAudio audio=jcAudios.get(position);
                    jcplayerView.playAudio(audio);
                    jcplayerView.registerServiceListener(RadioFMActivity.this);
                    // jcplayerView.playAudio(jcAudios);
                    // jcplayerView.registerServiceListener(BakthiPaadalgal.this);
                   /* Intent next=new Intent(getApplicationContext(),KavithaigalBox.class);
                    next.putExtra("position",position);
                    next.putExtra("tag",button_name);
                    startActivity(next);*/
                    //Toast.makeText(getApplicationContext(), dataArray[position], Toast.LENGTH_SHORT).show();
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        try {
            jcplayerView.kill();
        }
        catch (Exception e)
        {

        }
        super.onDestroy();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {

        try {
            jcplayerView.pause();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        catch (Exception e)
        {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // jcplayerView.kill();
    }

    @Override
    protected void onPause() {
        try {
            jcplayerView.createNotification(R.mipmap.ic_launcher);
        }
        catch (Exception e)
        {

        }
      //  moveTaskToBack(true);
        super.onPause();

    }

    @Override
    public void onPreparedAudio(String audioName, int duration) {
        Log.e("audioname",audioName);
    }

    @Override
    public void onCompletedAudio() {
        Log.e("tag","onCompletedAudio");
    }

    @Override
    public void onPaused() {
        Log.e("tag","onPaused");
    }

    @Override
    public void onContinueAudio() {
        Log.e("tag","onContinueAudio");
    }

    @Override
    public void onPlaying() {
        Log.e("tag","onPlaying");
    }

    @Override
    public void onTimeChanged(long currentTime) {
        Log.e("tag","onTimeChanged");
    }

    @Override
    public void updateTitle(String title) {
        Log.e("tag","updateTitle "+title);
    }
}

