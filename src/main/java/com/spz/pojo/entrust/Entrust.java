package com.spz.pojo.entrust;

/**
 * User:我吃饭的时候不饿
 * Date: 2023/10/02 20:04
 * Description:关于发布委托的信息类
 */
public class Entrust {
    private String info;//委托的需求（文字）
    private String price;//委托的价格
    private int status;//委托的状态 0 是未接 1是已接 2是已完成
    private int userId;//委托人id



    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
