package com.spz.controller;

import com.spz.service.CommunicateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spz/communicate")
@Slf4j
public class CommunicateController {
    @Autowired
    CommunicateService communicateService;
}
