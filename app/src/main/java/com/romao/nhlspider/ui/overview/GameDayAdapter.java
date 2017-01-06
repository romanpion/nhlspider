package com.romao.nhlspider.ui.overview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.joda.time.DateTime;

/**
 * Created by rpiontkovsky on 1/6/2017.
 */

public class GameDayAdapter extends FragmentStatePagerAdapter {

    private final int count;
    private final DateTime startDate;
    private final DateTime endDate;

    public GameDayAdapter(FragmentManager fragmentManager, DateTime start, DateTime end, int daysCount) {
        super(fragmentManager);
        endDate = end;
        startDate = start;
        count = daysCount;
    }


    @Override
    public Fragment getItem(int position) {
        DateTime itemDate = startDate.plusDays(position);
        return GameDayFragment.newInstance(itemDate);
    }

    @Override
    public int getCount() {
        return count;
    }
}
