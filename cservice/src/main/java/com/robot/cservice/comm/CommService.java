package com.robot.cservice.comm;

/**
 * @author: joey
 * @function:
 * @date: 2019/3/25
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.robot.connection.ITaskCallback;
import com.robot.connection.ITaskOp;
import com.robot.connection.comm.MsgBean;
import com.squareup.otto.Subscribe;

public class CommService extends Service {

    private static final String TAG = "glei-CommService";

    @Override
    public void onCreate() {
        Log.d(TAG, "CommService create");
    }

    @Override
    public IBinder onBind(Intent t) {
        Log.d(TAG, "CommService on bind");
        BusProvider.getInstance().register(this);
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "CommService on unbind");
        BusProvider.getInstance().unregister(this);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "CommService on destroy");
        super.onDestroy();
    }

    void callback(MsgBean msg) {
        final int N = mCallbacks.beginBroadcast();
        for (int i = 0; i < N; i++) {
            try {
                mCallbacks.getBroadcastItem(i).onReceiveMsg(msg);
            } catch (RemoteException e) {
                Log.e(TAG, "callback Exception " + e.toString());
            }
        }
        mCallbacks.finishBroadcast();
    }

    private final ITaskOp.Stub mBinder = new ITaskOp.Stub() {

        public void registerCallback(ITaskCallback cb) {
            if (cb != null) {
                mCallbacks.register(cb);
            }
        }

        public void unregisterCallback(ITaskCallback cb) {
            if (cb != null) {
                mCallbacks.unregister(cb);
            }
        }

        @Override
        public void test(String s) throws RemoteException {
            Log.d(TAG, "test receive s = " + s);
        }
    };

    final RemoteCallbackList<ITaskCallback> mCallbacks = new RemoteCallbackList<ITaskCallback>();

    @Subscribe
    public void showEvent(final EventData event) {
        callback(new MsgBean(event.code, event.info));
    }

}
