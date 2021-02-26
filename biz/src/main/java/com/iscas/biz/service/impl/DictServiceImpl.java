package com.iscas.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iscas.biz.mapper.DictMapper;
import com.iscas.biz.model.Dict;
import com.iscas.biz.service.DictService;
import org.springframework.stereotype.Service;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/2/25 16:13
 * @since jdk1.8
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
}
