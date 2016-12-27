package com.romao.nhlspider.util;

import android.content.Context;
import android.content.Intent;

import com.romao.nhlspider.ui.overview.GamesOverviewActivity;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class Route {

    public void toGamesActivity(Context context) {
        Intent intent = new Intent(context, GamesOverviewActivity.class);
        context.startActivity(intent);
    }
}
