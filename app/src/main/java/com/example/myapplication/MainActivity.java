package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //涉及资源，暂时注视
//        setContentView(R.layout.activity_main);
        Log.i("hzp", "我是宿主activity");
        Button button = new Button(this);


    }

}
