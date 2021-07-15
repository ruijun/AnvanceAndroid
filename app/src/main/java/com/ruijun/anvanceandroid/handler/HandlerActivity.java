package com.ruijun.anvanceandroid.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;
import android.widget.TextView;

import com.ruijun.anvanceandroid.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class HandlerActivity extends AppCompatActivity {
    private static final String TAG = "HandlerActivity";
    private TextView textview;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        textview = findViewById(R.id.text_view);

//        mHandler = new MyHandler();

        Thread myThread = new MyHandlerThread();
        myThread.start();

//        // 创建并启动工作线程
//        Thread workerThread = new Thread(new SampleTask(mHandler));
//        workerThread.start();

        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                //这里做一些操作
                Log.d(TAG, "queueIdle: ");
                return false;
            }
        });
    }

    public void appendText(String msg) {
        textview.setText(textview.getText() + "\n" + msg);
    }

    class MyHandler extends Handler {
        public MyHandler() {
        }

        public MyHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "msg----->" + msg.what);
            switch (msg.what) {
                case 1000:
                    String result = (String)msg.obj;
                    // 更新UI
                    appendText(result);
                    break;

                default:
                    break;
            }

        }
    }

    class MyHandlerThread extends Thread {
        @Override
        public void run() {
            Log.d(TAG, "run----->1");
            Looper.prepare();
            mHandler = new MyHandler(Looper.getMainLooper());

            try {
                Log.d(TAG, "run----->2");
                Thread.sleep(2000);
                // 任务完成后通知activity更新UI
                Message result = mHandler.obtainMessage();
                result.what = 1000;
                result.obj = "Hello Handler!!";
                mHandler.sendMessage(result);
                // message将被添加到主线程的MQ中
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Looper.loop();

            // 创建并启动工作线程
//            Thread workerThread = new Thread(new SampleTask(mHandler));
//            workerThread.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}