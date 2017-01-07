package com.romao.nhlspider.model;

import com.romao.nhlspider.model.enums.GameFinal;
import com.romao.nhlspider.model.enums.GameState;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rpiontkovsky on 1/3/2017.
 */

public class GameSummary extends RealmObject {

    @PrimaryKey
    private long gameId;

    private int homeScore;
    private int awayScore;
    private int homeGoals;
    private int awayGoals;
    private int homeShots;
    private int awayShots;
    private int homePenalties;
    private int awayPenalties;
    private int homePims;
    private int awayPims;
    private int homePowerPlays;
    private int awayPowerPlays;
    private int homePpGoals;
    private int awayPpGoals;
    private String finalType;
    private String gameState;
    private String inProgressState;

    private int attendance;
    private int gameLength;
    private String arenaName;
    private RealmList<Goal> goals;

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getGameLength() {
        return gameLength;
    }

    public void setGameLength(int gameLength) {
        this.gameLength = gameLength;
    }

    public RealmList<Goal> getGoals() {
        return goals;
    }

    public void setGoals(RealmList<Goal> goals) {
        this.goals = goals;
    }

    public String getArenaName() {
        return arenaName;
    }

    public void setArenaName(String arenaName) {
        this.arenaName = arenaName;
    }

    public int getHomeShots() {
        return homeShots;
    }

    public void setHomeShots(int homeShots) {
        this.homeShots = homeShots;
    }

    public int getAwayShots() {
        return awayShots;
    }

    public void setAwayShots(int awayShots) {
        this.awayShots = awayShots;
    }

    public int getHomePenalties() {
        return homePenalties;
    }

    public void setHomePenalties(int homePenalties) {
        this.homePenalties = homePenalties;
    }

    public int getAwayPenalties() {
        return awayPenalties;
    }

    public void setAwayPenalties(int awayPenalties) {
        this.awayPenalties = awayPenalties;
    }

    public int getHomePims() {
        return homePims;
    }

    public void setHomePims(int homePims) {
        this.homePims = homePims;
    }

    public int getAwayPims() {
        return awayPims;
    }

    public void setAwayPims(int awayPims) {
        this.awayPims = awayPims;
    }

    public double getHomeSvPct() {
        return Math.round(1000.0 * (awayShots - awayGoals) / awayShots) / 10.0;
    }

    public double getAwaySvPct() {
        return Math.round(1000.0 * (homeShots - homeGoals) / homeShots) / 10.0;
    }

    public int getHomePowerPlays() {
        return homePowerPlays;
    }

    public void setHomePowerPlays(int homePowerPlays) {
        this.homePowerPlays = homePowerPlays;
    }

    public int getAwayPowerPlays() {
        return awayPowerPlays;
    }

    public void setAwayPowerPlays(int awayPowerPlays) {
        this.awayPowerPlays = awayPowerPlays;
    }

    public int getHomePpGoals() {
        return homePpGoals;
    }

    public void setHomePpGoals(int homePpGoals) {
        this.homePpGoals = homePpGoals;
    }

    public int getAwayPpGoals() {
        return awayPpGoals;
    }

    public void setAwayPpGoals(int awayPpGoals) {
        this.awayPpGoals = awayPpGoals;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public GameFinal getFinalType() {
        return GameFinal.valueOf(finalType);
    }

    public void setFinalType(GameFinal gameFinal) {
        this.finalType = gameFinal.name();
    }

    public GameState getGameState() {
        return GameState.valueOf(gameState);
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState.name();
    }

    public InProgressState getInProgressState() {
        return InProgressState.fromString(inProgressState);
    }

    public void setInProgressState(InProgressState inProgressState) {
        this.inProgressState = inProgressState.toString();
    }
}
