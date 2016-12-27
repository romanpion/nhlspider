package com.romao.nhlspider.storage.impl.realm;

import android.content.Context;

import com.romao.nhlspider.storage.GameStorage;
import com.romao.nhlspider.storage.LocalStorage;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class RealmLocalStorage implements LocalStorage {

    private GameStorage gameStorage;

    public RealmLocalStorage(Context context) {
        this.gameStorage = new RealmGameStorage();
    }

    @Override
    public GameStorage games() {
        return gameStorage;
    }
}
