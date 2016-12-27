package com.romao.nhlspider.model.util;

import com.romao.nhlspider.model.Game;

import java.util.Comparator;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class GameComparator implements Comparator<Game> {

    @Override
    public int compare(Game o1, Game o2) {
        if (o1.getDate() == null && o2.getDate() == null) {

        }

        if (o1.getDate() != null && o2.getDate() == null) {
            return -1;
        }

        if (o1.getDate() == null && o2.getDate() != null) {
            return 1;
        }

        return o1.getDate().compareTo(o2.getDate());
    }
}
