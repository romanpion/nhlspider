package com.romao.nhlspider;

import android.app.Application;
import android.util.Log;

import com.romao.nhlspider.di.ApplicationComponent;
import com.romao.nhlspider.di.ApplicationModule;
import com.romao.nhlspider.di.DaggerApplicationComponent;

import io.realm.Realm;
import timber.log.Timber;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class NhlSpiderApp extends Application {

    private static NhlSpiderApp instance;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        initLogger();
        Timber.v("nhl on app create");
        super.onCreate();
        instance = this;

        initDatabase();
        initDI();
    }

    private void initDatabase() {
        long start = System.currentTimeMillis();
        Realm.init(this);
        long finish = System.currentTimeMillis();
        Timber.v("nhl initDatabase : " + (finish - start) + " ms");
    }

    private void initLogger() {
        // init logger
        Timber.plant(new Timber.DebugTree());
    }

    private void initDI() {
        long start = System.currentTimeMillis();
        this.applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        long finish = System.currentTimeMillis();
        Timber.v("nhl initDI : " + (finish - start) + " ms");
    }

    ApplicationComponent appComponent() {
        return applicationComponent;
    }

    public static NhlSpiderApp instance() {
        return instance;
    }
}
