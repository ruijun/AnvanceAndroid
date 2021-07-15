package com.ruijun.anvanceandroid.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.pm.PackageManager;

import java.lang.reflect.Field;

/**
 * com.ruijun.anvanceandroid.hook
 *
 * @author rj-liang
 * @date 2021/2/8 16:41
 */
public class HookHelper {
    public static void hookActivityThreadInstrumentation(PackageManager packageManager) {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field activityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            activityThreadField.setAccessible(true);
            //获取ActivityThread对象sCurrentActivityThread
            Object activityThread = activityThreadField.get(null);

            Field instrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
            instrumentationField.setAccessible(true);
            //从sCurrentActivityThread中获取成员变量mInstrumentation
            Instrumentation instrumentation = (Instrumentation) instrumentationField.get(activityThread);
            //创建代理对象InstrumentationProxy
            InstrumentationProxy proxy = new InstrumentationProxy(instrumentation, packageManager);
            //将sCurrentActivityThread中成员变量mInstrumentation替换成代理类InstrumentationProxy
            instrumentationField.set(activityThread, proxy);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    public static void hookActivityInstrumentation(Activity activity) {
//        try {
//            // 得到Activity的mInstrumentation字段
//            Field field = Activity.class.getDeclaredField("mInstrumentation");
//            field.setAccessible(true);
//            // 得到Activity中的Instrumentation对象
//            Instrumentation instrumentation = (Instrumentation) field.get(activity);
//            // 创建InstrumentationProxy对象来代理Instrumentation对象
//            InstrumentationProxy instrumentationProxy = new InstrumentationProxy(instrumentation);
//            // 用代理去替换Activity中的Instrumentation对象
//            field.set(activity, instrumentationProxy);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
}
