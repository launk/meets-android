package com.theagilemonkeys.meets.meetsandroid;

import android.app.Application;
import android.util.Log;

import com.octo.android.robospice.Jackson2SpringAndroidSpiceService;
import roboguice.util.temp.Ln;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class MeetsSpiceService extends Jackson2SpringAndroidSpiceService {
    @Override
    public void onCreate() {
        super.onCreate();

        // Logging really causes the app to chug with this many requests
        Ln.getConfig().setLoggingLevel(Log.ERROR);
    }

    @Override
    public int getThreadCount() {
        return 8;
    }
}
