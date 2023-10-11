package com.spz.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spz.mapper.ScrapTypeMapper;
import com.spz.pojo.scrap.ScrapType;
import com.spz.service.ScrapTypeService;
import org.springframework.stereotype.Service;

@Service
public class ScrapTypeImpl extends ServiceImpl<ScrapTypeMapper, ScrapType> implements ScrapTypeService {
}
