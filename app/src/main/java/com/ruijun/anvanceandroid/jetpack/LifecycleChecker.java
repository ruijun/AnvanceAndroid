package com.ruijun.anvanceandroid.jetpack;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 类或接口的描述信息
 *
 * @author ruijun
 * @date 2021/7/182:21 PM
 */
public class LifecycleChecker implements LifecycleObserver {
    private static final String TAG = "LifecycleChecker";

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onAppBackground() {
        // 应用进入后台
        Log.d(TAG,"LifecycleChecker onAppBackground ON_STOP");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void onAppForeground() {
        // 应用进入前台
        Log.d(TAG,"LifecycleChecker onAppForeground ON_START");

    }
}
