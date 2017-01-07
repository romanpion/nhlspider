package com.romao.nhlspider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.romao.nhlspider.ui.common.ToolbarDecorator;

/**
 * Created by rpiontkovsky on 1/4/2017.
 */

public abstract class ToolbarActivity extends BaseActivity {

    private FrameLayout container;


    protected abstract ToolbarDecorator createToolbarDecorator();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToolbarDecorator toolbarDecorator = createToolbarDecorator();

        super.setContentView(toolbarDecorator.getContentLayoutResId());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(toolbarDecorator.getActivityTitle());

        container = (FrameLayout) findViewById(R.id.view_content);

        toolbarDecorator.decorate();
    }

    @Override
    public final void setContentView(View view) {
        container.removeAllViews();
        container.addView(view);
    }

    @Override
    public final void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, container, false);
        setContentView(view);
    }

    @Override
    public final void setContentView(View view, ViewGroup.LayoutParams params) {
        container.addView(view, params);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
