package com.robot.cservice;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.robot.connection.comm.IflytekEvent;
import com.robot.cservice.comm.BusProvider;
import com.robot.cservice.comm.EventData;
import com.squareup.otto.Produce;

/**
 * @author: joey
 * @function: AIUI容器
 * @date: 2019/03/27
 */

public class MainService extends Service {

    private static final String TAG = "glei-service";

    private static final int LIVE_EVENT = 1;
    private static final int OUT_PERIOD = 3000;

    public MainService() {
    }

    public class MyBinder extends Binder {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "MainService create");

        /** 每分钟发送一次，表示service正常运行，目前主要用于调试目的 */
        mySend(LIVE_EVENT, OUT_PERIOD);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
    }

    private void mySend(int what, long delay) {
        Message message = new Message();
        message.what = what;
        mHandler.sendMessageDelayed(message, delay);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LIVE_EVENT:
                    Log.d(TAG, "我活着呢！！！");
                    BusProvider.getInstance().post(produceEventData(IflytekEvent.TEST_CODE, "我活着呢"));
                    mySend(LIVE_EVENT, OUT_PERIOD);
                    break;
            }
        }
    };

    /**
     * @Description 设法保证service被kill掉之后还可以重启
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Produce
    public EventData produceEventData(int code, String info) {
        return new EventData(code, info);
    }
}
