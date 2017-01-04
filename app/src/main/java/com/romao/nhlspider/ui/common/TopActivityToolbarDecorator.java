package com.romao.nhlspider.ui.common;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.romao.nhlspider.R;
import com.romao.nhlspider.ToolbarActivity;

import timber.log.Timber;

/**
 * Created by rpiontkovsky on 1/4/2017.
 */

public abstract class TopActivityToolbarDecorator implements ToolbarDecorator {


    public ToolbarActivity activity;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    public TopActivityToolbarDecorator(ToolbarActivity activity) {
        this.activity = activity;
    }

    @Override
    public final int getContentLayoutResId() {
        return R.layout.activity_drawer_toolbar;
    }

    @Override
    public void decorate() {
        final ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        if (mDrawerToggle == null) {
            Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
            mDrawerLayout = (DrawerLayout) activity.findViewById(R.id.layout_drawer);

            mDrawerToggle = new ActionBarDrawerToggle(activity, mDrawerLayout, toolbar,
                    R.string.app_name, // nav drawer open - description for accessibility
                    R.string.app_name // nav drawer close - description for accessibility
            ) {
                public void onDrawerClosed(View view) {
                    Timber.v("onDrawerClosed");
                }

                public void onDrawerOpened(View drawerView) {
                    Timber.v("onDrawerOpened");
                }
            };

            mDrawerLayout.setDrawerListener(mDrawerToggle);
            mDrawerToggle.syncState();
        }
    }
}
