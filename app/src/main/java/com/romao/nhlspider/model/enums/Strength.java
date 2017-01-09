package com.romao.nhlspider.model.enums;

/**
 * Created by roman on 09/01/2017.
 */

public enum Strength {

    EVEN_STRENGTH, POWER_PLAY, SHORTHANDED;

    public static Strength getByShortCode(String code) {
        if (code == null) {
            return null;
        }

        if (code.equals("EV")) {
            return EVEN_STRENGTH;
        } else if (code.equals("PP")) {
            return POWER_PLAY;
        } else if (code.equals("SH")) {
            return SHORTHANDED;
        }

        return null;
    }
}
