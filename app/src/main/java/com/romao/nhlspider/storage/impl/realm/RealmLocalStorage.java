package com.romao.nhlspider.storage.impl.realm;

import com.romao.nhlspider.storage.GameStorage;
import com.romao.nhlspider.storage.GameSummaryStorage;
import com.romao.nhlspider.storage.LocalStorage;
import com.romao.nhlspider.storage.PlayerStorage;

import io.realm.RealmConfiguration;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class RealmLocalStorage implements LocalStorage {

    private final GameStorage gameStorage;
    private final PlayerStorage playerStorage;
    private final GameSummaryStorage gameSummaryStorage;

    public RealmLocalStorage(RealmConfiguration realmConfiguration) {
        this.gameStorage = new RealmGameStorage(realmConfiguration);
        this.playerStorage = new RealmPlayerStorage(realmConfiguration);
        this.gameSummaryStorage = new RealmGameSummaryStorage(realmConfiguration);
    }

    @Override
    public GameStorage games() {
        return gameStorage;
    }

    @Override
    public PlayerStorage players() {
        return playerStorage;
    }

    @Override
    public GameSummaryStorage gameSummary() {
        return gameSummaryStorage;
    }
}
