package com.example.myretrofit;

import okhttp3.ResponseBody;

public interface WeatherApi {
    //get and post annotation method
    @POST("/v3/weather/weatherInfo")
    @FormUrlEncoded
    Call<ResponseBody> postWeather(@Field("city") String city, @Field("key") String key);

    @GET("/v3/weather/weatherInfo")
    Call<ResponseBody> getWeather(@Field("city") String city, @Field("key") String key);
}
