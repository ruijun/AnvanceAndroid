package com.ruijun.anvanceandroid.handler;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * com.ruijun.anvanceandroid.handler
 *
 * @author rj-liang
 * @date 2020/12/22 17:18
 */
public class SampleTask implements Runnable {
    private static final String TAG = SampleTask.class.getSimpleName();
    Handler mHandler;

    public SampleTask(Handler handler) {
        super();
        this.mHandler = handler;
    }

    @Override
    public void run() {
        try {
            // 模拟执行某项任务，下载等
            Thread.sleep(2000);
            Log.d(TAG, "run--->");
            // 任务完成后通知activity更新UI
            Message msg = prepareMessage("task completed!");
            // message将被添加到主线程的MQ中
            mHandler.sendMessage(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d(TAG, "interrupted!");
        }

    }

    private Message prepareMessage(String str) {
        Message result = mHandler.obtainMessage();
        result.what = 1000;
        result.obj = str;
//        Bundle data = new Bundle();
//        data.putString("message", str);
//        result.setData(data);
        return result;
    }
}
