package com.spz.public_resouce.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/*
 *   自定义元对象处理器
 * */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /*
     * 插入操作自动填充
     *
     * */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段填充insert:{}", metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        // 数据存储在线程
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());

    }

    /*
     *   更新操作自动填充
     * */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段填充update:{}", metaObject.toString());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());

    }
}
