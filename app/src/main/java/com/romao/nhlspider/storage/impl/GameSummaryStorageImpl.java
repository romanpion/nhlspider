package com.romao.nhlspider.storage.impl;

import com.romao.nhlspider.model.GameSummary;
import com.romao.nhlspider.storage.GameSummaryStorage;

import java.util.List;

/**
 * Created by rpiontkovsky on 1/3/2017.
 */

public class GameSummaryStorageImpl implements GameSummaryStorage {

    @Override
    public GameSummary readByGameId(long gameId) {
        return null;
    }

    @Override
    public List<GameSummary> readAll() {
        return null;
    }

    @Override
    public void upsert(GameSummary gameSummary) {

    }
}
