package com.romao.nhlspider.ui.game.summary;

import android.os.Handler;
import android.os.Message;

import com.romao.nhlspider.datamanager.DataManager;
import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.storage.LocalStorage;
import com.romao.nhlspider.ui.common.AbstractPresenter;
import com.romao.nhlspider.util.OkResult;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class GameMenuPresenter extends AbstractPresenter<GameMenuView> {

    private final long gameId;
    private final DataManager dataManager;
    private final LocalStorage storage;

    private Subscription subscription = null;


    private final Handler refreshHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                updateUi();
            }
        }
    };

    public GameMenuPresenter(final LocalStorage storage, final DataManager dataManager, long gameId) {
        this.gameId = gameId;
        this.storage = storage;
        this.dataManager = dataManager;
    }

    @Override
    protected void onViewDetached() {

    }

    @Override
    protected void onViewAttached() {
        subscription = dataManager.getGame(gameId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OkResult>() {
                    @Override
                    public void onCompleted() {
                        subscription = null;
                        triggerUiRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscription = null;
                        triggerUiRefresh();
                        Timber.e(e, "Game Summary Error");
                    }

                    @Override
                    public void onNext(OkResult game) {
                    }
                });

        triggerUiRefresh();
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
}
