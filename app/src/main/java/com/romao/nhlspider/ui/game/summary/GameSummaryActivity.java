package com.romao.nhlspider.ui.game.summary;

import com.romao.nhlspider.PresenterActivity;
import com.romao.nhlspider.ui.common.ChildActivityToolbarDecorator;
import com.romao.nhlspider.ui.common.ToolbarDecorator;
import com.romao.nhlspider.util.Route;

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
        long gameId = getIntent().getLongExtra(Route.EXTRA_GAME_ID, 0L);
        return new GameSummaryPresenter(localStorage, dataManager, connectionManager, gameId);
    }

    @Override
    protected ToolbarDecorator createToolbarDecorator() {
        return new ChildActivityToolbarDecorator(this) {
            @Override
            public String getActivityTitle() {
                return "Game Summary";
            }
        };
    }
}
