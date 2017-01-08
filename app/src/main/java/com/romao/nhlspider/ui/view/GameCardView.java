package com.romao.nhlspider.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.romao.nhlspider.R;
import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.model.GameSummary;
import com.romao.nhlspider.util.DateUtil;
import com.romao.nhlspider.util.TeamImageResolver;

/**
 * Created by rpiontkovsky on 12/28/2016.
 */

public class GameCardView extends FrameLayout {

    private TextView textGameDate;
    private TextView textHomeTeam;
    private TextView textAwayTeam;
    private TextView textHomeGoals;
    private TextView textAwayGoals;
    private TextView textGameState;
    private TextView textGamePeriod;
    private TextView textTimeRemaining;
    private ImageView imageHomeTeam;
    private ImageView imageAwayTeam;
    private View viewGameProgress;

    public GameCardView(Context context) {
        super(context);
        init();
    }

    public GameCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_game_card, this);

        textGameDate = (TextView) findViewById(R.id.text_game_date);
        textHomeTeam = (TextView) findViewById(R.id.text_home_team);
        textAwayTeam = (TextView) findViewById(R.id.text_away_team);
        textHomeGoals = (TextView) findViewById(R.id.text_home_goals);
        textAwayGoals = (TextView) findViewById(R.id.text_away_goals);
        imageHomeTeam = (ImageView) findViewById(R.id.image_home_team);
        imageAwayTeam = (ImageView) findViewById(R.id.image_away_team);
        textGameState = (TextView) findViewById(R.id.text_game_state);
        textGamePeriod = (TextView) findViewById(R.id.text_game_period);
        textTimeRemaining = (TextView) findViewById(R.id.text_time_remaining);
        viewGameProgress = findViewById(R.id.layout_game_progress);
    }

    public void setGameDate(String date) {
        textGameDate.setText(date);
    }

    public void setHomeTeam(String value) {
        textHomeTeam.setText(value);
    }

    public void setHomeTeamLogo(int logoResId) {
        imageHomeTeam.setImageResource(logoResId);
    }

    public void setHomeTeamScore(String value) {
        textHomeGoals.setText(value);
    }

    public void setHomeTeamScore(int value) {
        textHomeGoals.setText(String.valueOf(value));
    }

    public void setAwayTeam(String value) {
        textAwayTeam.setText(value);
    }

    public void setAwayTeamScore(String value) {
        textAwayGoals.setText(value);
    }

    public void setAwayTeamLogo(int logoResId) {
        imageAwayTeam.setImageResource(logoResId);
    }

    public void setAwayTeamScore(int value) {
        if (value < 0) {
            textAwayGoals.setText(R.string.i18n_score_placeholder);
        } else {
            textAwayGoals.setText(String.valueOf(value));
        }
    }

    public void setGameState(String value) {
        textGameState.setText(value);
    }

    public void setCardBackground(int resId) {
        setBackgroundResource(resId);
    }

    public void setGamePeriod(String value) {
        textGamePeriod.setText(value);
    }

    public void setTimeRemaining(String value) {
        textTimeRemaining.setText(value);
    }

    public void setGameProgressVisibility(boolean visible) {
        viewGameProgress.setVisibility(visible ? VISIBLE : GONE);
    }

    public void setGameStateVisibility(boolean visible) {
        textGameState.setVisibility(visible ? VISIBLE : GONE);
    }

}
