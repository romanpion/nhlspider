package com.romao.nhlspider.storage.impl.realm;

import com.romao.nhlspider.model.Player;
import com.romao.nhlspider.model.Team;
import com.romao.nhlspider.storage.PlayerStorage;

import io.realm.RealmConfiguration;

/**
 * Created by rpiontkovsky on 1/3/2017.
 */

public class RealmPlayerStorage extends RealmObjectStorage<Player> implements PlayerStorage {

    private static final String FIELD_FULL_NAME = "fullName";
    private static final String FIELD_TEAM = "team";

    public RealmPlayerStorage(RealmConfiguration realmConfiguration) {
        super(realmConfiguration, Player.class);
    }

    @Override
    public Player findByName(String name) {
        return copy(prepareQuery().equalTo(FIELD_FULL_NAME, name).findFirst());
    }

    @Override
    public Player find(String name, Team team) {
        return copy(prepareQuery().equalTo(FIELD_FULL_NAME, name).equalTo(FIELD_TEAM, team.name()).findFirst());
    }
}
