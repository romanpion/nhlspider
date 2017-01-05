package com.romao.nhlspider.model;

import org.joda.time.DateTime;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class Game extends RealmObject {

    @PrimaryKey
    private long gameId;

    private String homeTeam;
    private String awayTeam;
    private String date;

    private GameSummary gameSummary;

    public Game() {
    }

    private Game(long gameId, Team home, Team away, DateTime date) {
        this.gameId = gameId;
        this.homeTeam = home.name();
        this.awayTeam = away.name();
        this.date = date.toString();
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public Team getHomeTeam() {
        return Team.valueOf(homeTeam);
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam.name();
    }

    public Team getAwayTeam() {
        return Team.valueOf(awayTeam);
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam.name();
    }

    public DateTime getDate() {
        return DateTime.parse(date);
    }

    public void setDate(DateTime date) {
        this.date = date.toString();
    }

    public GameSummary getGameSummary() {
        return gameSummary;
    }

    public void setGameSummary(GameSummary gameSummary) {
        this.gameSummary = gameSummary;
    }

    public List<Goal> getGoals() {
        return gameSummary != null ? gameSummary.getGoals() : null;
    }

    public static class GameBuilder {
        private long id;
        private Team homeTeam;
        private Team awayTeam;
        private DateTime date;

        public GameBuilder addGameId(long gameId) {
            this.id = gameId;
            return this;
        }

        public GameBuilder addHomeTeam(Team team) {
            this.homeTeam = team;
            return this;
        }

        public GameBuilder addAwayTeam(Team team) {
            this.awayTeam = team;
            return this;
        }

        public GameBuilder addDate(DateTime date) {
            this.date = date;
            return this;
        }

        public Game build() {
            if (this.id == 0) {
                throw new IllegalArgumentException("Game ID not specified");
            }

            if (this.homeTeam == null) {
                throw new IllegalArgumentException("Home team not specified");
            }

            if (this.awayTeam == null) {
                throw new IllegalArgumentException("Away team not specified");
            }

            if (this.date == null) {
                throw new IllegalArgumentException("Date not specified");
            }

            return new Game(this.id, this.homeTeam, this.awayTeam, this.date);
        }
    }
}
