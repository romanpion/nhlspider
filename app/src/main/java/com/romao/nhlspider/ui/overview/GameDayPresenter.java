package com.romao.nhlspider.ui.overview;

import android.view.View;

import com.romao.nhlspider.BaseActivity;
import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.storage.LocalStorage;
import com.romao.nhlspider.ui.common.AbstractPresenter;
import com.romao.nhlspider.util.OkResult;

import org.joda.time.DateTime;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by rpiontkovsky on 1/6/2017.
 */

public class GameDayPresenter extends AbstractPresenter<GameDayView> {

    private final BaseActivity activity;
    private final LocalStorage storage;
    private final DateTime date;

    public GameDayPresenter(BaseActivity activity, LocalStorage storage, DateTime dateTime) {
        this.activity = activity;
        this.storage = storage;
        this.date = dateTime;
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
                            List<Game> games = storage.games().readByDate(date);
                            view.setData(games);
                        }
                    }
                });
    }

    public void onGameClicked(Game game) {
        activity.getRoute().toGameDetails(activity, game.getGameId());
    }
}
