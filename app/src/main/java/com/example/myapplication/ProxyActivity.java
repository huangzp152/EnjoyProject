package com.example.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ProxyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("hzp", "我是插件activity");

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.myapplication", "com.example.myapplication.ProxyActivity"));
        startActivity(intent);

    }
}
