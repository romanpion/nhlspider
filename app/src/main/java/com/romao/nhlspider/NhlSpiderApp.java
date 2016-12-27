package com.romao.nhlspider;

import android.app.Application;

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
        super.onCreate();
        instance = this;

        initDatabase();
        initLogger();
        initDI();
    }

    private void initDatabase() {
        Realm.init(this);
    }

    private void initLogger() {
        // init logger
        Timber.plant(new Timber.DebugTree());
    }

    private void initDI() {
        this.applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    ApplicationComponent appComponent() {
        return applicationComponent;
    }

    public static NhlSpiderApp instance() {
        return instance;
    }
}
