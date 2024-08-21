package com.spz.communication.service;

import com.spz.communication.entity.dto.MessageUserDto;

import java.util.List;

public interface CommunicationUserService {

    List<MessageUserDto> getUserDtoListByInfo(String info, Integer userId);

    List<MessageUserDto> getUserDtoListByUserId(Integer userId);
}
