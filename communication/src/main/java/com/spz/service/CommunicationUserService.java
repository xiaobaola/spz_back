package com.spz.service;

import com.spz.entity.dto.UserDto;
import com.spz.entity.page.PageBean;
import java.time.LocalDate;
import java.util.List;

public interface CommunicationUserService {


    PageBean page(Integer page, Integer pageSize, String username, LocalDate begin, LocalDate end);

    List<UserDto> getUserDtoListByInfo(String info, Integer userId);

    List<UserDto> getUserDtoListByUserId(Integer userId);
}
