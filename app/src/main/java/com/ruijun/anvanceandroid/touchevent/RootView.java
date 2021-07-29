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
 * @date 2021/2/2 16:56
 */
public class RootView extends RelativeLayout {
    private static final String TAG = Static.TAG2;

    public RootView(Context context) {
        super(context);
    }

    public RootView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.i(TAG, Static.dispatchTouchEvent + "技术部,你们的app快做完了么?");
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.i(TAG, Static.onInterceptTouchEvent);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.i(TAG, Static.onTouchEvent);
        }
        return false;
    }
}
