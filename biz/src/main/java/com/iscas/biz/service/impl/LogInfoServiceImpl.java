package com.iscas.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iscas.biz.config.log.LogInfo;
import com.iscas.biz.mapper.LogInfoMapper;
import com.iscas.biz.service.LogInfoService;
import org.springframework.stereotype.Service;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/2/20 18:27
 * @since jdk1.8
 */
@Service
public class LogInfoServiceImpl extends ServiceImpl<LogInfoMapper, LogInfo> implements LogInfoService {
}
