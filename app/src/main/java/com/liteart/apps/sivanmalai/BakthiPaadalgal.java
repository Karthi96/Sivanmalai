package com.liteart.apps.sivanmalai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.jean.jcplayer.JcAudio;
import com.example.jean.jcplayer.JcPlayerService;
import com.example.jean.jcplayer.JcPlayerView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BakthiPaadalgal extends AppCompatActivity implements JcPlayerService.JcPlayerServiceListener {
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
        setTitle(com.liteart.apps.sivanmalai.R.string.songs);
        try {
        setContentView(com.liteart.apps.sivanmalai.R.layout.activity_bakthi_paadalgal);



        RecyclerView recyclerView = (RecyclerView)findViewById(com.liteart.apps.sivanmalai.R.id.card_recycler_view);
        jcplayerView = (JcPlayerView) findViewById(com.liteart.apps.sivanmalai.R.id.jcplayer);

    recyclerView.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
    recyclerView.setLayoutManager(layoutManager);
    SharedPreferences pref = getApplicationContext().getSharedPreferences("dataPref", MODE_PRIVATE);
    Gson gson = new Gson();
    String json = pref.getString("titleList", null);
    String jsonUrl = pref.getString("urlList", null);
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
    askForPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,0x3);
}
catch (Exception e)
{
    e.printStackTrace();
}

    }

    @Override
    protected void onDestroy() {
        try {
            jcplayerView.kill();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private void initViews(final ArrayList<String> dataArray){
        RecyclerView recyclerView = (RecyclerView)findViewById(com.liteart.apps.sivanmalai.R.id.card_recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new DataAdapter(dataArray,urlList,jcplayerView,jcAudios,getApplicationContext());
        recyclerView.setAdapter(adapter);

       /* recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
                    //ArrayList<JcAudio> jcAudios = new ArrayList<>();
                    JcAudio audio=jcAudios.get(position);
                    jcplayerView.playAudio(audio);
                    jcplayerView.registerServiceListener(BakthiPaadalgal.this);
                   // jcplayerView.playAudio(jcAudios);
                   // jcplayerView.registerServiceListener(BakthiPaadalgal.this);
                   *//* Intent next=new Intent(getApplicationContext(),KavithaigalBox.class);
                    next.putExtra("position",position);
                    next.putExtra("tag",button_name);
                    startActivity(next);*//*
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
        });*/
    }
    public void onBackPressed() {
/*        if(!) {
            new AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                    .setTitle(getString(R.string.app_name))
                    .setMessage("Are you want to play in background or not ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            moveTaskToBack(true);
                        }

                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            jcplayerView.pause();
                            finish();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }
                    })
                    .show();
        }*/
        jcplayerView.pause();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();


    }

    @Override
    protected void onResume() {
        super.onResume();

       // jcplayerView.kill();
    }

    @Override
    protected void onPause() {
        jcplayerView.createNotification(com.liteart.apps.sivanmalai.R.mipmap.ic_launcher);
    //    moveTaskToBack(true);
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
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(BakthiPaadalgal.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(BakthiPaadalgal.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(BakthiPaadalgal.this, new String[]{permission}, requestCode);
            }

        } else {
            // saveImagefromUrl(url, title);
        }
    }
}
