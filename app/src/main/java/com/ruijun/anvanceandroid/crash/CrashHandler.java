package com.ruijun.anvanceandroid.crash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.ruijun.anvanceandroid.BuildConfig;
import com.ruijun.anvanceandroid.MainApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    // CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();
    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // 程序的Context对象
    private Context mContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<>();

    // 用于格式化日期,作为日志文件名的一部分
    @SuppressLint("SimpleDateFormat")
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();
      //  handleException(ex);

        Log.e(TAG, "uncaughtException", ex);
        if (!false) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
                ignored.printStackTrace();
            }
        }

        //不需要回传崩溃事件
//        Log.e("CrashHandler","MyApplication.isInintApppplication ="+MyApplication.isInintApppplication);
//        if(!MyApplication.isInintApppplication) {
//            mDefaultHandler.uncaughtException(thread, ex);
//        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 使用Toast来显示异常信息
        final boolean showToast =  BuildConfig.DEBUG;
        if (showToast) {
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(MainApplication.getContext(), "抱歉，出現錯誤，正在收集日誌，稍後將退出", Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }.start();
        }

        // 收集设备参数信息
        collectDeviceInfo(mContext);
        // 保存日志文件
        final String crashFile = saveCrashInfo2File(ex);

        if (showToast) {
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(MainApplication.getContext(), "已保存至:" + crashFile, Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }.start();
        }
        return true;
    }

    /**
     * 收集设备参数信息
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? null : pi.versionName;
                String versionCode = String.valueOf(pi.versionCode);
                infos.put(versionName, versionName);
                infos.put(versionCode, versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private String saveCrashInfo2File(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }
        sb.append("\n");

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                final File file = new File(MainApplication.getContext().getExternalFilesDir(null).getAbsolutePath() +  fileName);
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(sb.toString().getBytes());
                fos.close();
                return file.getPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
