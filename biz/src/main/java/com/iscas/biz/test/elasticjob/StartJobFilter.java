package com.iscas.biz.test.elasticjob;

import com.iscas.base.biz.config.elasticjob.ConditionalOnElasticJob;
import com.iscas.base.biz.config.elasticjob.ElasticJobHandler;
import com.iscas.base.biz.filter.started.AbstractStartedFilter;
import com.iscas.base.biz.filter.started.StartedFilterComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * 开启测试定时任务
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/3/26 19:21
 * @since jdk1.8
 */
@ConditionalOnElasticJob()
@StartedFilterComponent(order = 101)
@Slf4j
public class StartJobFilter extends AbstractStartedFilter {
    @Autowired
    private ElasticJobHandler elasticJobHandler;

    @Override
    public void doFilterInternal(ApplicationContext applicationContext) {
        elasticJobHandler.addJob("2222", "0/2 * * * * ?", 2, "test parameter", "0=a,1=b", MyJob2.class);
        super.doFilterInternal(applicationContext);
    }

    @Override
    public String getName() {
        return "测试elasticjob过滤器1";
    }
}
