package com.romao.nhlspider.ui.game.summary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.romao.nhlspider.R;

/**
 * Created by roman on 13/01/2017.
 */

public class GameGoalView extends FrameLayout {

    private ImageView imageTeam;
    private TextView textTime;
    private TextView textGoalScorer;
    private TextView textAssists;
    private TextView textPp;
    private TextView textSh;
    private TextView textEn;

    public GameGoalView(Context context) {
        super(context);
        init();
    }

    public GameGoalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameGoalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_game_goal, this);

        imageTeam = (ImageView) findViewById(R.id.image_team);
        textTime = (TextView) findViewById(R.id.text_goal_time);
        textGoalScorer = (TextView) findViewById(R.id.text_goal_scorer);
        textAssists = (TextView) findViewById(R.id.text_assists);
        textPp = (TextView) findViewById(R.id.text_pp);
        textSh = (TextView) findViewById(R.id.text_sh);
        textEn = (TextView) findViewById(R.id.text_en);
    }

    public void setTeamImage(int resId) {
        imageTeam.setImageResource(resId);
    }

    public void setTextTime(String time) {
        textTime.setText(time);
    }

    public void setGoalScorer(String text) {
        textGoalScorer.setText(text);
    }

    public void setAssists(String text) {
        textAssists.setText(text);
    }

    public void setIsPp(boolean flag) {
        textPp.setVisibility(flag ? VISIBLE : GONE);
    }

    public void setIsSh(boolean flag) {
        textSh.setVisibility(flag ? VISIBLE : GONE);
    }

    public void setIsEn(boolean flag) {
        textEn.setVisibility(flag ? VISIBLE : GONE);
    }
}
