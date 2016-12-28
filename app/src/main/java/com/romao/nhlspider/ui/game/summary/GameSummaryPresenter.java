package com.romao.nhlspider.ui.game.summary;

import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.storage.LocalStorage;
import com.romao.nhlspider.ui.common.AbstractPresenter;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class GameSummaryPresenter extends AbstractPresenter<GameSummaryView> {

    private final long gameId;
    private final Game game;
    private final LocalStorage storage;

    public GameSummaryPresenter(final LocalStorage storage, long gameId) {
        this.gameId = gameId;
        this.game = storage.games().readById(gameId);
        this.storage = storage;
    }

    @Override
    protected void onViewDetached() {

    }

    @Override
    protected void onViewAttached() {
        view.setGame(game);
    }
}
