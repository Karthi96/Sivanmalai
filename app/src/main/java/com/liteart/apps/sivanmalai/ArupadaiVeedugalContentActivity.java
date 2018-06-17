package com.liteart.apps.sivanmalai;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.liteart.apps.sivanmalai.R;

public class ArupadaiVeedugalContentActivity extends AppCompatActivity {
    String temple_name=null;
    Button btn_maps,btn_poojatimings;
    String url_maps,url_poojatimings;
    LinearLayout ll5,ll6,ll7,ll4;
    TextView title1,title2,title3,title4,title5,title6,title7,temple_title,historytitle;
    TextView content1,content2,content3,content4,content5,content6,content7,main_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arupadai_veedugal_content);
       // MobileAds.initialize(getApplicationContext(), "ca-app-pub-1439926476174790~9143120480");
        AdView mAdView = (AdView) findViewById(R.id.adView_content_arupadai);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        creaateID();
        temple_name=getIntent().getStringExtra("temple_name");
        switch (temple_name)
        {
            case "thiruparakundram":
                temple_title.setText(getString(R.string.thiruparakundram));
                setTitle(getString(R.string.thiruparakundram));
                url_maps=getString(R.string.maps_tiruparakundram);
                url_poojatimings=getString(R.string.pooja_timings_tiruparakundram);
                main_content.setText(getString(R.string.thiruparakundram_main_content));
                title1.setText(getString(R.string.thiruparakundram_title1));
                title2.setText(getString(R.string.thiruparakundram_title2));
                title3.setText(getString(R.string.thiruparakundram_title3));
                title4.setText(getString(R.string.thiruparakundram_title4));

                /*title5.setText(getString(R.string.thiruparakundram_title5));
                title6.setText(getString(R.string.thiruparakundram_title6));
                title7.setText(getString(R.string.thiruparakundram_title7));*/
                content1.setText(getString(R.string.thiruparakundram_content1));
                content2.setText(getString(R.string.thiruparakundram_content2));
                content3.setText(getString(R.string.thiruparakundram_content3));
                content4.setText(getString(R.string.thiruparakundram_content4));
                ll5.setVisibility(View.GONE);
                ll6.setVisibility(View.GONE);
                ll7.setVisibility(View.GONE);
                /*content5.setText(getString(R.string.thiruparakundram_content5));
                content6.setText(getString(R.string.thiruparakundram_content6));
                content7.setText(getString(R.string.thiruparakundram_content7));*/
                break;
            case "tiruchentur":
                temple_title.setText(getString(R.string.tiruchendur));
                setTitle(getString(R.string.tiruchendur));
                url_maps=getString(R.string.maps_tiruchendur);
                url_poojatimings=getString(R.string.pooja_timings_tiruchendur);
                main_content.setText(getString(R.string.tiruchendur_main_content));
                title1.setText(getString(R.string.tiruchendur_title1));
                title2.setText(getString(R.string.tiruchendur_title2));
                title3.setText(getString(R.string.tiruchendur_title3));
               /* title4.setText(getString(R.string.tiruchendur_title4));
                title5.setText(getString(R.string.tiruchendur_title5));
                title6.setText(getString(R.string.tiruchendur_title6));
                title7.setText(getString(R.string.tiruchendur_title7));*/
                content1.setText(getString(R.string.tiruchendur_content1));
                content2.setText(getString(R.string.tiruchendur_content2));
                content3.setText(getString(R.string.tiruchendur_content3));
                historytitle.setVisibility(View.GONE);
                ll4.setVisibility(View.GONE);
                ll5.setVisibility(View.GONE);
                ll6.setVisibility(View.GONE);
                ll7.setVisibility(View.GONE);
                /*content4.setText(getString(R.string.tiruchendur_content4));
                content5.setText(getString(R.string.tiruchendur_content5));
                content6.setText(getString(R.string.tiruchendur_content6));
                content7.setText(getString(R.string.tiruchendur_content7));*/
                break;
            case "palani":
                temple_title.setText(getString(R.string.palani));
                setTitle(getString(R.string.palani));
                url_maps=getString(R.string.maps_palani);
                url_poojatimings=getString(R.string.pooja_timings_palani);

                title1.setText(getString(R.string.palani_title1));
                title2.setText(getString(R.string.palani_title2));
                title3.setText(getString(R.string.palani_title3));

                content1.setText(getString(R.string.palani_content1));
                content2.setText(getString(R.string.palani_content2));
                content3.setText(getString(R.string.palani_content3));
                main_content.setVisibility(View.GONE);
                historytitle.setVisibility(View.GONE);
                ll4.setVisibility(View.GONE);
                ll5.setVisibility(View.GONE);
                ll6.setVisibility(View.GONE);
                ll7.setVisibility(View.GONE);
                break;
            case "swamimalai":
                temple_title.setText(getString(R.string.swamimalai));
                setTitle(getString(R.string.swamimalai));
                url_maps=getString(R.string.maps_swamimalai);
                url_poojatimings=getString(R.string.pooja_timings_swamimalai);
                main_content.setText(getString(R.string.swamimalai_main_content));
                title1.setText(getString(R.string.swamimalai_title1));
                title2.setText(getString(R.string.swamimalai_title2));
                title3.setText(getString(R.string.swamimalai_title3));
                title4.setText(getString(R.string.swamimalai_title4));
                title5.setText(getString(R.string.swamimalai_title5));
                content1.setText(getString(R.string.swamimalai_content1));
                content2.setText(getString(R.string.swamimalai_content2));
                content3.setText(getString(R.string.swamimalai_content3));
                content4.setText(getString(R.string.swamimalai_content4));
                content5.setText(getString(R.string.swamimalai_content5));
                ll6.setVisibility(View.GONE);
                ll7.setVisibility(View.GONE);
                break;
            case "thiruthanigai":
                temple_title.setText(getString(R.string.thiruthanigai));
                setTitle(getString(R.string.thiruthanigai));
                url_maps=getString(R.string.maps_tirutanigai);
                url_poojatimings=getString(R.string.pooja_timings_tirutanigai);
                main_content.setText(getString(R.string.thiruthanigai_main_content));
                title1.setText(getString(R.string.thiruthanigai_title1));
                title2.setText(getString(R.string.thiruthanigai_title2));
                title3.setText(getString(R.string.thiruthanigai_title3));
                title4.setText(getString(R.string.thiruthanigai_title4));
                title5.setText(getString(R.string.thiruthanigai_title5));
                title6.setText(getString(R.string.thiruthanigai_title6));
                content1.setText(getString(R.string.thiruthanigai_content1));
                content2.setText(getString(R.string.thiruthanigai_content2));
                content3.setText(getString(R.string.thiruthanigai_content3));
                content4.setText(getString(R.string.thiruthanigai_content4));
                content5.setText(getString(R.string.thiruthanigai_content5));
                content6.setText(getString(R.string.thiruthanigai_content6));
                ll7.setVisibility(View.GONE);
                break;
            case "palamuthirsolai":
                temple_title.setText(getString(R.string.palamuthirsolai));
                setTitle(getString(R.string.palamuthirsolai));
                url_maps=getString(R.string.maps_pazhamudhirsolai);
                url_poojatimings=getString(R.string.pooja_timings_pazhamudhirsolai);
                main_content.setText(getString(R.string.palamuthirsolai_main_content));
                title1.setText(getString(R.string.palamuthirsolai_title1));
                title2.setText(getString(R.string.palamuthirsolai_title2));
                title3.setText(getString(R.string.palamuthirsolai_title3));
                title4.setText(getString(R.string.palamuthirsolai_title4));
                title5.setText(getString(R.string.palamuthirsolai_title5));
                content1.setText(getString(R.string.palamuthirsolai_content1));
                content2.setText(getString(R.string.palamuthirsolai_content2));
                content3.setText(getString(R.string.palamuthirsolai_content3));
                content4.setText(getString(R.string.palamuthirsolai_content4));
                content5.setText(getString(R.string.palamuthirsolai_content5));
                ll6.setVisibility(View.GONE);
                ll7.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        btn_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(url_maps));
                startActivity(intent);


            }
        });
        btn_poojatimings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(url_poojatimings));
                startActivity(intent);


            }
        });
    }

    private void creaateID() {
        btn_maps=(Button)findViewById(R.id.btn_maps);
        btn_poojatimings=(Button)findViewById(R.id.btn_poojatimings);
        ll4=(LinearLayout)findViewById(R.id.ll4);
        ll5=(LinearLayout)findViewById(R.id.ll5);
        ll6=(LinearLayout)findViewById(R.id.ll6);
        ll7=(LinearLayout)findViewById(R.id.ll7);

        temple_title=(TextView)findViewById(R.id.temple_title);
        main_content=(TextView)findViewById(R.id.main_content);
        historytitle=(TextView)findViewById(R.id.history);
        title1=(TextView)findViewById(R.id.title1);
        title2=(TextView)findViewById(R.id.title2);
        title3=(TextView)findViewById(R.id.title3);
        title4=(TextView)findViewById(R.id.title4);
        title5=(TextView)findViewById(R.id.title5);
        title6=(TextView)findViewById(R.id.title6);
        title7=(TextView)findViewById(R.id.title7);

        content1=(TextView)findViewById(R.id.content1);
        content2=(TextView)findViewById(R.id.content2);
        content3=(TextView)findViewById(R.id.content3);
        content4=(TextView)findViewById(R.id.content4);
        content5=(TextView)findViewById(R.id.content5);
        content6=(TextView)findViewById(R.id.content6);
        content7=(TextView)findViewById(R.id.content7);
    }
}
