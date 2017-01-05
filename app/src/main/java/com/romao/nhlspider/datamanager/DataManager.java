package com.romao.nhlspider.datamanager;

import com.romao.nhlspider.util.OkResult;

import rx.Observable;

/**
 * Created by rpiontkovsky on 1/3/2017.
 */

public interface DataManager {

    Observable<OkResult> getGame(long gameId);
}
