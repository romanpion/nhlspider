package com.romao.nhlspider.web;

import com.romao.nhlspider.model.Game;

import rx.Observable;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

// http://www.nhl.com/scores/htmlreports/20162017/GS020507.HTM

public interface WebService {

    Observable<String> getGameSummary(Game game);
}
