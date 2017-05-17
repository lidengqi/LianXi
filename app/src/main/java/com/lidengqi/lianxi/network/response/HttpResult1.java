package com.lidengqi.lianxi.network.response;

/**
 * Created by lidengqi on 2017/5/17.
 */

public class HttpResult1<T> {

    //用来模仿resultCode和resultMessage
    private int count;
    private int start;
    private int total;
    private String title;

    //用来模仿Data
    private T subjects;
}
