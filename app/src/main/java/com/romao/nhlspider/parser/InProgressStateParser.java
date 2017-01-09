package com.romao.nhlspider.parser;

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

            Period period = Period.getByNum(periodNum);

            String timeStr = str.substring(3, str.indexOf(" ", 3));
            int time = GameTimeFormat.convertToSeconds(timeStr);
            return new InProgressState(period, time);
        }

        if (str.startsWith("OT ")) {
            str = str.replace("OT ", "");
            str = str.substring(1, str.indexOf(" "));
            String timeStr = str;
            int time = GameTimeFormat.convertToSeconds(timeStr);
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
                period = Period.getByNum(periodNum);
            } else if (str.startsWith("OT")) {
                period = Period.OVERTIME;
            }

            return new InProgressState(period, 0);
        }

        throw new IllegalArgumentException("InProgressState raw format invalid : " + str);
    }

}
