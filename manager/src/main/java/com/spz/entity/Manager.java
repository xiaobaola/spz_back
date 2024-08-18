package com.spz.entity;

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
    private LocalDateTime updateTime; //更新时间
    private LocalDateTime createTime; //创建时间

    // session -> managerId
    public static int getManagerIdByRequest(int managerId, HttpServletRequest request){
        Manager manager = (Manager) request.getSession().getAttribute("manager");
        if(manager != null && manager.getId() != null) {
            managerId = manager.getId();
        }
        return managerId;
    }
}
