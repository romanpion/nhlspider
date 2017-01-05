package com.romao.nhlspider.storage;

import com.romao.nhlspider.model.GameSummary;

/**
 * Created by rpiontkovsky on 1/3/2017.
 */

public interface GameSummaryStorage extends ObjectStorage<GameSummary> {

    GameSummary readByGameId(long gameId);

    void upsert(final GameSummary gameSummary);
}
