package com.iscas.biz.filter;

import com.iscas.base.biz.filter.started.AbstractStartedFilter;
import com.iscas.base.biz.filter.started.StartedFilterComponent;
import com.iscas.biz.service.common.LogInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/2/25 14:40
 * @since jdk1.8
 */
@StartedFilterComponent(order = 4)
@Slf4j
public class ClearLogTaskFilter extends AbstractStartedFilter {

    @Autowired
    private LogInfoService logInfoService;

    @Override
    public void doFilterInternal(ApplicationContext applicationContext) {
        log.info("=============启动后测试过滤器4=================");
        clearLogTask();
        super.doFilterInternal(applicationContext);
    }

    private void clearLogTask() {
        logInfoService.deleteDataByTime(null);
    }

    @Override
    public String getName() {
        return "清理访问日志定时任务";
    }
}
