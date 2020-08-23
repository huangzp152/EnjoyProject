package com.example.reflecttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @InjectView(value = R.id.tttvvv, j = 3)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InjectUtils.injectview(this);//相当于通过注解和反射的方式给代码赋值或者初始化了
        textView.setText("Handsome Stanley");
    }
}
