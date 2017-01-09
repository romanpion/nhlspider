package com.romao.nhlspider.model.enums;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public enum Team {

    ANA,
    BUF,
    BOS,
    ARI,
    CAR,
    CGY,
    CHI,
    COL,
    CBJ,
    DAL,
    DET,
    EDM,
    FLA,
    LAK,
    MIN,
    MTL,
    NYI,
    NYR,
    NJD,
    NSH,
    OTT,
    PIT,
    PHI,
    STL,
    SJS,
    TBL,
    TOR,
    VAN,
    WPG,
    WSH;

    public static Team getByShortName(String name) {
        if (name == null) {
            return null;
        }

        if (name.indexOf('.') < 0) {
            return Team.valueOf(name);
        }

        if (name.equals("T.B")) {
            return TBL;
        } else if (name.equals("S.J")) {
            return SJS;
        } else if (name.equals("N.J")) {
            return NJD;
        } else if (name.equals("L.A")) {
            return LAK;
        }

        return null;
    }

}
