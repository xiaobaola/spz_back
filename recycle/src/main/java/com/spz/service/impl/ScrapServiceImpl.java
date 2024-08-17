package com.spz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.spz.entity.dto.ScrapDto;
import com.spz.entity.page.PageBean;
import com.spz.entity.scrap.Scrap;
import com.spz.entity.scrap.ScrapType;
import com.spz.mapper.ScrapMapper;
import com.spz.mapper.ScrapTypeMapper;
import com.spz.service.ScrapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScrapServiceImpl implements ScrapService {
    @Autowired
    private ScrapMapper scrapMapper;
    @Autowired
    private ScrapTypeMapper scrapTypeMapper; 

    @Override
    public ArrayList<Scrap> listByTypeId(Integer id) {
        return scrapMapper.listByTypeId(id);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String name, LocalDate begin, LocalDate end) {
        //1、设置分页参数
        PageHelper.startPage(page,pageSize);

        //2、正常查询
        List<Scrap> scrapList = scrapMapper.list(name, begin, end);

        //从scrap到scrapDto 封装成dto对象 多一个属性
        List<ScrapDto> scrapDtoList = scrapList.stream().map((item) -> {
            ScrapDto scrapDto = new ScrapDto();
            //对象拷贝
            BeanUtils.copyProperties(item, scrapDto);
            //获取各自分类id
            int scrapTypeId = item.getScrapTypeId();
            //根据id查询分类对象
            ScrapType scrapType = scrapTypeMapper.getById(scrapTypeId);

            //设置回收品类型名
            if (scrapType != null) {
                scrapDto.setScrapTypeName(scrapType.getName());
            }

            return scrapDto;
        }).collect(Collectors.toList());

        //为了获取total
        Page<Scrap> scrapPage = (Page<Scrap>) scrapList;

        //3、封装pageBean对象 补丁型 完善dto类
        PageBean pageBean = new PageBean(scrapPage.getTotal(), scrapDtoList);

        return pageBean;
    }

    @Override
    public Scrap getById(Integer id) {
        return scrapMapper.getById(id);
    }

    @Override
    public void updateById(Scrap scrap) {
        scrap.setUpdateTime(LocalDateTime.now());
        scrapMapper.updateById(scrap);
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        scrapMapper.deleteByIds(ids);
    }

    @Override
    public void insert(Scrap scrap) {
        //补全属性
        scrap.setUpdateTime(LocalDateTime.now());
        scrap.setCreateTime(LocalDateTime.now());
        scrapMapper.insert(scrap);
    }
}