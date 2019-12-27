package com.d27.adjoe;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.d27.adjoe.fragment.NotiFragment;
import com.d27.adjoe.fragment.RecyclerViewFragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    private int mPageCount;
    public PageAdapter(FragmentManager fm, int pageCount) {
        super(fm);
        this.mPageCount = pageCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();
                return recyclerViewFragment;
            case 1:
                NotiFragment notiFragment = new NotiFragment();
                return notiFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return mPageCount;
    }

}