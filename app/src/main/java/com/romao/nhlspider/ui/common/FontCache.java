package com.romao.nhlspider.ui.common;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

import timber.log.Timber;

public class FontCache {
    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static synchronized Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if (tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), name);
            } catch (Exception e) {
                Timber.e(e, "Failed to create font from asset: " + name);
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }
}
