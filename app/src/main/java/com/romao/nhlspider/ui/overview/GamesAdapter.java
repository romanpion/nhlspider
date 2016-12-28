package com.romao.nhlspider.ui.overview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romao.nhlspider.R;
import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.ui.common.OnItemClickListener;
import com.romao.nhlspider.ui.view.GameCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {

    private final List<Game> data = new ArrayList<>();
    private final Context context;
    private final OnItemClickListener<Game> listener;

    public GamesAdapter(final Context context, final OnItemClickListener<Game> listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setData(List<Game> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_game_item, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Game game = data.get(position);
        holder.cardView.applyGame(game);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, game);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private GameCardView cardView;

        public ViewHolder(View rootView) {
            super(rootView);
            cardView = (GameCardView) rootView;
        }
    }
}
