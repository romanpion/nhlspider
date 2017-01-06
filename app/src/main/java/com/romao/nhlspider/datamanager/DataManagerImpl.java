package com.romao.nhlspider.datamanager;

import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.model.GameSummary;
import com.romao.nhlspider.storage.LocalStorage;
import com.romao.nhlspider.util.OkResult;
import com.romao.nhlspider.web.WebService;

import rx.Observable;
import rx.Subscriber;
import timber.log.Timber;

/**
 * Created by rpiontkovsky on 1/3/2017.
 */

public class DataManagerImpl implements DataManager {

    private final LocalStorage storage;
    private final WebService webService;

    public DataManagerImpl(LocalStorage storage, WebService webService) {
        this.storage = storage;
        this.webService = webService;
    }

    @Override
    public Observable<OkResult> getGame(final long gameId) {
        return Observable.create(new Observable.OnSubscribe<OkResult>() {
            @Override
            public void call(final Subscriber<? super OkResult> subscriber) {
                final Game game = storage.games().readById(gameId);
                if (game != null && game.getGameSummary() != null) {
                    subscriber.onNext(OkResult.INSTANCE);
                    subscriber.onCompleted();
                    return;
                }

                webService.getGameSummary(game).subscribe(new Subscriber<GameSummary>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onNext(OkResult.INSTANCE);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(GameSummary gameSummary) {
                        final Game game = storage.games().readById(gameId);
                        gameSummary.setGameId(game.getGameId());
                        storage.gameSummary().upsert(gameSummary);
                        Timber.v("upsert gameSummary #" + gameId);

                        game.setGameSummary(storage.gameSummary().readByGameId(gameId));
                        storage.games().upsert(game);
                        Timber.v("upsert game #" + gameId);
                    }
                });
            }
        });
    }
}
