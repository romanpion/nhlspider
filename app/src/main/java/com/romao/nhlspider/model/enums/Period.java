package com.romao.nhlspider.model.enums;

import android.support.annotation.Nullable;

/**
 * Created by roman on 08/01/2017.
 */

public enum Period {

    FIRST, SECOND, THIRD, OVERTIME, SHOOTOUT;

    @Nullable
    public static final Period getByNum(int periodNum) {
        switch (periodNum) {
            case 1:
                return Period.FIRST;
            case 2:
                return Period.SECOND;
            case 3:
                return Period.THIRD;
            case 4:
                return Period.OVERTIME;
            case 5:
                return Period.SHOOTOUT;
        }

        return null;
    }

    @Nullable
    public static final Period getByStringCode(String code) {
        if (code == null) {
            return null;
        }

        if (code.equals("1")) {
            return Period.FIRST;
        } else if (code.equals("2")) {
            return Period.SECOND;
        } else if (code.equals("3")) {
            return Period.THIRD;
        } else if (code.equals("OT")) {
            return Period.OVERTIME;
        } else if (code.equals("SO")) {
            return Period.SHOOTOUT;
        }

        return null;
    }
}
