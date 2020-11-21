package com.iscas.base.biz.aop.norepeat.submit;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/27 22:19
 * @since jdk1.8
 */
public interface INoRepeatSubmitRedisHandler {
    boolean check(String key);
    void remove(String key);
}
