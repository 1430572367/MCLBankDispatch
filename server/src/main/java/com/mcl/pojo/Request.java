package com.mcl.pojo;

import java.io.Serializable;

public class Request implements Serializable {

    private static final long nSerialVerUID = 1L;
    /*private int nSubReqID;
    private String strUserName;
    private String strProductName;
    private String strPhoneNumber;
    private String strAddr;

    public final int getSubReqID(){
        return nSubReqID;
    }

    public final void setSubReqID(int nSubReqID){
        this.nSubReqID = nSubReqID;
    }

    public final String getUserName(){
        return strUserName;
    }

    public final void setUserName(String strUserName){
        this.strUserName = strUserName;
    }

    public final String getProductName(){
        return strProductName;
    }

    public final void setProductName(String strProductName){
        this.strProductName = strProductName;
    }

    public final String getPhoneNumber(){
        return strPhoneNumber;
    }

    public final void setPhoneNumber(String strPhoneNumber){
        this.strPhoneNumber = strPhoneNumber;
    }

    public final String getAddress(){
        return strAddr;
    }

    public final void setAddress(String strAddr){
        this.strAddr = strAddr;
    }

    @Override
    public String toString(){
        return "----SubscribeReq[subReqID="+ nSubReqID + ",userName="+ strUserName
                +",productName="+strProductName+",phoneNumber="+strPhoneNumber
                +",address"+strAddr+"]";
    }*/
   private String type;
//   private int num;

    public void setType(String type) {
        this.type = type;
    }

//    public void setNum(int num) {
//        this.num = num;
//    }

    public String getType() {
        return type;
    }

//    public int getNum() {
//        return num;
//    }

    @Override
    public String toString() {
        return "Request{" +
                "type='" + type + '\'' +
                '}';
    }
}
