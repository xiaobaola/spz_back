package com.spz.entity.dto;

import com.spz.entity.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto extends User {
    private Integer status;
    @Override
    public String toString() {
        String str = super.toString();
        return str + "status="+ status;
    }
}
