package com.spz.personal.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDto extends User {
    private Integer status;
    private String greet;
    @Override
    public String toString() {
        String str = super.toString();
        return str + "status="+ status + "greet="+ greet;
    }
}
