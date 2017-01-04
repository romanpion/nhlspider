package com.romao.nhlspider.ui.overview;

import com.romao.nhlspider.PresenterActivity;
import com.romao.nhlspider.ui.common.ToolbarDecorator;
import com.romao.nhlspider.ui.common.TopActivityToolbarDecorator;

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
    protected ToolbarDecorator createToolbarDecorator() {
        return new TopActivityToolbarDecorator(this) {
            @Override
            public String getActivityTitle() {
                return "Game Overview";
            }
        };
    }
}
