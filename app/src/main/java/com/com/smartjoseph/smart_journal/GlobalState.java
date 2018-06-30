package com.com.smartjoseph.smart_journal;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * A subclass of Application for Calligraphy support.
 */
public class GlobalState extends Application {

    /**
     * Initializes Calligraphy for applying custom font found in Asset folder.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
