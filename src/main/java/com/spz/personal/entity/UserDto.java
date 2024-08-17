package com.spz.personal.entity;

import com.spz.personal.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto extends User {
    private Integer status;
    private String greet;
    @Override
    public String toString() {
        String str = super.toString();
        return str + "status="+ status + "greet="+ greet;
    }
}
