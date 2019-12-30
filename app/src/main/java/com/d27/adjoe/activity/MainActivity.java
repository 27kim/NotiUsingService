package com.d27.adjoe.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.d27.adjoe.fragment.PageAdapter;
import com.d27.adjoe.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private PageAdapter mPageAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = (TabLayout) findViewById(R.id.layout_tab);
        mTabLayout.addTab(mTabLayout.newTab().setText("Notification"));
        mTabLayout.addTab(mTabLayout.newTab().setText("RecyclerView"));

        mViewPager = (ViewPager) findViewById(R.id.pager_content);
        mPageAdapter = new PageAdapter(
                getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(mPageAdapter);

        mViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}

