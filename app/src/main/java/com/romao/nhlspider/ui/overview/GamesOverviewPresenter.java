package com.romao.nhlspider.ui.overview;

import android.view.View;

import com.romao.nhlspider.BaseActivity;
import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.model.util.GameComparator;
import com.romao.nhlspider.storage.LocalStorage;
import com.romao.nhlspider.ui.common.AbstractPresenter;
import com.romao.nhlspider.util.OkResult;

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
        Observable.create(new Observable.OnSubscribe<OkResult>() {
            @Override
            public void call(Subscriber<? super OkResult> subscriber) {
                subscriber.onNext(OkResult.INSTANCE);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<OkResult>() {
                    @Override
                    public void call(OkResult okResult) {
                        if (view != null) {
                            List<Game> games = storage.games().readAll();
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
