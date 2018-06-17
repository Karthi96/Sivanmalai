package com.liteart.apps.sivanmalai;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentTab1 order=new FragmentTab1();
                return order;

            case 1:
                FragmentTab2 order1=new FragmentTab2();
                return order1;

            case 2:
                FragmentTab3 order2=new FragmentTab3();
                return order2;


            default:
                return null;
        }
    }

    @Override
        public int getCount() {
        return mNumOfTabs;
    }
}
