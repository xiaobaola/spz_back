package com.spz.service.impl;

import com.spz.entity.safe.Token;
import com.spz.mapper.SafeMapper;
import com.spz.service.SafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SafeServiceImpl implements SafeService {
    @Autowired
    private SafeMapper safeMapper;

    @Override
    public Token getByToken(String token) {
        return safeMapper.getByToken(token);
    }

    @Override
    public void setToken(Token token) {
        safeMapper.insert(token);
    }
}
