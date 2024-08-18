package com.spz.service;

import com.spz.entity.dto.MessageUserDto;
import com.spz.entity.page.PageBean;
import java.time.LocalDate;
import java.util.List;

public interface CommunicationUserService {


    PageBean page(Integer page, Integer pageSize, String username, LocalDate begin, LocalDate end);

    List<MessageUserDto> getUserDtoListByInfo(String info, Integer userId);

    List<MessageUserDto> getUserDtoListByUserId(Integer userId);
}
