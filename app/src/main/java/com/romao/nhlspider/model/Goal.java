package com.romao.nhlspider.model;

import com.romao.nhlspider.model.enums.Team;

import io.realm.RealmObject;

/**
 * Created by rpiontkovsky on 1/3/2017.
 */

public class Goal extends RealmObject {

    private Player scorer;
    private Player primaryAssist;
    private Player secondaryAssist;
    private String team;
    private long gameId;
    private int time;

    public Player getScorer() {
        return scorer;
    }

    public void setScorer(Player scorer) {
        this.scorer = scorer;
    }

    public Player getPrimaryAssist() {
        return primaryAssist;
    }

    public void setPrimaryAssist(Player primaryAssist) {
        this.primaryAssist = primaryAssist;
    }

    public Player getSecondaryAssist() {
        return secondaryAssist;
    }

    public void setSecondaryAssist(Player secondaryAssist) {
        this.secondaryAssist = secondaryAssist;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Team getTeam() {
        return Team.valueOf(team);
    }

    public void setTeam(Team team) {
        this.team = team.name();
    }
}
