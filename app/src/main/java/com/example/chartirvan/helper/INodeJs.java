package com.example.chartirvan.helper;

import io.reactivex.Observable;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface INodeJs {
    @POST("predict")
    @FormUrlEncoded
    Observable<String> predict(@Field("json") String json);
}


