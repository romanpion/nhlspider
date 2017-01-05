package com.romao.nhlspider.ui.overview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.romao.nhlspider.R;
import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.ui.common.AbstractPresenterView;
import com.romao.nhlspider.ui.common.OnItemClickListener;
import com.romao.nhlspider.ui.common.PresenterView;

import java.util.List;

import timber.log.Timber;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class GamesOverviewView extends AbstractPresenterView<GamesOverviewPresenter> implements PresenterView<GamesOverviewPresenter> {

    private RecyclerView listViewGames;
    private GamesAdapter adapter;

    public GamesOverviewView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_games_overview, this);

        Timber.v(new Throwable(), "init game overview");

        listViewGames = (RecyclerView) findViewById(R.id.list_view_games);
        listViewGames.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GamesAdapter(getContext(), new OnItemClickListener<Game>() {
            @Override
            public void onItemClick(View view, Game game) {
                presenter.onGameClicked(view, game);
            }
        });
        listViewGames.setAdapter(adapter);
    }

    public void setData(List<Game> data) {
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }
}
