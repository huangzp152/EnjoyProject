package com.example.myretrofit;

import com.example.myretrofit.retrofit.Field;
import com.example.myretrofit.retrofit.GET;
import com.example.myretrofit.retrofit.POST;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.ResponseBody;

public interface EnjoyWeatherApi {
    //get and post annotation method
    @POST("/v3/weather/weatherInfo")
    Call postWeather(@Field("city") String city, @Field("key") String key);

    @GET("/v3/weather/weatherInfo")
    Call getWeather(@Field("city") String city, @Field("key") String key);

}


