package com.iscas.biz.schedule;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.iscas.base.biz.util.RegexUtils;
import com.iscas.biz.mapper.common.LogInfoMapper;
import com.iscas.biz.mp.aop.enable.ConditionalOnMybatis;
import com.iscas.common.tools.core.date.DateSafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/2/25 14:44
 * @since jdk1.8
 * 清理之前的log
 */
@Component
@Slf4j
@ConditionalOnMybatis
public class ClearLogTask {

    @Autowired
    private LogInfoMapper logInfoMapper;

    public void clearLog(String holdPeriod) {

        String clearBefore = getClearDateByPeriod(holdPeriod);
        UpdateWrapper wrapper = new UpdateWrapper();
        wrapper.le("operate_time", clearBefore);
        logInfoMapper.delete(wrapper);
    }

    private String getClearDateByPeriod(String cleanPeriod) {

        String period = RegexUtils.getStartNumber(cleanPeriod);
        String unit = cleanPeriod.replace(period, "");
        long time = Long.parseLong(period);

        if (unit.equalsIgnoreCase("s")) {
            //
        } else if (unit.equalsIgnoreCase("m")) {
            time = time * 60;
        } else if (unit.equalsIgnoreCase("h")) {
            time = time * 60 * 60;
        } else if (unit.equalsIgnoreCase("d")) {
            time = time * 60 * 60 * 24;
        } else {

        }
        String date = DateSafeUtils.format(new Date(new Date().getTime() - time));
        return date;
    }

}
