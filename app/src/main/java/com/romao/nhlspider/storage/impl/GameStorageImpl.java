package com.romao.nhlspider.storage.impl;

import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.storage.GameStorage;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class GameStorageImpl implements GameStorage {

    @Override
    public List<Game> readAll() {
        return null;
    }

    @Override
    public Game readById(long id) {
        return null;
    }

    @Override
    public void upsertAll(List<Game> games) {

    }

    @Override
    public void upsert(Game game) {

    }

    @Override
    public List<Game> readByDate(DateTime dateTime) {
        return null;
    }
}
