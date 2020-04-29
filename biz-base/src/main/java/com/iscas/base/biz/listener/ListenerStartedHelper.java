package com.iscas.base.biz.listener;

import com.iscas.base.biz.filter.started.AbstractStartedFilter;
import com.iscas.base.biz.filter.started.StartedFilterComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/4/21 14:37
 * @since jdk1.8
 */
@Component
@Slf4j
public class ListenerStartedHelper {

    public void startFilters(ConfigurableApplicationContext applicationContext) {

        //获取AbstractStartedFileter类型的springBean
        Map<String, AbstractStartedFilter> startedFilterMap = applicationContext.getBeansOfType(AbstractStartedFilter.class);
        if (MapUtils.isNotEmpty(startedFilterMap)) {
            log.info("=========正在注册服务启动过滤器栈==============");
            Collection<AbstractStartedFilter> startedFilters = startedFilterMap.values();
            List<AbstractStartedFilter> startedFilterList = new ArrayList<>();
            for (AbstractStartedFilter startedFilter : startedFilters) {
                StartedFilterComponent annotation = startedFilter.getClass().getAnnotation(StartedFilterComponent.class);
                if (annotation == null) {
                    log.warn("过滤器：{}没有@StartedFilterComponent，不会被注册生效", startedFilter.getName() == null
                            ? startedFilter.getClass().getName() :startedFilter.getName());
                } else {
                    startedFilterList.add(startedFilter);
                }

            }
            log.debug("=============启动过滤器按照order顺序排序==============");
            Collections.sort(startedFilterList, (o1, o2) -> {
                StartedFilterComponent annotation1 = o1.getClass().getAnnotation(StartedFilterComponent.class);
                StartedFilterComponent annotation2 = o2.getClass().getAnnotation(StartedFilterComponent.class);
                if (Objects.equals(annotation1.order(), annotation2.order())) {
                    return -1;
                } else {
                    return annotation1.order() < annotation2.order() ? -1 : 1;
                }
            });

            log.debug("==================设置每个“启动后过滤器”的下一个过滤器的实体====================");
            for (int i = 0; i < startedFilterList.size(); i++) {
                if (i < startedFilterList.size() - 1) {
                    startedFilterList.get(i).setNextFilter(startedFilterList.get(i + 1));
                }
            }
            log.info("=========注册服务启动过滤器栈结束==============");
            startedFilterList.get(0).doFilterInternal(applicationContext);

        }

    }
}
