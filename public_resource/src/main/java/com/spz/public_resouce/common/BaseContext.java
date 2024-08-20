package com.spz.public_resouce.common;

/*
 * 基于ThreadLocal封装工具类，用于保存和获取当前登录用户id
 * */
public class BaseContext {
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(int id) {
        threadLocal.set(id);
    }

    public static int getCurrentId() {
        return threadLocal.get();
    }

}
