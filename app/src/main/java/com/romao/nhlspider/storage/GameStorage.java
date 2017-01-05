package com.romao.nhlspider.storage;

import com.romao.nhlspider.model.Game;

import java.util.List;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public interface GameStorage extends ObjectStorage<Game> {

    Game readById(long id);

    void upsert(Game game);

    void upsertAll(List<Game> games);
}
