package com.iscas.biz.filter;

import com.iscas.base.biz.filter.started.AbstractStartedFilter;
import com.iscas.base.biz.filter.started.StartedFilterComponent;
import com.iscas.base.biz.util.CaffCacheUtils;
import com.iscas.biz.model.Param;
import com.iscas.biz.service.ParamService;
import com.iscas.templet.view.table.TableSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/2/25 16:16
 * @since jdk1.8
 * 将字典表数据放入缓存
 */
@StartedFilterComponent(order = 3)
@Slf4j
public class ParamCacheFilter extends AbstractStartedFilter {

    @Autowired
    private ParamService paramService;

    @Override
    public void doFilterInternal(ApplicationContext applicationContext) {
        TableSearchRequest<Object> searchRequest = new TableSearchRequest<>();
        searchRequest.setPageNumber(1);
        searchRequest.setPageSize(Integer.MAX_VALUE);
        try {
            List<Param> paramList = paramService.getBaseMapper().selectList(null);

            Map<String, String> paramMap = Optional.ofNullable(paramList).map(params -> params.stream()
                    .collect(Collectors.toMap(param -> param.getParamKey(), param -> param.getParamValue(), (oldValue, newValue) -> newValue, ConcurrentHashMap::new)))
                    .orElse(new ConcurrentHashMap<>());

            CaffCacheUtils.set("param", paramMap);
        } catch (Exception e) {

        }
        super.doFilterInternal(applicationContext);
    }

    @Override
    public String getName() {
        return "启动服务时加载参数表到内存";
    }
}
