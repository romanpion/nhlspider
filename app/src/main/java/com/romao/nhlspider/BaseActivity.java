package com.romao.nhlspider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.romao.nhlspider.storage.LocalStorage;
import com.romao.nhlspider.util.ConnectionManager;
import com.romao.nhlspider.util.Route;
import com.romao.nhlspider.web.WebService;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public abstract class BaseActivity extends FragmentActivity {

    @Inject
    protected WebService webService;
    @Inject
    protected LocalStorage localStorage;
    @Inject
    protected ConnectionManager connectionManager;
    @Inject
    protected Route route;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        NhlSpiderApp.instance().appComponent().inject(this);

        Timber.v("webService : " + webService);
        Timber.v("localStorage : " + localStorage);
        Timber.v("connectionManager : " + connectionManager);
    }

    public Route getRoute() {
        return route;
    }
}
