package com.romao.nhlspider.ui.overview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.romao.nhlspider.R;
import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.util.DateUtil;
import com.romao.nhlspider.util.TeamImageResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {

    private final List<Game> data = new ArrayList<>();
    private final Context context;

    public GamesAdapter(final Context context) {
        this.context = context;
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
        Game game = data.get(position);
        holder.textGameDate.setText(DateUtil.toShortString(game.getDate()));
        holder.textHomeTeam.setText(game.getHomeTeam().name());
        holder.textAwayTeam.setText(game.getAwayTeam().name());
        holder.imageHomeTeam.setImageResource(TeamImageResolver.getTeamLogoResource(context, game.getHomeTeam()));
        holder.imageAwayTeam.setImageResource(TeamImageResolver.getTeamLogoResource(context, game.getAwayTeam()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textGameDate;
        private TextView textHomeTeam;
        private TextView textAwayTeam;
        private ImageView imageHomeTeam;
        private ImageView imageAwayTeam;

        public ViewHolder(View rootView) {
            super(rootView);
            textGameDate = (TextView) rootView.findViewById(R.id.text_game_date);
            textHomeTeam = (TextView) rootView.findViewById(R.id.text_home_team);
            textAwayTeam = (TextView) rootView.findViewById(R.id.text_away_team);
            imageHomeTeam = (ImageView) rootView.findViewById(R.id.image_home_team);
            imageAwayTeam = (ImageView) rootView.findViewById(R.id.image_away_team);
        }
    }
}
