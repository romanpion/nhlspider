package com.romao.nhlspider.ui.game.summary;

import android.os.Handler;
import android.os.Message;

import com.romao.nhlspider.datamanager.DataManager;
import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.model.GameSummary;
import com.romao.nhlspider.model.enums.GameState;
import com.romao.nhlspider.storage.LocalStorage;
import com.romao.nhlspider.ui.common.AbstractPresenter;
import com.romao.nhlspider.util.ConnectionManager;
import com.romao.nhlspider.util.OkResult;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class GameSummaryPresenter extends AbstractPresenter<GameSummaryView> {

    private final long gameId;
    private final DataManager dataManager;
    private final LocalStorage storage;
    private final ConnectionManager connectionManager;

    private Subscription subscription = null;


    private final Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                updateUi();
            }
        }
    };

    public GameSummaryPresenter(final LocalStorage storage,
                                final DataManager dataManager,
                                final ConnectionManager connectionManager,
                                long gameId) {
        this.gameId = gameId;
        this.storage = storage;
        this.dataManager = dataManager;
        this.connectionManager = connectionManager;
    }

    @Override
    protected void onViewDetached() {

    }

    @Override
    protected void onViewAttached() {
        GameSummary gameSummary = storage.gameSummary().readByGameId(gameId);
        if (gameSummary == null || gameSummary.getGameState() != GameState.FINAL) {
            updateGameSummary();
        }

        triggerUiRefresh();
    }

    private void updateGameSummary() {
        if (!connectionManager.isConnected()) {
            view.showErrorToast("No internet connection");
            view.setRefreshFinished();
        } else {
            subscription = dataManager.getGame(gameId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnTerminate(new Action0() {
                        @Override
                        public void call() {
                            subscription = null;
                            triggerUiRefresh();
                            if (view != null) {
                                view.setRefreshFinished();
                            }
                        }
                    })
                    .subscribe(new Subscriber<OkResult>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e(e, "Game Summary Error");
                        }

                        @Override
                        public void onNext(OkResult game) {
                        }
                    });
        }
    }

    private void triggerUiRefresh() {
        refreshHandler.sendEmptyMessage(0);
    }

    protected void updateUi() {
        Timber.v("update ui summary #" + gameId);
        Game game = storage.games().readById(gameId);
        updateUi(game);
    }

    protected void updateUi(Game game) {
        if (view != null) {
            view.setGame(game);
            view.setLoading(subscription != null);
        }
    }

    public void onRefreshRequested() {
        updateGameSummary();
    }
}
