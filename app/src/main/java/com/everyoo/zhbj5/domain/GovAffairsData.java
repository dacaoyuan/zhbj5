package com.everyoo.zhbj5.domain;

import java.util.ArrayList;

/**
 * Created by abc on 2016/9/29.
 */
public class GovAffairsData {

    public int MsgCode;
    public String FailureCode;

    public ArrayList<ChlidrenData> Entity;


    @Override
    public String toString() {
        return "GovAffairsData{" +
                "MsgCode=" + MsgCode +
                ", Entity=" + Entity +
                '}';
    }

    public ArrayList<ChlidrenData> getEntity() {
        return Entity;
    }

    public void setEntity(ArrayList<ChlidrenData> entity) {
        Entity = entity;
    }

    public int getMsgCode() {
        return MsgCode;
    }

    public void setMsgCode(int msgCode) {
        MsgCode = msgCode;
    }

    public String getFailureCode() {
        return FailureCode;
    }

    public void setFailureCode(String failureCode) {
        FailureCode = failureCode;
    }


    public class ChlidrenData {
        public String NickName;
        public String UserName;


        @Override
        public String toString() {
            return "ChlidrenData{" +
                    "NickName='" + NickName + '\'' +
                    ", UserName='" + UserName + '\'' +
                    '}';
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String nickName) {
            NickName = nickName;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }
    }


}
