package com.romao.nhlspider.model;

import com.romao.nhlspider.model.enums.Period;

/**
 * Created by roman on 07/01/2017.
 */

public class InProgressState {

    private final Period period;
    private final int timeRemaining;

    public InProgressState(Period period, int timeRemaining) {
        this.period = period;
        this.timeRemaining = timeRemaining;
    }

    public Period getPeriod() {
        return period;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    @Override
    public String toString() {
        return period.name() + ";" + timeRemaining;
    }

    public static InProgressState fromString(String str) {
        if (str == null || str.indexOf(";") < 0 || str.split(";").length != 2) {
            throw new IllegalArgumentException("InProgressState format is invalid");
        }

        String[] parts = str.split(";");
        Period period = Period.valueOf(parts[0]);
        int timeRemaining = Integer.parseInt(parts[1]);
        return new InProgressState(period, timeRemaining);
    }
}
