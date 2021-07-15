package com.ruijun.anvanceandroid.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.ruijun.anvanceandroid.R;

/**
 * 流式标签布局
 */
public class FlowTagLayout extends ViewGroup {
    private static final String TAG = FlowTagLayout.class.getSimpleName();
    private int mMaxChildLine = -1;

    public FlowTagLayout(Context context) {
        this(context, null);
    }

    public FlowTagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowTagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowTagLayout, 0, 0);
            mMaxChildLine = a.getInteger(R.styleable.FlowTagLayout_maxChildLine, -1);
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取Padding
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //FlowLayout最终的宽度和高度值
        int resultWidth = 0;
        int resultHeight = 0;

        //测量时每一行的宽度
        int lineWidth = 0;
        //测量时每一行的高度，加起来就是FlowLayout的高度
        int lineHeight = 0;
        // 行数
        int childLineCount = 0;

        //遍历每个子元素
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);
            //测量每一个子view的宽和高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            //获取到测量的宽和高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

//            Log.d(TAG, "onMeasure: childWidth=" + childWidth + " childHeight=" + childHeight
//                    + " lineWidth=" + lineWidth + " lineHeight=" + lineHeight);

            //因为子View可能设置margin，这里要加上margin的距离
            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();
            int realChildWidth = childWidth + mlp.getMarginStart() + mlp.getMarginEnd();
            int realChildHeight = childHeight + mlp.topMargin + mlp.bottomMargin;
//
//            Log.d(TAG, "onMeasure: realChildWidth=" + childWidth + " realChildHeight=" + childHeight
//                    + " lineWidth=" + lineWidth + " lineHeight=" + lineHeight);


            //如果当前一行的宽度加上要加入的子view的宽度大于父容器给的宽度，就换行
            if ((lineWidth + realChildWidth) > sizeWidth) {
                //换行
                resultWidth = Math.max(lineWidth, realChildWidth);
                resultHeight += realChildHeight;
                childLineCount++;
                if (childLineCount == mMaxChildLine) {
                    setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : resultWidth,
                            modeHeight == MeasureSpec.EXACTLY ? sizeHeight : resultHeight);
                    break;
                }
                //换行了，lineWidth和lineHeight重新算
                lineWidth = realChildWidth;
                lineHeight = realChildHeight;
            } else {
                //不换行，直接相加
                lineWidth += realChildWidth;
                //每一行的高度取二者最大值
                lineHeight = Math.max(lineHeight, realChildHeight);
            }

            //遍历到最后一个的时候，肯定走的是不换行
            if (i == childCount - 1) {
                resultWidth = Math.max(lineWidth, resultWidth);
                resultHeight += lineHeight;
            }

            setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : resultWidth,
                    modeHeight == MeasureSpec.EXACTLY ? sizeHeight : resultHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final boolean isLayoutRtl = false;

        int flowWidth = getWidth();
        int childLeft = 0;
        int childTop = 0;
        int childLineCount = 0;
        if (childLineCount == mMaxChildLine) {
            return;
        }

        //遍历子控件，记录每个子view的位置
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);

            //跳过View.GONE的子View
            if (childView.getVisibility() == View.GONE) {
                continue;
            }

            //获取到测量的宽和高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            //因为子View可能设置margin，这里要加上margin的距离
            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();

            if (childLeft + mlp.getMarginStart() + childWidth + mlp.getMarginEnd() > flowWidth) {
                childLineCount++;
                if (childLineCount == mMaxChildLine) {
                    return;
                }
                //换行处理
                childTop += (mlp.topMargin + childHeight + mlp.bottomMargin);
                childLeft = 0;
            }
            //布局
            int left = childLeft + mlp.getMarginStart();
            int top = childTop + mlp.topMargin;
            int right = childLeft + mlp.getMarginStart() + childWidth;
            int bottom = childTop + mlp.topMargin + childHeight;

            if (right + mlp.getMarginEnd() > flowWidth) {
                right = flowWidth - mlp.getMarginEnd();
            }
            if (isLayoutRtl) {
                left = flowWidth - childLeft - childWidth;
                if (left < 0) {
                    left = 0;
                }
                right = flowWidth - childLeft;
            }

            childView.layout(left, top, right, bottom);

            childLeft += (mlp.getMarginStart() + childWidth + mlp.getMarginEnd());
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

}
