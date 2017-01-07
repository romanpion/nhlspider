package com.romao.nhlspider.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class ConnectionManager {

    private final ConnectivityManager connectivityManager;

    public ConnectionManager(Context applicationContext) {
        connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean isConnected() {
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}
