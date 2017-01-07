package com.romao.nhlspider.ui.overview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romao.nhlspider.BaseActivity;
import com.romao.nhlspider.util.DateUtil;

import org.joda.time.DateTime;

/**
 * Created by rpiontkovsky on 1/6/2017.
 */

public class GameDayFragment extends Fragment {

    public static GameDayFragment newInstance(DateTime date) {
        GameDayFragment fragment = new GameDayFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("date", date.getMillis());
        fragment.setArguments(bundle);
        return fragment;
    }


    private GameDayPresenter presenter;
    private GameDayView view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DateTime dateTime = DateUtil.toStartOfDay(new DateTime(getArguments().getLong("date")));

        BaseActivity activity = (BaseActivity) getActivity();
        presenter = new GameDayPresenter(activity, activity.storage(), dateTime);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = new GameDayView(getContext());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.attach(view);
        view.setPresenter(presenter);
    }

    @Override
    public void onStop() {
        presenter.detach(view);
        view.setPresenter(null);

        super.onStop();
    }
}
