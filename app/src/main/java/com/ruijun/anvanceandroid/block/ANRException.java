package com.ruijun.anvanceandroid.block;

import android.os.Looper;

/**
 * ANRException
 *
 * @author rj-liang
 * @date 2021/7/15 18:23
 */
class ANRException extends RuntimeException {
    public ANRException() {
        super("应用程序无响应，快来改BUG啊！！");
        Thread mainThread = Looper.getMainLooper().getThread();
        setStackTrace(mainThread.getStackTrace());

    }
}
