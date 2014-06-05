package com.theagilemonkeys.meets.meetsandroid;

import android.content.Context;
import com.octo.android.robospice.SpiceManager;
import com.theagilemonkeys.meets.ApiMethod;
import com.theagilemonkeys.meets.MeetsDefaultConfig;
import com.theagilemonkeys.meets.models.base.MeetsFactory;
import com.theagilemonkeys.meets.request.RequestWrapper;

/**
 * Created by kloster on 3/06/14.
 */
public class MeetsRobospiceConfig extends MeetsDefaultConfig<MeetsRobospiceConfig> {

    private SpiceManager spiceManager = new SpiceManager(MeetsSpiceService.class);

    public MeetsRobospiceConfig(Context applicationContext, MeetsFactory factory, String hostUrl) {
        super(factory, hostUrl);
        spiceManager.start(applicationContext);
    }

    @Override
    public <RESULT> RequestWrapper<RESULT> getRequestWrapper(ApiMethod<RESULT> method) {
        return new SpiceRequestWrapper<RESULT>(spiceManager, method);
    }

    public SpiceManager getSpiceManager() {
        return spiceManager;
    }
}
