package com.ruijun.anvanceandroid.block;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * ANRWatchDog
 *
 * @author rj-liang
 * @date 2021/7/15 18:23
 */
class ANRWatchDog extends Thread {
    public static final int MESSAGE_WATCHDOG_TIME_TICK = 0;

    /**
     * 判定Activity发生了ANR的时间，必须要小于5秒，否则等弹出ANR，可能就被用户立即杀死了。
     */
    public static final int ACTIVITY_ANR_TIMEOUT = 2000;


    //用来判断消息是否被处理
    private static int lastTimeTick = -1;
    private static int timeTick = 0;

    /**
     * 用来给主线程发送消息，并处理消息。
     */
    private Handler watchDogHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            timeTick++;
            timeTick = timeTick % Integer.MAX_VALUE;
            Log.i("ANRWatch","timeTick = "+timeTick+"  lastTimeTick="+lastTimeTick);
        }
    };

    @Override
    public void run() {
        super.run();
        while (true) {
            watchDogHandler.sendEmptyMessage(MESSAGE_WATCHDOG_TIME_TICK);
            try {
                Thread.sleep(ACTIVITY_ANR_TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //如果相等，说明过了ACTIVITY_ANR_TIMEOUT的时间后watchDogHandler仍没有处理消息，已经ANR了
            if (timeTick == lastTimeTick) {
                throw new ANRException();
            } else {
                lastTimeTick = timeTick;
            }
        }

    }
}
