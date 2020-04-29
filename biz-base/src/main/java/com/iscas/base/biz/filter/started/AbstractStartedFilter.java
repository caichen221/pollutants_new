package com.iscas.base.biz.filter.started;

import com.iscas.base.biz.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

/**
 * 服务启动后过滤器，
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/4/20 9:45
 * @since jdk1.8
 */
@Slf4j
public abstract class AbstractStartedFilter {
    protected AbstractStartedFilter nextFilter;

    /**
     * 调用下一个过滤器
     * */
    public void doFilter(ApplicationContext applicationContext) {
        if (nextFilter != null) {
            log.info("========服务启动后过滤器:{}调用开始==============", StringUtils.isEmpty(getName()) ?
                    this.getClass().getName() : getName());
            nextFilter.doFilterInternal(applicationContext);
        } else {
            log.info("========服务启动后过滤器栈调用结束==============");
        }
    }
    /**
     * 处理业务
     * */
    public void doFilterInternal(ApplicationContext applicationContext) {
        doFilter(applicationContext);
    }

    /**
     * 获取过滤器名字
     * */
    public abstract String getName();

    public void setNextFilter(AbstractStartedFilter nextFilter) {
        this.nextFilter = nextFilter;
    }
}

