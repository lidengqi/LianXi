package com.lidengqi.lianxi.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidengqi.lianxi.R;
import com.lidengqi.lianxi.aidl.AdditionService;
import com.lidengqi.lianxi.aidl.IAdditionService;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lidengqi on 2017/6/5.
 */

public class AidlActivity extends AppCompatActivity {

    IAdditionService service;
    AdditionServiceConnection connection;

    @BindView(R.id.value1)
    EditText value1;
    @BindView(R.id.value2)
    EditText value2;
    @BindView(R.id.buttonCalc)
    Button buttonCalc;
    @BindView(R.id.result)
    TextView result;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        ButterKnife.bind(this);
        initService();
        initView();
    }

    private void initView() {
        buttonCalc = (Button)findViewById(R.id.buttonCalc);
        result = (TextView)findViewById(R.id.result);
        value1 = (EditText)findViewById(R.id.value1);
        value2 = (EditText)findViewById(R.id.value2);

        buttonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int v1, v2, res = -1;
                v1 = Integer.parseInt(value1.getText().toString());
                v2 = Integer.parseInt(value2.getText().toString());
                try {
                    res = service.add(v1, v2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                result.setText(Integer.valueOf(res).toString());
            }
        });
    }

//    @OnClick(R.id.buttonCalc)
//    public void onClick() {
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseService();
    }

    /**
     * This inner class is used to connect to the service
     */
    class AdditionServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder boundservice) {
            service = IAdditionService.Stub.asInterface((IBinder) boundservice);
            Toast.makeText(AidlActivity.this, "Service connected", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            service = null;
            Toast.makeText(AidlActivity.this, "Service disconnected", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This function connects the Activity to the service
     */
    private void initService() {
        connection = new AdditionServiceConnection();
        Intent intent = new Intent(this, AdditionService.class);
//        intent.setClassName("com.lidengqi.lianxi.aidl", AdditionService.class.getName());
        boolean ret = bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    /**
     * This function disconnects the Activity from the service
     */
    private void releaseService() {
        unbindService(connection);
        connection = null;
    }
}
