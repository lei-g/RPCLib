package com.robot.connection;

import com.robot.connection.comm.MsgBean;

interface ITaskCallback {

    //in out inout
    void onReceiveMsg(in MsgBean msg);

}