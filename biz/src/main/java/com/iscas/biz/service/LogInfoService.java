package com.iscas.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iscas.biz.model.LogInfo;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/2/20 18:26
 * @since jdk1.8
 */
public interface LogInfoService extends IService<LogInfo> {

    void deleteDataByTime(String holdPeriod);
}
