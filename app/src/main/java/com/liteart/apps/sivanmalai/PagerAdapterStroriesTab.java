package com.liteart.apps.sivanmalai;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapterStroriesTab extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public PagerAdapterStroriesTab(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                StoriesFragmentTab1 order=new StoriesFragmentTab1();
                return order;

            case 1:
                StoriesFragmentTab2 order1=new StoriesFragmentTab2();
                return order1;


            default:
                return null;
        }
    }

    @Override
        public int getCount() {
        return mNumOfTabs;
    }
}
