package com.romao.nhlspider.ui.overview;

import android.view.View;

import com.romao.nhlspider.BaseActivity;
import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.model.util.GameComparator;
import com.romao.nhlspider.storage.LocalStorage;
import com.romao.nhlspider.ui.common.AbstractPresenter;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class GamesOverviewPresenter extends AbstractPresenter<GamesOverviewView> {

    private final BaseActivity activity;
    private final LocalStorage storage;

    public GamesOverviewPresenter(BaseActivity activity, LocalStorage storage) {
        this.activity = activity;
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
        Observable.create(new Observable.OnSubscribe<List<Game>>() {
            @Override
            public void call(Subscriber<? super List<Game>> subscriber) {
                List<Game> games = storage.games().readAll();
                Collections.sort(games, new GameComparator());
                subscriber.onNext(games);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Game>>() {
                    @Override
                    public void call(List<Game> games) {
                        if (view != null) {
                            view.setData(games);
                            Timber.v("update overview");
                        }
                    }
                });
    }

    public void onGameClicked(Game game, View sharedView) {
        activity.getRoute().toGameDetails(activity, sharedView, game.getGameId());
    }
}
