package com.romao.nhlspider.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.romao.nhlspider.R;
import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.model.Period;
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
    private TextView textFinalPeriod;
    private ImageView imageHomeTeam;
    private ImageView imageAwayTeam;

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
        textFinalPeriod = (TextView) findViewById(R.id.text_final_period);
    }

    public void applyGame(Game game) {
        if (game != null) {
            textGameDate.setText(DateUtil.toShortString(game.getDate()));
            textHomeTeam.setText(game.getHomeTeam().name());
            textAwayTeam.setText(game.getAwayTeam().name());

            if (game.getGameSummary() != null) {
                textHomeGoals.setText(String.valueOf(game.getGameSummary().getHomeGoals()));
                textAwayGoals.setText(String.valueOf(game.getGameSummary().getAwayGoals()));
                switch (game.getGameSummary().getFinalPeriod()) {
                    case REGULATION:
                        textFinalPeriod.setText(null);
                        break;
                    case OVERTIME:
                        textFinalPeriod.setText("OT");
                        break;
                    case SHOOTOUT:
                        textFinalPeriod.setText("SO");
                        break;
                }
            } else {
                textHomeGoals.setText(" - ");
                textAwayGoals.setText(" - ");
                textFinalPeriod.setText(null);
            }

            imageHomeTeam.setImageResource(TeamImageResolver.getTeamLogoResource(getContext(), game.getHomeTeam()));
            imageAwayTeam.setImageResource(TeamImageResolver.getTeamLogoResource(getContext(), game.getAwayTeam()));
        } else {
            textGameDate.setText(null);
            textHomeTeam.setText(null);
            textAwayTeam.setText(null);
            imageHomeTeam.setImageResource(0);
            imageAwayTeam.setImageResource(0);
            textHomeGoals.setText(null);
            textAwayGoals.setText(null);
        }

    }
}
