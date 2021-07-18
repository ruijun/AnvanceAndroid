package com.ruijun.anvanceandroid;

import android.app.Application;
import android.content.Context;

import com.ruijun.anvanceandroid.jetpack.LifecycleChecker;

import androidx.lifecycle.ProcessLifecycleOwner;

/**
 * MainApplication
 *
 * @author ruijun
 * @date 2021/7/182:06 PM
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new LifecycleChecker());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
