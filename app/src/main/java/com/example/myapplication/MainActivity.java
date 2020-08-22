package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.myapplication.fanxing.Apple;
import com.example.myapplication.fanxing.Banana;
import com.example.myapplication.fanxing.FanxingTest;
import com.example.myapplication.fanxing.Fruit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //涉及资源，暂时注视
//        setContentView(R.layout.activity_main);
        Log.i("hzp", "我是宿主activity");
        Button button = new Button(this);
        FanxingTest.fanxingTest();
    }
}
