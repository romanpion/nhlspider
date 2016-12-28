package com.romao.nhlspider.ui.overview;

import com.romao.nhlspider.PresenterActivity;

import timber.log.Timber;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class GamesOverviewActivity extends PresenterActivity<GamesOverviewPresenter, GamesOverviewView> {

    @Override
    protected GamesOverviewView createView() {
        return new GamesOverviewView(this);
    }

    @Override
    protected GamesOverviewPresenter createPresenter() {
        return new GamesOverviewPresenter(this, localStorage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.v("onResume");
    }
}
