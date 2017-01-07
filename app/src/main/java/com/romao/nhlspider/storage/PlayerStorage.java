package com.romao.nhlspider.storage;

import com.romao.nhlspider.model.Player;
import com.romao.nhlspider.model.enums.Team;

/**
 * Created by rpiontkovsky on 1/3/2017.
 */

public interface PlayerStorage extends ObjectStorage<Player> {

    Player findByName(String name);

    Player find(String name, Team team);
}
