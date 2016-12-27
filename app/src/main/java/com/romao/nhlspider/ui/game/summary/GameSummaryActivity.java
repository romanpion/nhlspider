package com.romao.nhlspider.ui.game.summary;

import com.romao.nhlspider.PresenterActivity;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class GameSummaryActivity extends PresenterActivity<GameSummaryPresenter, GameSummaryView> {

    @Override
    protected GameSummaryView createView() {
        return new GameSummaryView(this);
    }

    @Override
    protected GameSummaryPresenter createPresenter() {
        return new GameSummaryPresenter();
    }
}
