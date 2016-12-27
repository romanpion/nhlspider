package com.romao.nhlspider.ui.overview;

import android.content.Context;

import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.model.util.GameComparator;
import com.romao.nhlspider.storage.LocalStorage;
import com.romao.nhlspider.ui.common.AbstractPresenter;

import java.util.Collections;
import java.util.List;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class GamesOverviewPresenter extends AbstractPresenter<GamesOverviewView> {

    private final Context context;
    private final LocalStorage storage;

    public GamesOverviewPresenter(Context context, LocalStorage storage) {
        this.context = context;
        this.storage = storage;
    }

    @Override
    protected void onViewDetached() {
        // clean up if needed
    }

    @Override
    protected void onViewAttached() {
        // init/update model
        // pass data to view
        List<Game> games = storage.games().readAll();
        Collections.sort(games, new GameComparator());
        view.setData(games);
    }
}
