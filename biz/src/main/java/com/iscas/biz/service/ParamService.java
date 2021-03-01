package com.iscas.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iscas.biz.model.Param;

import java.util.List;
import java.util.Map;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/2/26 9:51
 * @since jdk1.8
 */
public interface ParamService extends IService<Param> {

    boolean deleteData(List<Object> ids);

    String getParamValue(String paramKey);

}
