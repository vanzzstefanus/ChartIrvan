package com.example.chartirvan.helper;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface INodeJs {
    @POST("predict")
    Observable<String> predict(@Body String json);
}


