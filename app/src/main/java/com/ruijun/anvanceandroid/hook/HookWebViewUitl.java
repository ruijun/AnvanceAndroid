package com.ruijun.anvanceandroid.hook;

import android.os.Build;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class HookWebViewUitl {
    private static final String TAG = "HookUtils";
    private static final String CHROMIUM_WEBVIEW_FACTORY_METHOD = "create";

    public HookWebViewUitl() {
    }

    public static void hookWebViewProvider() {
        try {
            Class<?> factoryClass = Class.forName("android.webkit.WebViewFactory");
            Field field = factoryClass.getDeclaredField("sProviderInstance");
            field.setAccessible(true);
            Object sProviderInstance = field.get((Object)null);
            if (sProviderInstance != null) {
                Log.d("HookUtils", "HookWebView----->sProviderInstance isn't null");
                return;
            }

            Method getProviderClassMethod;
            if (Build.VERSION.SDK_INT > 22) {
                getProviderClassMethod = factoryClass.getDeclaredMethod("getProviderClass");
            } else {
                if (Build.VERSION.SDK_INT != 22) {
                    Log.d("HookUtils", "HookWebView----->Don't need to Hook WebView");
                    return;
                }

                getProviderClassMethod = factoryClass.getDeclaredMethod("getFactoryClass");
            }

            getProviderClassMethod.setAccessible(true);
            Class<?> providerClass = (Class)getProviderClassMethod.invoke(factoryClass);
            Class<?> delegateClass = Class.forName("android.webkit.WebViewDelegate");
            Constructor<?> providerConstructor = providerClass.getConstructor(delegateClass);
            if (providerConstructor != null) {
                providerConstructor.setAccessible(true);
                Constructor<?> declaredConstructor = delegateClass.getDeclaredConstructor();
                declaredConstructor.setAccessible(true);
                sProviderInstance = providerConstructor.newInstance(declaredConstructor.newInstance());
                Log.d("HookUtils", "HookWebView----->sProviderInstance:{}" + sProviderInstance);
                field.set("sProviderInstance", sProviderInstance);
            }

            Log.d("HookUtils", "HookWebView----->Hook done!");
        } catch (Throwable var8) {
            Log.e("HookUtils", "HookWebView----->" + var8);
        }

    }
}
