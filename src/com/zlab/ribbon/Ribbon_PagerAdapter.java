package com.zlab.ribbon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

public class Ribbon_PagerAdapter extends FragmentPagerAdapter {
    public Ribbon_PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Ribbon_SectionFragment();
        Bundle args = new Bundle();
        args.putInt(Ribbon_SectionFragment.ARG_SECTION_NUMBER, position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return Ribbon_Main.mContext.getString(R.string.title_section_user).toUpperCase(l);
            case 1:
                return Ribbon_Main.mContext.getString(R.string.title_section_ribbon).toUpperCase(l);
            case 2:
                return Ribbon_Main.mContext.getString(R.string.title_section_network).toUpperCase(l);
        }
        return null;
    }
}
