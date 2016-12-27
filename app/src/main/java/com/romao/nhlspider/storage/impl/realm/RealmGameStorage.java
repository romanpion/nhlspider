package com.romao.nhlspider.storage.impl.realm;

import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.storage.GameStorage;

import java.util.List;

import io.realm.Realm;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class RealmGameStorage implements GameStorage {

    private static final String FIELD_GAME_ID = "gameId";

    @Override
    public List<Game> readAll() {
        Realm realm = Realm.getDefaultInstance();
        return realm.copyFromRealm(realm.where(Game.class).findAll());
    }

    @Override
    public Game readById(long id) {
        Realm realm = Realm.getDefaultInstance();
        return realm.copyFromRealm(realm.where(Game.class).equalTo(FIELD_GAME_ID, id).findFirst());
    }

    @Override
    public void upsert(Game game) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(game);
        realm.commitTransaction();
    }

    @Override
    public void upsertAll(List<Game> games) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(games);
        realm.commitTransaction();
    }
}
