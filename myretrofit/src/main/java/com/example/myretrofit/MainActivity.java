package com.example.myretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://restapi.amap.com").build();
//        WeatherApi weatherApi = retrofit.create(WeatherApi.class);

        //可以自己撸的retrofit
        //涉及到反射 动态代理 注解
        EnjoyRetrofit retrofit = new EnjoyRetrofit.Builder().baseUrl("https://restapi.amap.com").build();
        EnjoyWeatherApi weatherApi = retrofit.create(EnjoyWeatherApi.class);
    }
}
