package com.liteart.apps.sivanmalai;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.liteart.apps.sivanmalai.R;

import java.util.ArrayList;


public class StoriesFragmentTab1 extends Fragment {

    private ListView mainListView ;
    ListAdapter listAdapter;
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
        final String [] titles = StringArrays.story;
        ArrayList<String> titlesList = new ArrayList<String>();
        int j=1;
        for(String i:titles)
        {
            String k=i.replace("_"," ");
            titlesList.add(String.valueOf(j++)+" . "+k);
        }
        // titlesList.addAll( Arrays.asList(titles) );

        listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simplerow, titlesList);

        mainListView.setAdapter( listAdapter );
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Intent ii=new Intent(getActivity(), EssayWebviewActivity.class);
                    ii.putExtra("position", position);
                    ii.putExtra("string-array", titles);
                    startActivity(ii);


            }
        });

    }

}


