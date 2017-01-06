package com.romao.nhlspider.storage.impl.realm;

import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.storage.GameStorage;

import org.joda.time.DateTime;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class RealmGameStorage extends RealmObjectStorage<Game> implements GameStorage {

    private static final String FIELD_GAME_ID = "gameId";
    private static final String FIELD_GAME_DATE = "date";

    public  RealmGameStorage(RealmConfiguration realmConfiguration) {
        super(realmConfiguration, Game.class);
    }

    @Override
    public synchronized Game readById(long id) {
        return copy(prepareQuery().equalTo(FIELD_GAME_ID, id).findFirst());
    }

    @Override
    public synchronized void upsert(final Game game) {
        performTransaction(new StorageTransaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(game);
            }
        });
    }

    @Override
    public synchronized void upsertAll(final List<Game> games) {
        performTransaction(new StorageTransaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(games);
            }
        });
    }

    @Override
    public List<Game> readByDate(DateTime dateTime) {
        return copy(prepareQuery().equalTo(FIELD_GAME_DATE, dateTime.toString()).findAll());
    }
}
