package com.romao.nhlspider;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.util.GameDataCreator;
import com.romao.nhlspider.util.OkResult;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class BootActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.v("nhl BOOT");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);
    }

    @Override
    protected void onResume() {
        super.onResume();

        readGames().map(checkGames())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        proceed();
                    }
                }).subscribe(new Subscriber<OkResult>() {
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

    @NonNull
    private Func1<List<Game>, OkResult> checkGames() {
        return new Func1<List<Game>, OkResult>() {
            @Override
            public OkResult call(List<Game> games) {
                if (!games.isEmpty()) {
                    return OkResult.INSTANCE;
                }

                return loadGameData().toBlocking().first();
            }
        };
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

    private Observable<List<Game>> readGames() {
        return Observable.create(new Observable.OnSubscribe<List<Game>>() {
            @Override
            public void call(Subscriber<? super List<Game>> subscriber) {
                subscriber.onNext(storage().games().readAll());
                subscriber.onCompleted();
            }
        });
    }
}
