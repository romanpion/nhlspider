package com.romao.nhlspider.ui.renderer;

import com.romao.nhlspider.R;
import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.model.GameSummary;
import com.romao.nhlspider.model.InProgressState;
import com.romao.nhlspider.model.enums.GameState;
import com.romao.nhlspider.model.enums.Period;
import com.romao.nhlspider.ui.view.GameCardView;
import com.romao.nhlspider.util.DateUtil;
import com.romao.nhlspider.util.TeamImageResolver;

/**
 * Created by roman on 08/01/2017.
 */

public class GameCardRenderer {

    private final GameCardView view;

    public GameCardRenderer(GameCardView gameCardView) {
        view = gameCardView;
    }

    public void applyGame(Game game) {
        if (game != null) {
            view.setGameDate(DateUtil.toShortString(game.getDate()));
            view.setHomeTeam(game.getHomeTeam().name());
            view.setAwayTeam(game.getAwayTeam().name());
            view.setGameProgressVisibility(game.getGameSummary() != null && game.getGameSummary().getGameState() == GameState.IN_PROGRESS);

            if (game.getGameSummary() != null) {
                view.setHomeTeamScore(game.getGameSummary().getHomeScore());
                view.setAwayTeamScore(game.getGameSummary().getAwayScore());

                switch (game.getGameSummary().getGameState()) {
                    case NOT_STARTED:
                        view.setGameState(null);
                        view.setGameProgressState(null);
                        break;
                    case IN_PROGRESS:
                        view.setGameState("In Progress");
                        view.setGameStateColor(R.color.textColorAlert);
                        view.setGameProgressState(getDisplayProgressState(game.getGameSummary().getInProgressState()));
                        break;
                    case FINAL:
                        String text = "FINAL";
                        text += getFinalType(game.getGameSummary());
                        view.setGameState(text);
                        view.setGameStateColor(R.color.textColorSecondary);
                        view.setGameProgressState(null);
                        break;
                }
            } else {
                view.setHomeTeamScore(-1);
                view.setAwayTeamScore(-1);
                view.setGameState(null);
            }

            view.setHomeTeamLogo(TeamImageResolver.getTeamLogoResource(game.getHomeTeam()));
            view.setAwayTeamLogo(TeamImageResolver.getTeamLogoResource(game.getAwayTeam()));
        } else {
            view.setGameDate(null);
            view.setHomeTeam(null);
            view.setAwayTeam(null);
            view.setHomeTeamLogo(0);
            view.setAwayTeamLogo(0);
            view.setHomeTeamScore(-1);
            view.setAwayTeamScore(-1);
            view.setGameState(null);
            view.setGameProgressState(null);
        }
    }

    private String getFinalType(GameSummary gameSummary) {
        switch (gameSummary.getFinalType()) {
            case OVERTIME:
                return " OT";
            case SHOOTOUT:
                return " SO";
            default:
                return "";
        }
    }

    private String getDisplayProgressState(InProgressState state) {
        if (state == null) {
            return null;
        }

        if (state.getTimeRemaining() == 0) {
            return "End of " + getPeriodDisplayName(state.getPeriod());
        }

        return getPeriodDisplayName(state.getPeriod()) + " (" + getTimeDisplayView(state.getTimeRemaining()) + " remaining)";
    }

    private String getPeriodDisplayName(Period period) {
        if (period == null) {
            return null;
        }

        switch (period) {
            case FIRST:
                return "Period 1";
            case SECOND:
                return "Period 2";
            case THIRD:
                return "Period 3";
            case OVERTIME:
                return "Overtime";
            case SHOOTOUT:
                return "Shootout";
        }

        return null;
    }

    private String getTimeDisplayView(int time) {
        int min = time / 60;
        int sec = time % 60;

        return (min < 10 ? "0" : "") + min + ":" + (sec < 10 ? "0" : "") + sec;
    }
}
