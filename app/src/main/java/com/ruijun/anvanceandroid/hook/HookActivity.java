package com.ruijun.anvanceandroid.hook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ruijun.anvanceandroid.R;

public class HookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
    }
}