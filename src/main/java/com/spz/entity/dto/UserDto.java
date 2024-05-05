package com.spz.entity.dto;

import com.spz.entity.user.User;
import lombok.Data;

@Data
public class UserDto extends User {
    private Integer status;
}
