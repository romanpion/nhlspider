package com.romao.nhlspider.web;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by rpiontkovsky on 12/28/2016.
 */

public interface RetrofitWebService {

    @GET("/scores/htmlreports/20162017/GS{game_number}.HTM")
    Observable<String> getGameSummary(@Path("game_number") String gameNumber);
}
