package com.romao.nhlspider.web;

import com.romao.nhlspider.model.Game;
import com.romao.nhlspider.parser.HtmlTextHandler;

import org.jdom2.Document;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.functions.Func1;

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
    public Observable<Document> getGameSummary(Game game) {
        String gameNumber = String.valueOf(game.getGameId()).substring(4);
        return retrofitWebService.getGameSummary(gameNumber).flatMap(new Func1<String, Observable<Document>>() {
            @Override
            public Observable<Document> call(String s) {
                return Observable.just(HtmlTextHandler.convertToDocument(s));
            }
        });
    }
}
