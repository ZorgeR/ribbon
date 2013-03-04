package com.zlab.ribbon;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class Ribbon_Main extends FragmentActivity {

    Ribbon_PagerAdapter mRibbonPagerAdapter;
    ViewPager mViewPager;
    static Context mContext;
    static Ribbon_Main mActivity; /* TODO удалить если не используется */

    static final int TAB_USER = 1;
    static final int TAB_RIBBON = 2;
    static final int TAB_NETWORK = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ribbon_main);

        mRibbonPagerAdapter = new Ribbon_PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mRibbonPagerAdapter);
        mViewPager.setCurrentItem(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ribbon__main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                return true;
            }
            case R.id.action_exit:{
                finish();
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

}
