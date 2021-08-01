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

    private static Application mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new LifecycleChecker());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static Application getContext() {
        return mContext;
    }

}
