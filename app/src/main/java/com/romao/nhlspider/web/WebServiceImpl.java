package com.romao.nhlspider.web;

import com.romao.nhlspider.model.Game;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class WebServiceImpl implements WebService {

    private final RetrofitWebService retrofitWebService;

    public WebServiceImpl(OkHttpClient httpClient) {
        //Converter.Factory converterFactory = SimpleXmlConverterFactory.create();
        Converter.Factory converterFactory = ScalarsConverterFactory.create();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(converterFactory)
                .baseUrl("http://www.nhl.com")
                .client(httpClient)
                .build();

        retrofitWebService = retrofit.create(RetrofitWebService.class);
    }

    @Override
    public Observable<String> getGameSummary(Game game) {
        String gameNumber = String.valueOf(game.getGameId()).substring(4);
        return retrofitWebService.getGameSummary(gameNumber);
    }
}
