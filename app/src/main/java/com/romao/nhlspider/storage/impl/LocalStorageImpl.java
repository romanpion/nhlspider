package com.romao.nhlspider.storage.impl;

import com.romao.nhlspider.storage.GameStorage;
import com.romao.nhlspider.storage.LocalStorage;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class LocalStorageImpl implements LocalStorage {

    private final GameStorage gameStorage;

    public LocalStorageImpl() {
        gameStorage = new GameStorageImpl();
    }

    @Override
    public GameStorage games() {
        return gameStorage;
    }
}
