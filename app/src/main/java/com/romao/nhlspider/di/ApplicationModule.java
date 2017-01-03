package com.romao.nhlspider.di;

import android.app.Application;
import android.content.Context;

import com.romao.nhlspider.datamanager.DataManager;
import com.romao.nhlspider.datamanager.DataManagerImpl;
import com.romao.nhlspider.storage.LocalStorage;
import com.romao.nhlspider.storage.impl.realm.RealmLocalStorage;
import com.romao.nhlspider.util.ConnectionManager;
import com.romao.nhlspider.util.Route;
import com.romao.nhlspider.web.WebService;
import com.romao.nhlspider.web.WebServiceImpl;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

@Module
public class ApplicationModule {

    private Application app;

    public ApplicationModule(Application app) {
        this.app = app;
    }

    @Provides
    Context provideContext() {
        return this.app;
    }

    @Provides
    @Singleton
    ConnectionManager provideConnectionManager(Context context) {
        return new ConnectionManager(context);
    }

    @Provides
    @Singleton
    LocalStorage provideLocalStorage(RealmConfiguration realmConfiguration) {
        return new RealmLocalStorage(realmConfiguration);
    }

    @Provides
    @Singleton
    RealmConfiguration provideRealmConfiguration() {
        return new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    @Provides
    @Singleton
    DataManager provideDataManager(LocalStorage storage, WebService webService) {
        return new DataManagerImpl(storage, webService);
    }

    @Provides
    @Singleton
    Route provideRoute() {
        return new Route();
    }

    @Provides
    @Singleton
    public OkHttpClient provideHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor);

        return builder.build();
    }

    @Provides
    public WebService provideWebService(OkHttpClient client) {
        return new WebServiceImpl(client);
    }
}
