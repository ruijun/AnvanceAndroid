package com.ruijun.anvanceandroid.inflater;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ruijun.anvanceandroid.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;

/**
 * Created by amal.chandran on 19/09/16.
 */
public class LazyLayoutInflaterActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout parentLayout;
    private Button btnAdd;
    private Button btnRemove;
    private EditText edtCount;
    private SwitchCompat switchAsync;

    private boolean isAsyncOn = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy_layout_inflater);
        bindViews();
        addListners();
    }

    private void bindViews(){
        parentLayout = findViewById(R.id.layout_parent);
        btnAdd = findViewById(R.id.btn_add);
        btnRemove = findViewById(R.id.btn_remove);
        edtCount = findViewById(R.id.edt_viewcount);
        switchAsync = findViewById(R.id.switch1);
    }

    private void addListners(){
        btnAdd.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        switchAsync.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAsyncOn = b;
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                String number = edtCount.getText().toString();
                if(number.isEmpty())number = "refactor1";
                int count = Integer.parseInt(number);
                loadInsaneNumberOfViews(count);
                break;
            case R.id.btn_remove:
                parentLayout.removeAllViews();
                break;
        }
    }

    private void loadInsaneNumberOfViews(int count){
        for(int i = 0; i < count; i++){
            log(System.currentTimeMillis()+"");
            if(!isAsyncOn) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.layout_dummy, null);
                LinearLayout inflatedLayout = (LinearLayout) view.findViewById(R.id.layout_dummy);
                parentLayout.addView(inflatedLayout);
            }else {
                AsyncLayoutInflater asyncInflator = new AsyncLayoutInflater(this);
                asyncInflator.inflate(R.layout.layout_dummy, null, new AsyncLayoutInflater.OnInflateFinishedListener() {
                    @Override
                    public void onInflateFinished(View view, int resid, ViewGroup parent) {
                        LinearLayout inflatedLayout = (LinearLayout) view.findViewById(R.id.layout_dummy);
                        parentLayout.addView(inflatedLayout);
                    }
                });
            }
        }
    }

    private void log(String log){
        Log.i(LazyLayoutInflaterActivity.class.getSimpleName(), ""+log);
    }
}