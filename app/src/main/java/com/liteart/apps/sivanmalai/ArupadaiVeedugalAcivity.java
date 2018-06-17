package com.liteart.apps.sivanmalai;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Timer;
import java.util.TimerTask;

public class ArupadaiVeedugalAcivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_thiruparakundram,btn_tiruchendur,
            btn_palani,btn_swamimalai,btn_thiruthanigai,
            btn_palamuthirsolai,btn_news;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    ViewPager viewPager;
    int images[] = {R.drawable.murugan_image, R.drawable.murugan,R.drawable.image3};
    MyCustomPagerAdapter myCustomPagerAdapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arupadai_veedugal);
        setTitle(getString(R.string.arupadai_veedugal));
      //  MobileAds.initialize(getApplicationContext(), "ca-app-pub-1439926476174790~9143120480");
        AdView mAdView = (AdView) findViewById(R.id.adView_arupadai_veedugal);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer .schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
        myCustomPagerAdapter = new MyCustomPagerAdapter(ArupadaiVeedugalAcivity.this, images);
        viewPager.setAdapter(myCustomPagerAdapter);
        btn_thiruparakundram=(Button)findViewById(R.id.btn_thiruparakundram);
        btn_tiruchendur=(Button)findViewById(R.id.btn_tiruchendur);
        btn_palani=(Button)findViewById(R.id.btn_palani);
        btn_swamimalai=(Button)findViewById(R.id.btn_swamimalai);
        btn_thiruthanigai=(Button)findViewById(R.id.btn_thiruthanigai);
        btn_palamuthirsolai=(Button)findViewById(R.id.btn_palamuthirsolai);
        btn_news=(Button)findViewById(R.id.btn_news);

        btn_thiruparakundram.setOnClickListener(this);
        btn_tiruchendur.setOnClickListener(this);
        btn_palani.setOnClickListener(this);
        btn_swamimalai.setOnClickListener(this);
        btn_thiruthanigai.setOnClickListener(this);
        btn_palamuthirsolai.setOnClickListener(this);
        btn_news.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_thiruparakundram:
                // do your code
                Intent in=new Intent(getApplicationContext(),ArupadaiVeedugalContentActivity.class);
                in.putExtra("temple_name","thiruparakundram");
                startActivity(in);
                break;

            case R.id.btn_tiruchendur:
                // do your code
                Intent in_tiruchentur=new Intent(getApplicationContext(),ArupadaiVeedugalContentActivity.class);
                in_tiruchentur.putExtra("temple_name","tiruchentur");
                startActivity(in_tiruchentur);
                break;

            case R.id.btn_palani:
                // do your code
                Intent in_palani=new Intent(getApplicationContext(),ArupadaiVeedugalContentActivity.class);
                in_palani.putExtra("temple_name","palani");
                startActivity(in_palani);
                break;
            case R.id.btn_swamimalai:
                // do your code
                Intent in_swamimalai=new Intent(getApplicationContext(),ArupadaiVeedugalContentActivity.class);
                in_swamimalai.putExtra("temple_name","swamimalai");
                startActivity(in_swamimalai);
                break;

            case R.id.btn_thiruthanigai:
                // do your code
                Intent in_thiruthanigai=new Intent(getApplicationContext(),ArupadaiVeedugalContentActivity.class);
                in_thiruthanigai.putExtra("temple_name","thiruthanigai");
                startActivity(in_thiruthanigai);
                break;

            case R.id.btn_palamuthirsolai:
                // do your code
                Intent in_palamuthirsolai=new Intent(getApplicationContext(),ArupadaiVeedugalContentActivity.class);
                in_palamuthirsolai.putExtra("temple_name","palamuthirsolai");
                startActivity(in_palamuthirsolai);
                break;
            case R.id.btn_news:
                // do your code
                Intent in_news=new Intent(getApplicationContext(),NewsListActivity.class);
                startActivity(in_news);
                break;
            default:
                break;
        }

    }
}
