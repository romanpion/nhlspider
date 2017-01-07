package com.romao.nhlspider.ui.game.summary;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.romao.nhlspider.R;
import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.model.GameSummary;
import com.romao.nhlspider.ui.common.AbstractPresenterView;
import com.romao.nhlspider.ui.view.GameCardView;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class GameSummaryView extends AbstractPresenterView<GameSummaryPresenter> {

    private GameCardView viewGameCard;
    private View layoutLoading;
    private View viewGameSummary;
    private TextView textGameAttendance;
    private GameSummaryRowView viewSummaryShots;
    private GameSummaryRowView viewSummaryPowerplays;
    private GameSummaryRowView viewSummaryPpGoals;
    private GameSummaryRowView viewSummaryPenalties;
    private GameSummaryRowView viewSummaryPims;
    private GameSummaryRowView viewSummarySvPct;

    public GameSummaryView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_game_summary, this);
        viewGameCard = (GameCardView) findViewById(R.id.view_game_card);
        layoutLoading = findViewById(R.id.layout_loading);
        viewGameSummary = findViewById(R.id.view_game_summary);
        textGameAttendance = (TextView) viewGameSummary.findViewById(R.id.text_game_attendance);
        viewSummaryShots = (GameSummaryRowView) viewGameSummary.findViewById(R.id.view_category_shots);
        viewSummaryPowerplays = (GameSummaryRowView) viewGameSummary.findViewById(R.id.view_category_powerplays);
        viewSummaryPpGoals = (GameSummaryRowView) viewGameSummary.findViewById(R.id.view_category_ppgoals);
        viewSummaryPenalties = (GameSummaryRowView) viewGameSummary.findViewById(R.id.view_category_penalties);
        viewSummaryPims = (GameSummaryRowView) viewGameSummary.findViewById(R.id.view_category_pims);
        viewSummarySvPct = (GameSummaryRowView) viewGameSummary.findViewById(R.id.view_category_save_pct);
    }

    public void setGame(Game game) {
        viewGameCard.applyGame(game);

        if (game.getGameSummary() != null) {
            GameSummary gs = game.getGameSummary();

            String attendance = "Attendance %s at %s";
            String value = String.format(attendance, game.getGameSummary().getAttendance(), game.getGameSummary().getArenaName());
            textGameAttendance.setText(value);
            viewSummaryShots.setHomeText(gs.getHomeShots());
            viewSummaryShots.setAwayText(gs.getAwayShots());
            viewSummaryPowerplays.setHomeText(gs.getHomePowerPlays());
            viewSummaryPowerplays.setAwayText(gs.getAwayPowerPlays());
            viewSummaryPpGoals.setHomeText(gs.getHomePpGoals());
            viewSummaryPpGoals.setAwayText(gs.getAwayPpGoals());
            viewSummaryPenalties.setHomeText(gs.getHomePenalties());
            viewSummaryPenalties.setAwayText(gs.getAwayPenalties());
            viewSummaryPims.setHomeText(gs.getHomePims());
            viewSummaryPims.setAwayText(gs.getAwayPims());
            viewSummarySvPct.setHomeText(gs.getHomeSvPct());
            viewSummarySvPct.setAwayText(gs.getAwaySvPct());
        } else {
            textGameAttendance.setText(" - ");
        }
    }

    public void setLoading(boolean flag) {
        if (flag) {
            layoutLoading.setVisibility(VISIBLE);
            viewGameSummary.setVisibility(GONE);
        } else {
            layoutLoading.setVisibility(GONE);
            viewGameSummary.setVisibility(VISIBLE);
        }
    }
}