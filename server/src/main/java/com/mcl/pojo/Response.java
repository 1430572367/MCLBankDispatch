package com.mcl.pojo;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long nSerialVerUID = 2L;
/*
    private int nSubReqID;

    private int nRespCode;

    private String strDesc;

    public final int getnSubReqID() {
        return nSubReqID;
    }

    public final void setnSubReqID(int nSubReqID) {
        this.nSubReqID = nSubReqID;
    }

    public final int getnRespCode() {
        return nRespCode;
    }

    public final void setRespCode(int nRespCode) {
        this.nRespCode = nRespCode;
    }

    public final String getstrDesc() {
        return strDesc;
    }

    public final void setDesc(String strDesc) {
        this.strDesc = strDesc;
    }

    @Override
    public String toString() {
        return "SubscribeResp [nSubReqID=" + nSubReqID + ", nRespCode=" + nRespCode
                + ", strDesc=" + strDesc + "]";
    }*/

    private String type;
    private int num;
    private String strDesc;

    public void setType(String type) {
        this.type = type;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setStrDesc(String strDesc) {
        this.strDesc = strDesc;
    }

    public String getType() {
        return type;
    }

    public int getNum() {
        return num;
    }

    public String getStrDesc() {
        return strDesc;
    }

    @Override
    public String toString() {
        return "Response{" +
                "type='" + type + '\'' +
                ", num=" + num +
                ", strDesc='" + strDesc + '\'' +
                '}';
    }
}
