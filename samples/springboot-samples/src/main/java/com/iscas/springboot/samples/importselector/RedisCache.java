package com.iscas.springboot.samples.importselector;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/10/24 12:37
 * @since jdk1.8
 */
public class RedisCache implements ICache {
    @Override
    public String test(){
        return "=====测试Redis缓存====";
    }
}
