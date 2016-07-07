package com.mayank.user.mytaskreminder;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by user on 07-07-2016.
 */
public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
