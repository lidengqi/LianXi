package com.lidengqi.lianxi.network;

/**
 * Created by lidengqi on 2017/5/17.
 */

public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}
