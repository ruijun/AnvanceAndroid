package com.ruijun.anvanceandroid.touchevent;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.ruijun.anvanceandroid.R;

import androidx.appcompat.app.AppCompatActivity;

public class DispatchTouchEventActivity extends AppCompatActivity {

    private static final String TAG = Static.TAG1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_touch_event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            //Log.i(TAG, Static.dispatchTouchEvent+"经理,我准备发展一下电商业务,下周之前做一个淘宝出来.");
            //Log.i(TAG, Static.dispatchTouchEvent+"把按钮做的好看一点,要有光泽,给人一种点击的欲望.");
            Log.i(TAG, Static.dispatchTouchEvent+"现在项目做到什么程度了?");
        }
//        return super.dispatchTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            //Log.i(TAG, Static.onTouchEvent+"这么简单都做不了,你们都是干啥的(愤怒).");
            Log.i(TAG, Static.onTouchEvent);
        }
        return true;
    }

}