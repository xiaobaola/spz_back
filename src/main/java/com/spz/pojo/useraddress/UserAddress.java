package com.spz.pojo.useraddress;

/**
 * User:我吃饭的时候不饿
 * Date: 2023/10/02 20:01
 * Description:用户的ip地址信息
 */
public class UserAddress {
    private int userId;//用户id
    private String address;//用户ip地址

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
