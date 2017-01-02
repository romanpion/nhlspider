package com.romao.nhlspider.web;

import com.romao.nhlspider.model.Game;

import org.jdom2.Document;

import rx.Observable;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

// http://www.nhl.com/scores/htmlreports/20162017/GS020507.HTM

public interface WebService {

    Observable<Document> getGameSummary(Game game);
}
