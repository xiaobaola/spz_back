package com.spz.recycle.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.spz.recycle.mapper.ScrapTypeMapper;
import com.spz.recycle.entity.ScrapType;
import com.spz.recycle.service.ScrapTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ScrapTypeImpl implements ScrapTypeService {

    @Value("${spz.hasRedis}")
    private Boolean hasRedis;
    private ScrapTypeMapper scrapTypeMapper;

    private RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setScrapTypeMapper(ScrapTypeMapper scrapTypeMapper) {
        this.scrapTypeMapper = scrapTypeMapper;
    }

    public void add(ScrapType scrapType) {
        scrapType.setCreateTime(LocalDateTime.now());
        scrapType.setUpdateTime(LocalDateTime.now());
        scrapTypeMapper.insert(scrapType);
        // 如果配置了，则清理redis中的数据
        if (hasRedis) {
            redisTemplate.delete("scrapTypeList");
        }
    }

    @Override
    public ArrayList<ScrapType> getList() {
        // redis缓存 获取
        if (hasRedis && redisTemplate.hasKey("scrapTypeList")) {
            String json = (String) redisTemplate.opsForValue().get("scrapTypeList");
            // JSON 字符串是一个数组 将 JSONArray
            JSONArray jsonArray = JSON.parseArray(json);
            // 转换为 List<ScrapType>
            ArrayList<ScrapType> scrapTypes = (ArrayList<ScrapType>) jsonArray.toJavaList(ScrapType.class);
            // 缓存命中，返回数据
            log.info("service 从redis缓存获取回收品信息 {}", scrapTypes);
            return scrapTypes;
        }
        ArrayList<ScrapType> scrapTypes = scrapTypeMapper.selectList();
        // redis缓存 保存 过期时间15-30min 多人访问
        if (hasRedis) {
            // 序列化数据为 JSON 字符串
            String json = JSON.toJSONString(scrapTypes);
            // 将数据存入 Redis 缓存
            redisTemplate.opsForValue().set("scrapTypeList", json, 15, TimeUnit.MINUTES);
        }
        return scrapTypes;
    }

    @Override
    public void changeById(ScrapType scrapType) {
        scrapType.setUpdateTime(LocalDateTime.now());
        scrapTypeMapper.updateById(scrapType);
    }

    @Override
    public void deleteById(Integer id) {
        scrapTypeMapper.deleteById(id);
        // 如果配置了，则清理redis中的数据
        if (hasRedis) {
            redisTemplate.delete("scrapTypeList");
        }

    }

    @Override
    public ScrapType getById(int scrapTypeId) {
        return scrapTypeMapper.selectById(scrapTypeId);
    }
}
