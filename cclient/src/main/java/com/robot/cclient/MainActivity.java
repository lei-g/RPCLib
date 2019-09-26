package com.robot.cclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.robot.connection.ITaskCallback;
import com.robot.connection.ITaskOp;
import com.robot.connection.comm.MsgBean;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "glei-client";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService();

        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (mTaskOp != null) {
                        mTaskOp.test("client test");
                    } else {
                        Log.e(TAG, "mTaskOp is null");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn_unbind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService();
            }
        });
    }

    private ITaskOp mTaskOp;
    private boolean connected;

    private void bindService() {
        Intent intent = new Intent();
        intent.setPackage("com.robot.cservice");
        intent.setAction("rpc.connection.action");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbindService() {
        if (connected) {
            try {
                mTaskOp.unregisterCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            unbindService(mConnection);
            connected = false;
            mTaskOp = null;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            connected = true;
            mTaskOp = ITaskOp.Stub.asInterface(service);
            try {
                mTaskOp.registerCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.d(TAG, "onServiceDisconnected");
            connected = false;
            mTaskOp = null;
        }
    };

    private ITaskCallback mCallback = new ITaskCallback.Stub() {
        @Override
        public void onReceiveMsg(MsgBean msg) throws RemoteException {
            int code = msg.getMsgCode();
            String info = msg.getMsgInfo();
            Log.d(TAG, "onReceiveMsg code : " + code);
            Log.d(TAG, "onReceiveMsg info : " + info);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService();
    }

}
