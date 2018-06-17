package com.liteart.apps.sivanmalai;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.liteart.apps.sivanmalai.R;

public class NewsDesActivity extends AppCompatActivity {
    String str_description=null,str_date=null;
    Button home,share;
    TextView date_to_time,desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_des);
setTitle(getString(R.string.app_name_header));
        AdView mAdView = (AdView) findViewById(R.id.adViewNewsDesPage);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        home=(Button)findViewById(R.id.home);
        share=(Button)findViewById(R.id.share);
        date_to_time=(TextView)findViewById(R.id.date_to_time);
        desc=(TextView)findViewById(R.id.description);
        try {
            str_description = getIntent().getStringExtra("description");
            str_date = getIntent().getStringExtra("date");
            if (str_description != null)
                desc.setText(str_description);
            if (str_date != null)
                date_to_time.setText(str_date);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "App link ");
                String share= str_description+Html.fromHtml("<br/>")+str_date+Html.fromHtml("<br/>") + "Download app in https://play.google.com/store/" +
                        "apps/details?id="+getPackageName();
                sharingIntent.putExtra(Intent.EXTRA_TEXT,share);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });
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
