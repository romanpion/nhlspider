package com.romao.nhlspider.ui.game.summary;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.romao.nhlspider.R;
import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.ui.common.AbstractPresenterView;
import com.romao.nhlspider.ui.view.GameCardView;

import timber.log.Timber;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class GameMenuView extends AbstractPresenterView<GameMenuPresenter> {

    private GameCardView viewGameCard;
    private View layoutLoading;
    private View viewGameSummary;
    private TextView textHomeGoals;
    private TextView textAwayGoals;
    private TextView textGameAttendance;

    public GameMenuView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_game_summary, this);
        viewGameCard = (GameCardView) findViewById(R.id.view_game_card);
        layoutLoading = findViewById(R.id.layout_loading);
        viewGameSummary = findViewById(R.id.view_game_summary);
        textHomeGoals = (TextView) viewGameCard.findViewById(R.id.text_home_goals);
        textAwayGoals = (TextView) viewGameCard.findViewById(R.id.text_away_goals);
        textGameAttendance = (TextView) viewGameSummary.findViewById(R.id.text_game_attendance);
    }

    public void setGame(Game game) {
        viewGameCard.applyGame(game);

        if (game.getGameSummary() != null) {
            String attendance = "Attendance %s at %s";
            String value = String.format(attendance, game.getGameSummary().getAttendance(), game.getGameSummary().getArenaName());
            textGameAttendance.setText(value);
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
