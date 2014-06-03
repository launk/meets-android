package com.theagilemonkeys.meets.request.robospice;

import android.content.Context;
import com.octo.android.robospice.SpiceManager;
import com.theagilemonkeys.meets.MeetsDefaultConfig;
import com.theagilemonkeys.meets.models.base.MeetsFactory;

/**
 * Created by kloster on 3/06/14.
 */
public class MeetsRobospiceConfig extends MeetsDefaultConfig<MeetsRobospiceConfig> {
    private SpiceManager spiceManager = new SpiceManager(MeetsSpiceService.class);

    public MeetsRobospiceConfig(Context applicationContext, MeetsFactory factory, String hostUrl) {
        super(factory, hostUrl);
        spiceManager.start(applicationContext);
    }

    public SpiceManager getSpiceManager() {
        return spiceManager;
    }
}
