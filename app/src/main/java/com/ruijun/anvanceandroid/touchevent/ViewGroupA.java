package com.ruijun.anvanceandroid.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * com.ruijun.anvanceandroid.touchevent
 *
 * @author rj-liang
 * @date 2021/2/2 16:58
 */
public class ViewGroupA extends RelativeLayout {
    private static final String TAG = Static.TAG3;

    public ViewGroupA(Context context) {
        super(context);
    }

    public ViewGroupA(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.i(TAG, Static.dispatchTouchEvent + "项目进度?");
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.i(TAG, Static.onInterceptTouchEvent);
        }

//         return super.onInterceptTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.i(TAG, Static.onTouchEvent+"正在测试,明天就测试完了");
        }
        return true;
    }
}
