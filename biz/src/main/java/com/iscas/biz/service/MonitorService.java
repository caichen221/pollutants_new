package com.iscas.biz.service;

import java.util.Map;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/3/2 14:29
 * @since jdk1.8
 */
public interface MonitorService {

    void saveData(Map<Class, Object> data, Map<Class, Object> fixData);

    Object getData();
}
