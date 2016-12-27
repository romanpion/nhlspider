package com.romao.nhlspider.util;

import android.content.Context;

import com.romao.nhlspider.R;
import com.romao.nhlspider.model.Team;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class TeamImageResolver {

    public static int getTeamLogoResource(Context context, Team team) {
        switch (team) {
            case ANA:
                return R.mipmap.ana;
            case ARI:
                return R.mipmap.ari;
            case BOS:
                return R.mipmap.bos;
            case BUF:
                return R.mipmap.buf;
            case CAR:
                return R.mipmap.car;
            case CBJ:
                return R.mipmap.cbj;
            case CGY:
                return R.mipmap.cgy;
            case CHI:
                return R.mipmap.chi;
            case COL:
                return R.mipmap.col;
            case DAL:
                return R.mipmap.dal;
            case DET:
                return R.mipmap.det;
            case EDM:
                return R.mipmap.edm;
            case FLA:
                return R.mipmap.fla;
            case LAK:
                return R.mipmap.lak;
            case MIN:
                return R.mipmap.min;
            case MTL:
                return R.mipmap.mtl;
            case NJD:
                return R.mipmap.njd;
            case NSH:
                return R.mipmap.nsh;
            case NYI:
                return R.mipmap.nyi;
            case NYR:
                return R.mipmap.nyr;
            case OTT:
                return R.mipmap.ott;
            case PHI:
                return R.mipmap.phi;
            case PIT:
                return R.mipmap.pit;
            case SJS:
                return R.mipmap.sjs;
            case STL:
                return R.mipmap.stl;
            case TBL:
                return R.mipmap.tbl;
            case TOR:
                return R.mipmap.tor;
            case VAN:
                return R.mipmap.van;
            case WPG:
                return R.mipmap.wpg;
            case WSH:
                return R.mipmap.wsh;
            default:
                return 0;
        }
    }
}
