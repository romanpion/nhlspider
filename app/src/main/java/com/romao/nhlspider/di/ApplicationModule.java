package com.romao.nhlspider.di;

import android.app.Application;
import android.content.Context;

import com.romao.nhlspider.storage.LocalStorage;
import com.romao.nhlspider.storage.impl.LocalStorageImpl;
import com.romao.nhlspider.storage.impl.realm.RealmLocalStorage;
import com.romao.nhlspider.util.ConnectionManager;
import com.romao.nhlspider.util.Route;
import com.romao.nhlspider.web.WebService;
import com.romao.nhlspider.web.WebServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
    WebService provideWebService() {
        return new WebServiceImpl();
    }

    @Provides
    @Singleton
    ConnectionManager provideConnectionManager(Context context) {
        return new ConnectionManager(context);
    }

    @Provides
    @Singleton
    LocalStorage provideLocalStorage(Context context) {
        return new RealmLocalStorage(context);
    }

    @Provides
    @Singleton
    Route provideRoute() {
        return new Route();
    }
}
