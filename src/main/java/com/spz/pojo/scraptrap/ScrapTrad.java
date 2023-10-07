package com.spz.pojo.scraptrap;

import java.util.Date;

/**
 * User:我吃饭的时候不饿
 * Date: 2023/10/02 19:47
 * Description:关于记录废品交易量
 */
public class ScrapTrad {
    private int status;//废品状态 0 是等待卖出 1是已卖出
    private int size;//尺寸大小
    private String type;//类型
    private int userId;//用户Id
    private Date createTime;//创建订单时间
    private Date updateTime;//更新订单时间

    //加入交易对象时，需要填入交易的重量（尺寸），类型
    public ScrapTrad(int size, String type) {
        this.size = size;
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
