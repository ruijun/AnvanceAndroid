package com.ruijun.anvanceandroid;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.ruijun.anvanceandroid.handler.HandlerActivity;
import com.ruijun.anvanceandroid.handler.ThreadLocalActivity;
import com.ruijun.anvanceandroid.hook.HookActivity;
import com.ruijun.anvanceandroid.inflater.LazyLayoutInflaterActivity;
import com.ruijun.anvanceandroid.kt.CoroutineActivity;
import com.ruijun.anvanceandroid.oom.OOMActivity;
import com.ruijun.anvanceandroid.touchevent.DispatchTouchEventActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterCompat;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String ENTER_STR = "\n";
    public static String[] cmdArray = {"ps", "-AT"};          // 查线程id


    private Button mBtnHandler;
    private Button mBtnThreadLocal;
    private Button mBtnOOM;
    private Button mBtnInflater;
    private Button mBtnDispatchEvent;
    private Button mBtnHookActivity;
    private Button mBtnKtActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

//                long start = System.currentTimeMillis();
                View view = getDelegate().createView(parent, name, context, attrs);
//                long cost = System.currentTimeMillis() - start;
//                Log.d("onCreateView", "==" + name + "==cost==" + cost);
                return view;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                return null;
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        HookHelper.hookActivityThreadInstrumentation(getPackageManager());

        mBtnHandler = findViewById(R.id.btn_handler);
        mBtnThreadLocal = findViewById(R.id.btn_thread_local);
        mBtnOOM = findViewById(R.id.btn_oom);
        mBtnInflater = findViewById(R.id.btn_inflater);
        mBtnDispatchEvent = findViewById(R.id.btn_dispatch_event);
        mBtnHookActivity = findViewById(R.id.btn_hook_activity);
        mBtnKtActivity = findViewById(R.id.btn_kt_activity);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBtnOOM.setText("Test OOM");
            }
        }).start();

        mBtnHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HandlerActivity.class);
                startActivity(intent);
            }
        });

        mBtnThreadLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThreadLocalActivity.class);
                startActivity(intent);
            }
        });

        mBtnOOM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OOMActivity.class);
                startActivity(intent);
            }
        });

        mBtnOOM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OOMActivity.class);
                startActivity(intent);
            }
        });

        mBtnInflater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LazyLayoutInflaterActivity.class);
                startActivity(intent);
            }
        });

        mBtnDispatchEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DispatchTouchEventActivity.class);
                startActivity(intent);
            }
        });

        mBtnHookActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HookActivity.class);
                startActivity(intent);
            }
        });

        mBtnKtActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CoroutineActivity.class);
                startActivity(intent);
            }
        });

        Log.d(TAG, "onCreate: ");

//        testOkHttp();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },200);

        new Thread(new Runnable() {
            @Override
            public void run() {
                readThreadInfo();
            }
        }).start();
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

    private void testOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://zhuanlan.zhihu.com/p/149024396")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: ", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response.code());
                Log.d(TAG, "response.body().string() == " + response.body().string());
            }
        });

        Log.d(TAG, "testOkHttp: " + request.url().host());


    }

    /**
     * 获取线程id 、name
     *
     * @return
     */
    public static String readThreadInfo() {
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        int threads = 0;
        try {
            Process process = Runtime.getRuntime().exec(cmdArray);
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append(ENTER_STR);
                ++threads;
                line = bufferedReader.readLine();
            }
            stringBuilder.append(ENTER_STR);
        } catch (Exception e) {
            Log.e(TAG, "readThreadInfo ", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.e(TAG, " threads = " + threads);
        Log.e(TAG, " threadInfo = " + stringBuilder.toString());
        return stringBuilder.toString();

    }
}