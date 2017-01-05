package com.romao.nhlspider.storage.impl;

import com.romao.nhlspider.storage.GameStorage;
import com.romao.nhlspider.storage.GameSummaryStorage;
import com.romao.nhlspider.storage.LocalStorage;
import com.romao.nhlspider.storage.PlayerStorage;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class LocalStorageImpl implements LocalStorage {

    private final GameStorage gameStorage;
    private final PlayerStorage playerStorage;
    private final GameSummaryStorage gameSummaryStorage;

    public LocalStorageImpl() {
        gameStorage = new GameStorageImpl();
        playerStorage = new PlayerStorageImpl();
        gameSummaryStorage = new GameSummaryStorageImpl();
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
