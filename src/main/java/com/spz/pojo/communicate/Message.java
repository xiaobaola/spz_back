package com.spz.pojo.communicate;

import java.io.Serializable;

/**
 * User:我吃饭的时候不饿
 * Date: 2023/10/02 19:56
 * Description:通讯的信息类
 */
public class Message implements Serializable {//实现Serializable接口，保证Message类的可序列化
    private static final long serialVersionUID = 1L;//保证其兼容性
    private String sender;//发送者
    private String getter;//接收者
    private String content;//内容
    private String sendTime;//发送时间
    private String mesType;//消息类型[可以在接口定义消息类型]


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }
}
