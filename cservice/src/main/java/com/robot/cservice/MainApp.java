package com.robot.cservice;

import android.app.Application;

/**
 * @author: joey
 * @function: APP容器
 * @date: 2019/03/27
 */
public class MainApp extends Application {

    private static MainApp mApp = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static MainApp get() {
        return mApp;
    }
}
