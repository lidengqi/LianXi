package com.lidengqi.lianxi.util;

import android.content.Context;
import android.widget.Toast;

import com.lidengqi.lianxi.network.SubscriberOnNextListener;
import com.lidengqi.lianxi.network.ProgressCancelListener;

import rx.Subscriber;

/**
 * Created by lidengqi on 2017/5/17.
 */

public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener{
    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProgressDialogHandler progressDialogHandler;
    private Context context;

    public ProgressSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = subscriberOnNextListener;
        this.context = context;
        progressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    private void showProgressDialog() {
        if (progressDialogHandler != null) {
            progressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (progressDialogHandler != null) {
            progressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            progressDialogHandler = null;
        }
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
        Toast.makeText(context, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNext(T t) {
        mSubscriberOnNextListener.onNext(t);
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
