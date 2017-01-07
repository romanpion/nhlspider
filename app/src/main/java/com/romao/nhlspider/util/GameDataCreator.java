package com.romao.nhlspider.util;

import android.content.Context;

import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.model.enums.Team;

import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class GameDataCreator {

    private final Context context;
    private final String source;

    public GameDataCreator(Context context, String source) {
        this.source = source;
        this.context = context;
    }

    public List<Game> getGameList() {
        List<Game> result = new ArrayList<>(30);

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(source), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                String[] parts = mLine.split(";");

                Game.GameBuilder builder = new Game.GameBuilder();
                Game game = builder.addGameId(Long.parseLong(parts[0]))
                        .addHomeTeam(Team.valueOf(parts[2]))
                        .addAwayTeam(Team.valueOf(parts[3]))
                        .addDate(DateTime.parse(parts[1]).withZone(DateUtil.getDefaultTimeZone()))
                        .build();
                result.add(game);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        return result;
    }
}
