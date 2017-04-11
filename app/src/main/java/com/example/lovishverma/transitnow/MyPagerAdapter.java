package com.example.lovishverma.transitnow;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;


    public MyPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentOne f1 = new FragmentOne();
                return f1;
            case 1:
                FragmentTwo f2 = new FragmentTwo();
                return f2;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
