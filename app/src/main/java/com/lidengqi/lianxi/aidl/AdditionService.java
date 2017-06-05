package com.lidengqi.lianxi.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by lidengqi on 2017/6/5.
 */

public class AdditionService extends Service{

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IAdditionService.Stub(){

            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double
                    aDouble, String aString) throws RemoteException {
            }

            @Override
            public int add(int value1, int value2) throws RemoteException {
                return value1 + value2;
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
