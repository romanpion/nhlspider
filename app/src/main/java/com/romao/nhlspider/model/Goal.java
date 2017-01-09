package com.romao.nhlspider.model;

import com.romao.nhlspider.model.enums.Period;
import com.romao.nhlspider.model.enums.Strength;
import com.romao.nhlspider.model.enums.Team;

import io.realm.RealmObject;

/**
 * Created by rpiontkovsky on 1/3/2017.
 */

public class Goal extends RealmObject {

    private String scorer;
    private int goalNumber;
    private String primaryAssist;
    private int primaryAssistNumber;
    private String secondaryAssist;
    private int secondaryAssistNumber;
    private String team;
    private String opponent;
    private long gameId;
    private int time;
    private String period;
    private String strength;
    private boolean emptyNetter;
    private boolean penaltyShot;

    public String getScorer() {
        return scorer;
    }

    public void setScorer(String scorer) {
        this.scorer = scorer;
    }

    public int getGoalNumber() {
        return goalNumber;
    }

    public void setGoalNumber(int goalNumber) {
        this.goalNumber = goalNumber;
    }

    public String getPrimaryAssist() {
        return primaryAssist;
    }

    public void setPrimaryAssist(String primaryAssist) {
        this.primaryAssist = primaryAssist;
    }

    public int getPrimaryAssistNumber() {
        return primaryAssistNumber;
    }

    public void setPrimaryAssistNumber(int primaryAssistNumber) {
        this.primaryAssistNumber = primaryAssistNumber;
    }

    public String getSecondaryAssist() {
        return secondaryAssist;
    }

    public void setSecondaryAssist(String secondaryAssist) {
        this.secondaryAssist = secondaryAssist;
    }

    public int getSecondaryAssistNumber() {
        return secondaryAssistNumber;
    }

    public void setSecondaryAssistNumber(int secondaryAssistNumber) {
        this.secondaryAssistNumber = secondaryAssistNumber;
    }

    public Team getTeam() {
        return Team.valueOf(team);
    }

    public void setTeam(Team team) {
        this.team = team.name();
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Period getPeriod() {
        return Period.valueOf(period);
    }

    public void setPeriod(Period period) {
        this.period = period.name();
    }

    public Strength getStrength() {
        return Strength.valueOf(strength);
    }

    public void setStrength(Strength strength) {
        this.strength = strength.name();
    }

    public boolean isEmptyNetter() {
        return emptyNetter;
    }

    public void setEmptyNetter(boolean emptyNetter) {
        this.emptyNetter = emptyNetter;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public boolean isPenaltyShot() {
        return penaltyShot;
    }

    public void setPenaltyShot(boolean penaltyShot) {
        this.penaltyShot = penaltyShot;
    }
}
