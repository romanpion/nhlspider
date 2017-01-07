package com.romao.nhlspider.model;

import com.romao.nhlspider.model.enums.Team;

import io.realm.RealmObject;

/**
 * Created by rpiontkovsky on 1/3/2017.
 */

public class Player extends RealmObject {

    private String fullName;
    private String shortName;
    private int number;
    private String team;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Team getTeam() {
        return Team.valueOf(team);
    }

    public void setTeam(Team team) {
        this.team = team.name();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
