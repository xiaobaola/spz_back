package com.spz.manager.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.spz.common.BaseContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manager {
    private Integer id; //管理员唯一标识
    private String name; //名字
    private String username; //管理员用户名
    private String password; //密码
    private String phone; //手机号
    private String authority; //权限等级 1:超级管理员 2:二手物品内容审核 3:二手物品价格审核 4:回收员 5:客服 6:好友申请审核 7:接单员
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime; //更新时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime; //创建时间

    // session -> managerId
    public static int getManagerIdByRequest(int managerId, HttpServletRequest request){
        Manager manager = (Manager) request.getSession().getAttribute("manager");
        if(manager != null && manager.getId() != null) {
            managerId = manager.getId();
        }
        return managerId;
    }
    public static int getManagerIdByThread(int managerId){
        int id = BaseContext.getCurrentId();
        return id == -1 ? managerId : id;
    }
}
