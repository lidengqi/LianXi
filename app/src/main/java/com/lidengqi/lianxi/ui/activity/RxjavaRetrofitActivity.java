package com.lidengqi.lianxi.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.lidengqi.lianxi.R;
import com.lidengqi.lianxi.api.MovieApi;
import com.lidengqi.lianxi.entity.MovieEntity;
import com.lidengqi.lianxi.global.AppConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Rxjava与Retrofit结合使用Demo
 * Created by lidengqi on 2017/5/17.
 */

public class RxjavaRetrofitActivity extends AppCompatActivity{

    @BindView(R.id.bt_click)
    Button mButton;

    @BindView(R.id.tv_result)
    TextView mResultTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjavaretrofit);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_click)
    public void onClick() {
        getMovie();
    }

    //进行网络请求
    private void getMovie() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<MovieEntity> call = movieApi.getTopMovie(0,10);
        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                mResultTV.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                mResultTV.setText(t.getMessage());
            }
        });
    }
}
