package com.romao.nhlspider.ui.game.summary;

import android.view.Menu;
import android.view.MenuItem;

import com.romao.nhlspider.PresenterActivity;
import com.romao.nhlspider.R;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game_summary, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemRefresh = menu.findItem(R.id.item_refresh);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_refresh) {
            presenter.onRefreshRequested();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
