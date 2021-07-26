package com.ruijun.anvanceandroid.customview;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;

/**
 * WebView初始化
 * WebView-->ensureProviderCreated-->WebViewFactoryProvider#getProvider-->WebViewChromiumFactoryProvider#createWebView
 */
public class FixBugWebView extends WebView {
    private static final String TAG = "FixBugWebView";
    public FixBugWebView(Context context) {
        super(getFixedContext(context));
    }

    public FixBugWebView(Context context, AttributeSet attrs) {
        super(getFixedContext(context), attrs);
    }

    public FixBugWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(getFixedContext(context), attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FixBugWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(getFixedContext(context), attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setOverScrollMode(int mode) {
//        try {
//            super.setOverScrollMode(mode);
//        } catch (Exception e) {
//            String errorMessage = e.getMessage();
//            if (!TextUtils.isEmpty(errorMessage) && (errorMessage.contains("Failed to load WebView provider: No WebView installed")
//                    || errorMessage.contains("Failed to verify WebView provider, version code is lower than expected"))) {
//                e.printStackTrace();
//            } else {
//                throw e;
//            }
//        }

        try {
            super.setOverScrollMode(mode);
        } catch (Throwable e) {
            Pair<Boolean, String> pair = isWebViewPackageException(e);
            if (pair.first) {
                e.printStackTrace();
//                destroy();
            } else {
                throw e;
            }
        }
    }

    private static Context getFixedContext(Context context) {
        // Android 5.0 和 5.1 可能 有 Resources$NotFoundException问题
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP || Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1) {
            return context.createConfigurationContext(new Configuration());
        }
        return context;
    }


    private Pair<Boolean, String> isWebViewPackageException(Throwable e) {
        String messageCause = e.getCause() == null ? e.toString() : e.getCause().toString();
        String trace = Log.getStackTraceString(e);
        if (trace.contains("android.content.pm.PackageManager$NameNotFoundException")
                || trace.contains("java.lang.RuntimeException: Cannot load WebView")
                || trace.contains("android.webkit.WebViewFactory$MissingWebViewPackageException: Failed to load WebView provider: No WebView installed")
                || trace.contains("Failed to verify WebView provider, version code is lower than expected")) {

            Log.e(TAG, "isWebViewPackageException", e);
            return new Pair<Boolean, String>(true, "WebView load failed, " + messageCause);
        }
        return new Pair<Boolean, String>(false, messageCause);
    }
}
