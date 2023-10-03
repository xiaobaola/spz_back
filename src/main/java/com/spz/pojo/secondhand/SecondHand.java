package com.spz.pojo.secondhand;

/**
 * User:我吃饭的时候不饿
 * Date: 2023/10/02 20:07
 * Description:二手物品信息
 */
public class SecondHand {
    private int userId;//用户id
    private String info;//二手物品描述
    private int price;//二手物品价格
    private String type;//二手物品类型

    //创建二手物品实例对象时，需填入信息，价格，类型
    public SecondHand(String info, int price, String type) {
        this.info = info;
        this.price = price;
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
