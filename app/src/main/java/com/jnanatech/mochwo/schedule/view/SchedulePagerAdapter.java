package com.jnanatech.mochwo.schedule.view;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SchedulePagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public SchedulePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new DayOneFragment();
        } else  {
            return new DayTwoFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return "Day 1";
            case 1:
                return "Day 2";
            default:
                return null;
        }
    }

}
