package com.iscas.biz.test.elasticjob;

import com.iscas.base.biz.config.elasticjob.ConditionalOnElasticJob;
import com.iscas.base.biz.config.elasticjob.ElasticJobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testEsJob")
@ConditionalOnElasticJob()
public class TestEsJobController {
    @Autowired
    private ElasticJobHandler elasticJobHandler;


    /**
     * 动态添加任务逻辑,使用方式1
     */
    @RequestMapping("/test1")
    public void test1() {
        elasticJobHandler.addJob("111", "0/2 * * * * ?", 1, "test parameter", "0=a", MyJob2.class);
    }


    /**
     * 动态添加任务逻辑,使用方式2
     */
    @RequestMapping("/test2")
    public void test2() {
        elasticJobHandler.addJob2("111", "0/2 * * * * ?", 1, "test parameter", "0=a", MyJob2.class);
    }
}