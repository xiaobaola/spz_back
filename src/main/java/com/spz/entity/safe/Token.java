package com.spz.entity.safe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String token;
    private LocalDateTime createTime;
    public Token(String token) {
        this.token = token;
        createTime = LocalDateTime.now();
    }
}
