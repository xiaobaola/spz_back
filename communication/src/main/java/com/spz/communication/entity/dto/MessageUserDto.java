package com.spz.communication.entity.dto;

import com.spz.personal.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MessageUserDto extends User {
    private Integer status;
    private String greet;
    @Override
    public String toString() {
        String str = super.toString();
        return str + "status="+ status + "greet="+ greet;
    }
}
