package com.romao.nhlspider.storage.impl.realm;

import com.romao.nhlspider.model.GameSummary;
import com.romao.nhlspider.storage.GameSummaryStorage;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by rpiontkovsky on 1/3/2017.
 */

public class RealmGameSummaryStorage extends RealmObjectStorage<GameSummary> implements GameSummaryStorage {

    private static final String FIELD_GAME_ID = "gameId";

    public RealmGameSummaryStorage(RealmConfiguration realmConfiguration) {
        super(realmConfiguration, GameSummary.class);
    }

    @Override
    public GameSummary readByGameId(long gameId) {
        return copy(prepareQuery().equalTo(FIELD_GAME_ID, gameId).findFirst());
    }

    @Override
    public void upsert(final GameSummary gameSummary) {
        performTransaction(new StorageTransaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(gameSummary);
            }
        });
    }
}
