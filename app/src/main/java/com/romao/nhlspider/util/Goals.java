package com.romao.nhlspider.util;

import com.romao.nhlspider.model.Goal;
import com.romao.nhlspider.model.enums.Period;

/**
 * Created by roman on 14/01/2017.
 */

public class Goals {

    public static String getGoalScorerString(Goal goal) {
        String result = goal.getScorer();
        if (goal.getPeriod() != Period.SHOOTOUT) {
            result += " (" + goal.getGoalNumber() + ")";
        }
        return WordUtils.capitalizeString(result);
    }

    public static String getAssistsString(Goal goal) {
        if (goal.getPrimaryAssist() == null) {
            return "Unassisted";
        }
        String result = goal.getPrimaryAssist() + " (" + goal.getPrimaryAssistNumber() + ")";
        if (goal.getSecondaryAssist() != null) {
            result += ", ";
            result += goal.getSecondaryAssist() + " (" + goal.getSecondaryAssistNumber() + ")";
        }

        return WordUtils.capitalizeString(result);
    }

    public static String getGoalTime(Goal goal) {
        int periodMinute = goal.getTime() / 60;
        int absMinute = goal.getPeriod().ordinal() * 20 + periodMinute;
        int sec = goal.getTime() % 60;

        String minStr = absMinute < 10 ? "0" + absMinute : String.valueOf(absMinute);
        String secString = sec < 10 ? "0" + sec : String.valueOf(sec);
        return minStr + ":" + secString;
    }
}
