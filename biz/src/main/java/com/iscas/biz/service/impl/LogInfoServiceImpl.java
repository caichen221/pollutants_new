package com.iscas.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iscas.base.biz.schedule.CronTaskRegister;
import com.iscas.base.biz.schedule.SchedulingRunnable;
import com.iscas.base.biz.util.CaffCacheUtils;
import com.iscas.base.biz.util.DateTimeUtils;
import com.iscas.biz.config.log.LogInfo;
import com.iscas.biz.mapper.LogInfoMapper;
import com.iscas.biz.model.Param;
import com.iscas.biz.service.LogInfoService;
import com.iscas.biz.service.ParamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/2/20 18:27
 * @since jdk1.8
 */
@Service
public class LogInfoServiceImpl extends ServiceImpl<LogInfoMapper, LogInfo> implements LogInfoService {
    @Autowired
    private CronTaskRegister cronTaskRegister;

    @Autowired
    private ParamService paramService;

    @Override
    public void deleteDataByTime(String holdPeriod) {

        holdPeriod = checkAndCachePeriod(holdPeriod);
        if (StringUtils.isBlank(holdPeriod)) {
            return;
        }

        SchedulingRunnable task = new SchedulingRunnable("clearLogTask", "clearLog", holdPeriod);
        //每天执行一次任务
        cronTaskRegister.addCronTask("clear_logInfo_task", task, "0 0 0 * * ?");

    }

    private String checkAndCachePeriod(String holdPeriod) {
        if (StringUtils.isBlank(holdPeriod)) {
            //period为空时，尝试从缓存中获取
            return getPeriodFromCache();
        } else {
            //period不为空时，放入缓存，更新数据库
            return cachePeriod(holdPeriod);
        }
    }

    private String cachePeriod(String holdPeriod) {

        //写入数据库
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("param_key", "sys.log.holdPeriod");
        Param param = paramService.getOne(wrapper);
        if (param == null) {
            Param data = new Param()
                    .setParamName("系统监控-访问日志-保留时长")
                    .setParamKey("sys.log.holdPeriod")
                    .setParamValue(holdPeriod)
                    .setParamType("系统类")
                    .setParamDesc("根据该参数清除之前的访问日志数据")
                    //TODO JWTUtils.getLoginUsername()
                    .setCreateBy("TEST")
                    .setCreateTime(DateTimeUtils.getDateStr(new Date()));
            paramService.save(data);
        } else {
            if (param.getParamValue().equals(holdPeriod)) {
                //清理周期没有改变
                return "";
            }
            Param updateParam = new Param()
                    .setParamValue(holdPeriod)
                    //JWTUtils.getLoginUsername()
                    .setUpdateBy("test")
                    .setUpdateTime(DateTimeUtils.getDateStr(new Date()));
            paramService.update(updateParam, wrapper);
        }

        //放入缓存
        Object dict = CaffCacheUtils.get("param");
        if (dict == null) {
            synchronized ("param") {
                dict = CaffCacheUtils.get("param");
                if (dict == null) {
                    dict = new ConcurrentHashMap<>();
                    CaffCacheUtils.set("param", dict);
                }
            }
        }
        ConcurrentHashMap map = (ConcurrentHashMap<Object, Object>) dict;
        map.put("sys.log.holdPeriod", holdPeriod);
        return holdPeriod;
    }

    private String getPeriodFromCache() {
        String holdPeriod = "";
        Object dict = CaffCacheUtils.get("param");
        if (dict != null) {
            Map<Object, Object> dictValue = (Map<Object, Object>) dict;
            Object period = dictValue.get("sys.log.holdPeriod");
            if (period != null) {
                holdPeriod = period.toString();
            }
        }
        //不存在时，赋值默认值，1天
        if (StringUtils.isBlank(holdPeriod)) {
            holdPeriod = "1d";
        }
        return holdPeriod;
    }


}
