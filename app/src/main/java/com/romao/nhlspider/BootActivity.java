package com.romao.nhlspider;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.util.GameDataCreator;
import com.romao.nhlspider.util.OkResult;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class BootActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);

        List<Game> games = localStorage.games().readAll();
        Timber.v("1. games #" + games.size());
        final long start = System.currentTimeMillis();

        if (games.isEmpty()) {
            populateDb(start);
        } else {
            proceed();
        }
    }

    private void populateDb(final long start) {
        loadGameData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        long finish = System.currentTimeMillis();
                        List<Game> games = localStorage.games().readAll();
                        Timber.v("2. games #" + games.size());
                        Timber.v("time elapsed: " + (finish - start) + " ms");
                        proceed();
                    }
                })
                .subscribe(new Subscriber<OkResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(OkResult okResult) {
                    }
                });
    }

    private Observable<OkResult> loadGameData() {
        return Observable.create(new Observable.OnSubscribe<OkResult>() {
            @Override
            public void call(Subscriber<? super OkResult> subscriber) {
                try {
                    List<Game> storedGames = new GameDataCreator(BootActivity.this, "Schedule_20162017.csv").getGameList();
                    localStorage.games().upsertAll(storedGames);
                    subscriber.onNext(OkResult.INSTANCE);
                    subscriber.onCompleted();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    subscriber.onError(ex);
                }
            }
        });
    }

    private void proceed() {
        route.toGamesActivity(this);
        finish();
    }
}
