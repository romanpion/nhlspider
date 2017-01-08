package com.romao.nhlspider.ui.overview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romao.nhlspider.R;
import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.ui.common.OnItemClickListener;
import com.romao.nhlspider.ui.renderer.GameCardRenderer;
import com.romao.nhlspider.ui.view.GameCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {

    private final List<Game> data = new ArrayList<>();
    private final OnItemClickListener<Game> listener;

    public GamesAdapter(final OnItemClickListener<Game> listener) {
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
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Game game = data.get(position);
        GameCardRenderer renderer = (GameCardRenderer) holder.cardView.getTag();
        if (renderer == null) {
            renderer = new GameCardRenderer(holder.cardView);
        }
        renderer.applyGame(game);
        holder.viewContent.setBackgroundResource(game.getGameSummary() != null ? R.drawable.bg_clickable : R.drawable.bg_clickable_solid);
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

        private final GameCardView cardView;
        private final View viewContent;

        public ViewHolder(View rootView) {
            super(rootView);
            cardView = (GameCardView) rootView;
            viewContent = cardView.findViewById(R.id.layout_game_clickable);
        }
    }
}
