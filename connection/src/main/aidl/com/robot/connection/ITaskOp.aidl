package com.robot.connection;

import com.robot.connection.ITaskCallback;

interface ITaskOp {
    void registerCallback(ITaskCallback cb);
    void unregisterCallback(ITaskCallback cb);
    void test(String s);
}