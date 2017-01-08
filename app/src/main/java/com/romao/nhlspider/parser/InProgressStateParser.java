package com.romao.nhlspider.parser;

import android.support.annotation.Nullable;

import com.romao.nhlspider.model.InProgressState;
import com.romao.nhlspider.model.enums.Period;

/**
 * Created by roman on 07/01/2017.
 */

public class InProgressStateParser {

    public InProgressState parse(String str) {
        if (str == null || str.trim().length() == 0) {
            throw new IllegalArgumentException("InProgressState raw format invalid : " + str);
        }

        if (str.startsWith("Period ")) {
            str = str.replace("Period ", "");
            int periodNum = Integer.parseInt(str.substring(0, 1));

            Period period = getPeriodByNum(periodNum);

            String timeStr = str.substring(3, str.indexOf(" ", 3));
            String[] timeParts = timeStr.split(":");
            int min = Integer.parseInt(timeParts[0]);
            int sec = Integer.parseInt(timeParts[1]);
            int time = min * 60 + sec;
            return new InProgressState(period, time);
        }

        if (str.startsWith("OT ")) {
            str = str.replace("OT ", "");
            str = str.substring(1, str.indexOf(" "));
            String timeStr = str;
            String[] timeParts = timeStr.split(":");
            int min = Integer.parseInt(timeParts[0]);
            int sec = Integer.parseInt(timeParts[1]);
            int time = min * 60 + sec;
            return new InProgressState(Period.OVERTIME, time);
        }

        if (str.startsWith("Shootout")) {
            return new InProgressState(Period.SHOOTOUT, 0);
        }

        if (str.startsWith("End of ")) {
            str = str.replace("End of ", "");
            Period period = null;
            if (str.startsWith("Period ")) {
                str = str.replace("Period ", "");
                int periodNum = Integer.parseInt(str);
                period = getPeriodByNum(periodNum);
            } else if (str.startsWith("OT")) {
                period = Period.OVERTIME;
            }

            return new InProgressState(period, 0);
        }

        throw new IllegalArgumentException("InProgressState raw format invalid : " + str);
    }

    @Nullable
    private Period getPeriodByNum(int periodNum) {
        Period period = null;
        switch (periodNum) {
            case 1:
                period = Period.FIRST;
                break;
            case 2:
                period = Period.SECOND;
                break;
            case 3:
                period = Period.THIRD;
                break;
        }
        return period;
    }
}
