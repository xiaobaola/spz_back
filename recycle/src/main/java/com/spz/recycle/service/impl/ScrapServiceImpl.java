package com.spz.recycle.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.spz.recycle.entity.dto.ScrapDto;
import com.spz.entity.page.PageBean;
import com.spz.recycle.entity.Scrap;
import com.spz.recycle.entity.ScrapType;
import com.spz.recycle.mapper.ScrapMapper;
import com.spz.recycle.service.ScrapService;
import com.spz.recycle.service.ScrapTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ScrapServiceImpl implements ScrapService {
    @Value("${spz.hasRedis}")
    private Boolean hasRedis;
    private ScrapMapper scrapMapper;
    private ScrapTypeService scrapTypeService;

    private RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setScrapMapper(ScrapMapper scrapMapper) {
        this.scrapMapper = scrapMapper;
    }

    @Autowired
    public void setScrapTypeService(ScrapTypeService scrapTypeService) {
        this.scrapTypeService = scrapTypeService;
    }

    @Override
    public ArrayList<Scrap> getListByTypeId(Integer id) {
        // 可以考虑做出AOP
        // redis缓存 获取
        if (hasRedis && redisTemplate.hasKey("scrapListByTypeId_" + id)) {
            log.info("service 从redis缓存获取回收品信息");
            String json = (String) redisTemplate.opsForValue().get("scrapListByTypeId_" + id); ;
            // JSON 字符串是一个数组 将 JSONArray 转换为 List<SecondHandItemDto> 缓存命中，返回数据
            return (ArrayList<Scrap>) JSON.parseArray(json).toJavaList(Scrap.class);
        }
        ArrayList<Scrap> scraps = scrapMapper.selectListByTypeId(id);
        // redis缓存 保存 过期时间15-30min 多人访问
        if (hasRedis) {
            // 序列化数据为 JSON 字符串
            String json = JSON.toJSONString(scraps);
            // 将数据存入 Redis 缓存
            redisTemplate.opsForValue().set("scrapListByTypeId_" + id, json, 15, TimeUnit.MINUTES);
        }
        return scraps;
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String name, LocalDate begin, LocalDate end) {
        //1、设置分页参数
        PageHelper.startPage(page,pageSize);

        //2、正常查询
        List<Scrap> scrapList = scrapMapper.selectList(name, begin, end);

        //从scrap到scrapDto 封装成dto对象 多一个属性
        List<ScrapDto> scrapDtoList = scrapList.stream().map((item) -> {
            ScrapDto scrapDto = new ScrapDto();
            //对象拷贝
            BeanUtils.copyProperties(item, scrapDto);
            //获取各自分类id
            int scrapTypeId = item.getScrapTypeId();
            //根据id查询分类对象
            ScrapType scrapType = scrapTypeService.getById(scrapTypeId);

            //设置回收品类型名
            if (scrapType != null) {
                scrapDto.setScrapTypeName(scrapType.getName());
            }

            return scrapDto;
        }).collect(Collectors.toList());

        //为了获取total
        Page<Scrap> scrapPage = (Page<Scrap>) scrapList;

        //3、封装pageBean对象 补丁型 完善dto类

        return new PageBean(scrapPage.getTotal(), scrapDtoList);
    }

    @Override
    public Scrap getById(Integer id) {
        return scrapMapper.selectById(id);
    }

    @Override
    public void changeById(Scrap scrap) {
        scrap.setUpdateTime(LocalDateTime.now());
        scrapMapper.updateById(scrap);
        // 如果redis配置了则清理redis缓存
        if (hasRedis) {
            Set keys = redisTemplate.keys("scrapListByTypeId_*");
            redisTemplate.delete(keys);
        }
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        scrapMapper.deleteByIds(ids);
    }

    @Override
    public void add(Scrap scrap) {
        //补全属性
        scrap.setUpdateTime(LocalDateTime.now());
        scrap.setCreateTime(LocalDateTime.now());
        scrapMapper.insert(scrap);
    }
}
