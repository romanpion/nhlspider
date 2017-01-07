package com.romao.nhlspider.ui.game.summary;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.romao.nhlspider.R;

/**
 * Created by roman on 03/01/2017.
 */

public class GameSummaryRowView extends FrameLayout {

    private TextView textHome;
    private TextView textAway;
    private TextView textCategory;

    public GameSummaryRowView(Context context) {
        super(context);
        init(null);
    }

    public GameSummaryRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GameSummaryRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.view_game_summary_row, this);

        textHome = (TextView) findViewById(R.id.text_home);
        textAway = (TextView) findViewById(R.id.text_away);
        textCategory = (TextView) findViewById(R.id.text_category);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.GameSummaryRowView);
        if (a != null) {
            if (a.getString(R.styleable.GameSummaryRowView_category) != null) {
                setCategory(a.getString(R.styleable.GameSummaryRowView_category));
            }
        }
        a.recycle();
    }

    public void setCategory(CharSequence str) {
        textCategory.setText(str);
    }

    public void setHomeText(CharSequence str) {
        textHome.setText(str);
    }

    public void setAwayText(CharSequence str) {
        textAway.setText(str);
    }

    public void setHomeText(int value) {
        textHome.setText(String.valueOf(value));
    }

    public void setAwayText(int value) {
        textAway.setText(String.valueOf(value));
    }

    public void setHomeText(double value) {
        textHome.setText(String.valueOf(value));
    }

    public void setAwayText(double value) {
        textAway.setText(String.valueOf(value));
    }
}
