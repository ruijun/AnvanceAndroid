package com.ruijun.anvanceandroid.handler;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.ruijun.anvanceandroid.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ThreadLocalActivity extends AppCompatActivity {
    private static final String TAG = "ThreadLocalActivity";
    private ThreadLocal<String> mThreadLocal = new ThreadLocal<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_local);

        mThreadLocal.set("小明");
        Log.d("ThreadLocalActivity", "Thread:" + Thread.currentThread().getName() + " name:" + mThreadLocal.get());
        new Thread("thread1") {
            @Override
            public void run() {
                mThreadLocal.set("小红A");
                mThreadLocal.set("小红B");
                Log.d("ThreadLocalActivity", "Thread:" + Thread.currentThread().getName() + " name:" + mThreadLocal.get());
            }
        }.start();
        new Thread("thread2") {
            @Override
            public void run() {
                Log.d("ThreadLocalActivity", "Thread:" + Thread.currentThread().getName() + " name:" + mThreadLocal.get());
            }
        }.start();

        Log.d(TAG, "onCreate: ");

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: ");
    }

}