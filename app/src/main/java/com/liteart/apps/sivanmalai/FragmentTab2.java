package com.liteart.apps.sivanmalai;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.liteart.apps.sivanmalai.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FragmentTab2 extends Fragment {



    private ListView mainListView ;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_essay, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v=getView();
        MobileAds.initialize(getActivity(), "ca-app-pub-1439926476174790~9143120480");
        AdView mAdView = (AdView) v.findViewById(R.id.adView_essay1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mainListView = (ListView) v.findViewById( R.id.mainListView );
        final String [] titles = StringArrays.tab2_title;

        ArrayList<String> titlesList = new ArrayList<String>();
        int j=1;
        for(String i:titles)
        {
            titlesList.add(String.valueOf(j++)+" . "+i);
        }

        String[] urlImage=fetch(StringArrays.tab2_url);
        CustomListYoutube adapter = new
                CustomListYoutube(getActivity(), StringArrays.tab2_title, urlImage);

        mainListView.setAdapter( adapter );
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent ii=new Intent(getActivity(), WebviewBrowser.class);
                ii.putExtra("url", StringArrays.tab2_url[position]);
                startActivity(ii);

            }
        });

    }
    private String[] fetch(String[] tab1_url) {
        String[] temp=new String[tab1_url.length];
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        for(int i=0;i<tab1_url.length;i++)
        {
            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(tab1_url[i].trim());
            String ytid = null;
            if(matcher.find()){
                ytid= matcher.group();
            }
            if(ytid!=null)
                temp[i]="https://img.youtube.com/vi/"+ytid+"/0.jpg";
        }
        Log.e("urlImage",temp.toString());
        return temp;
    }
}


