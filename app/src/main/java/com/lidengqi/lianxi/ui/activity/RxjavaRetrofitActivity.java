package com.lidengqi.lianxi.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.lidengqi.lianxi.R;
import com.lidengqi.lianxi.entity.MovieEntity;
import com.lidengqi.lianxi.network.SubscriberOnNextListener;
import com.lidengqi.lianxi.util.HttpMethods;
import com.lidengqi.lianxi.util.ProgressSubscriber;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Rxjava与Retrofit结合使用Demo
 * Created by lidengqi on 2017/5/17.
 */

public class RxjavaRetrofitActivity extends AppCompatActivity{

    @BindView(R.id.bt_click)
    Button mButton;

    @BindView(R.id.tv_result)
    TextView mResultTV;

    Subscriber<List<MovieEntity>> subscriber;
    SubscriberOnNextListener subscriberOnNextListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjavaretrofit);
        ButterKnife.bind(this);

        subscriberOnNextListener = new SubscriberOnNextListener<List<MovieEntity>>() {
            @Override
            public void onNext(List<MovieEntity> movieEntityList) {
                mResultTV.setText(movieEntityList.toString());
            }
        };
    }

    @OnClick(R.id.bt_click)
    public void onClick() {
        getMovie();
    }

    //进行网络请求
    private void getMovie() {
        subscriber = new ProgressSubscriber(subscriberOnNextListener, RxjavaRetrofitActivity.this);
        HttpMethods.getInstance().getTopMovie(subscriber, 0, 10);
    }
}
