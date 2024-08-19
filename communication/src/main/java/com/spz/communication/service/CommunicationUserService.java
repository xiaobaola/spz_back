package com.spz.communication.service;

import com.spz.communication.entity.dto.MessageUserDto;
import com.spz.public_resouce.entity.page.PageBean;
import java.time.LocalDate;
import java.util.List;

public interface CommunicationUserService {


    PageBean page(Integer page, Integer pageSize, String username, LocalDate begin, LocalDate end);

    List<MessageUserDto> getUserDtoListByInfo(String info, Integer userId);

    List<MessageUserDto> getUserDtoListByUserId(Integer userId);
}
