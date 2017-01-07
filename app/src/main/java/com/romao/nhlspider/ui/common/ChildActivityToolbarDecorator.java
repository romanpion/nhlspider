package com.romao.nhlspider.ui.common;

import android.support.v7.app.ActionBar;

import com.romao.nhlspider.R;
import com.romao.nhlspider.ToolbarActivity;

/**
 * Created by rpiontkovsky on 1/4/2017.
 */

public abstract class ChildActivityToolbarDecorator implements ToolbarDecorator {

    private final ToolbarActivity activity;

    public ChildActivityToolbarDecorator(ToolbarActivity activity) {
        this.activity = activity;
    }

    @Override
    public final int getContentLayoutResId() {
        return R.layout.activity_simple_toolbar;
    }

    @Override
    public void decorate() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayOptions(getSupportActionBar().getDisplayOptions() | ActionBar.DISPLAY_SHOW_TITLE);
    }

    private ActionBar getSupportActionBar() {
        return activity.getSupportActionBar();
    }
}
