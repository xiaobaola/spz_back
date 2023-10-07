package com.spz.pojo.user;

/**
 * User:我吃饭的时候不饿
 * Date: 2023/10/02 19:43
 * Description:用户的身份信息
 */
public class User {
    private String name;//呢称
    private String phone;//电话
    private String passwd;//密码
    private int sex=0;//性别 0 为男 1为女  默认为0---->男

    public User(String name, String phone, String passwd, int sex) {
        this.name = name;
        this.phone = phone;
        this.passwd = passwd;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
