package com.example.q.week3;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.q.week3.EvaluationFragment;
import com.example.q.week3.DailyFragment;
import com.example.q.week3.GraphFragment;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public MyPagerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                EvaluationFragment tab1 = new EvaluationFragment();
                return tab1;
            case 1:
                DailyFragment tab2 = new DailyFragment();
                return  tab2;
            case 2:
                ChartFragment tab3 = new ChartFragment();
                return  tab3;
            case 3:
                GraphFragment tab4 = new GraphFragment();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}