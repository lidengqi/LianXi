package com.lidengqi.lianxi.network.response;

/**
 * Created by lidengqi on 2017/5/17.
 */

public class HttpResult<T> {
    private int resultCode;
    private String resultMessage;

    private T data;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public T getData() {
        return data;
    }
}
