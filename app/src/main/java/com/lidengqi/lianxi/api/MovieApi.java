package com.lidengqi.lianxi.api;

import com.lidengqi.lianxi.entity.MovieEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lidengqi on 2017/5/17.
 */

public interface MovieApi {

    @GET("/v2/movie/top250")
    Call<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);
}
