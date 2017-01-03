package com.romao.nhlspider.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.romao.nhlspider.ui.game.summary.GameMenuActivity;
import com.romao.nhlspider.ui.overview.GamesOverviewActivity;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class Route {

    public static final String EXTRA_GAME_ID = "game_id";

    public void toGamesActivity(Context context) {
        Intent intent = new Intent(context, GamesOverviewActivity.class);
        context.startActivity(intent);
    }

    public void toGameDetails(Activity activity, View sharedView, long gameId) {
        Intent intent = new Intent(activity, GameMenuActivity.class);
        intent.putExtra(EXTRA_GAME_ID, gameId);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(activity, sharedView, "viewGameCard");
        activity.startActivity(intent, options.toBundle());
    }
}
