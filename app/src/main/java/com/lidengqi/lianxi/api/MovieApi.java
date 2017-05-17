package com.lidengqi.lianxi.api;

import com.lidengqi.lianxi.entity.MovieEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lidengqi on 2017/5/17.
 */

public interface MovieApi {

    @GET("/v2/movie/top250")
    Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);
}
