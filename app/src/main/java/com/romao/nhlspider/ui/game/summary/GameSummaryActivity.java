package com.romao.nhlspider.ui.game.summary;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.romao.nhlspider.PresenterActivity;
import com.romao.nhlspider.util.Route;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

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
        return new GameSummaryPresenter(localStorage, gameId);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Timber.v("back pressed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Timber.v("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        long gameId = getIntent().getLongExtra(Route.EXTRA_GAME_ID, 0L);
        webService.getGameSummary(localStorage.games().readById(gameId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        // TODO
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO
                    }

                    @Override
                    public void onNext(String s) {
                        // TODO
                    }
                });
    }
}
