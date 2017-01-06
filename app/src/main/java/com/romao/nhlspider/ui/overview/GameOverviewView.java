package com.romao.nhlspider.ui.overview;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.romao.nhlspider.R;
import com.romao.nhlspider.ui.common.AbstractPresenterView;
import com.romao.nhlspider.ui.common.PresenterView;
import com.romao.nhlspider.util.DateUtil;

import org.joda.time.DateTime;

/**
 * Created by rpiontkovsky on 1/6/2017.
 */

public class GameOverviewView extends AbstractPresenterView<GamesOverviewPresenter> implements PresenterView<GamesOverviewPresenter> {

    private ViewPager viewPager;
    private GameDayAdapter adapter;
    private TextView textDate;
    private ImageButton buttonPrevious;
    private ImageButton buttonNext;

    public GameOverviewView(Context context) {
        super(context);
        init();
    }

    public GameOverviewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameOverviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        FragmentActivity activity = (FragmentActivity) getContext();

        inflate(activity, R.layout.view_games_overview, this);
        viewPager = (ViewPager) findViewById(R.id.view_pager_games);
        adapter = new GameDayAdapter(activity.getSupportFragmentManager(), DateTime.now(), DateTime.now(), 1);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                presenter.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        textDate = (TextView) findViewById(R.id.text_date);
        textDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onDateClicked();
            }
        });
        buttonNext = (ImageButton) findViewById(R.id.button_next);
        buttonNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onNextClicked();
            }
        });
        buttonPrevious = (ImageButton) findViewById(R.id.button_previous);
        buttonPrevious.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPreviousClicked();
            }
        });
    }

    public void setCurrentDate(String date) {
        textDate.setText(date);
    }

    public void setRange(DateTime start, DateTime end) {
        FragmentActivity activity = (FragmentActivity) getContext();
        int daysBetween = DateUtil.daysBetween(start, end);
        adapter = new GameDayAdapter(activity.getSupportFragmentManager(), start, end, daysBetween);
        viewPager.setAdapter(adapter);
    }

    public void setCurrentItem(int page) {
        viewPager.setCurrentItem(page);
    }

    public void setNextEnabled(boolean value) {
        buttonNext.setVisibility(value ? VISIBLE : INVISIBLE);
    }

    public void setPreviousEnabled(boolean value) {
        buttonPrevious.setVisibility(value ? VISIBLE : INVISIBLE);
    }

    public void showDatePickerDialog(int year, int month, int day) {
        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                presenter.onDatePicked(year, month, dayOfMonth);
            }
        },
                year,
                month,
                day
        ).show();
    }

    public void showErrorToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }
}
