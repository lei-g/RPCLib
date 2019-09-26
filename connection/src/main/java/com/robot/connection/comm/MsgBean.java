package com.robot.connection.comm;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author: joey
 * @function: 消息
 * @date: 2019/3/25
 */
public class MsgBean implements Parcelable {

    private int msgCode;
    private String msgInfo;

    public MsgBean() {

    }

    protected MsgBean(Parcel in) {
        this.msgCode = in.readInt();
        this.msgInfo = in.readString();
    }

    public MsgBean(int code, String info) {
        this.msgCode = code;
        this.msgInfo = info;
    }

    public int getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(String msgInfo) {
        this.msgInfo = msgInfo;
    }

    public static final Creator<MsgBean> CREATOR = new Creator<MsgBean>() {
        @Override
        public MsgBean createFromParcel(Parcel in) {
            return new MsgBean(in);
        }

        @Override
        public MsgBean[] newArray(int size) {
            return new MsgBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.msgCode);
        parcel.writeString(this.msgInfo);
    }

    public void readFromParcel(Parcel dest) {
        msgCode = dest.readInt();
        msgInfo = dest.readString();
    }

}
