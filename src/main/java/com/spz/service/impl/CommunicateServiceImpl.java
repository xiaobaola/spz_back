package com.spz.service.impl;

import com.spz.mapper.CommunicateMapper;
import com.spz.service.CommunicateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunicateServiceImpl implements CommunicateService {
    @Autowired
    CommunicateMapper communicateMapper;
}
