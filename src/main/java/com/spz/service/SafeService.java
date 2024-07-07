package com.spz.service;

import com.spz.entity.safe.Token;

public interface SafeService {
    Token getByToken(String token);

    void setToken(Token token);
}
