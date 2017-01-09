package com.romao.nhlspider.parser;

/**
 * Created by roman on 09/01/2017.
 */

public class GameTimeFormat {

    public static final int convertToSeconds(String timeStr) {
        String[] timeParts = timeStr.split(":");
        int min = Integer.parseInt(timeParts[0]);
        int sec = Integer.parseInt(timeParts[1]);
        return min * 60 + sec;
    }
}
