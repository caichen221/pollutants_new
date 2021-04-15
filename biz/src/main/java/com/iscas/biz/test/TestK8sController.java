package com.iscas.biz.test;

import com.iscas.common.tools.core.random.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/4/13 17:05
 * @since jdk1.8
 */
@RestController
@RequestMapping("/test/k8s")
public class TestK8sController {
    @Value("${sleep:200}")
    private long sleepTime;
    @Value("${random_str_len:1024}")
    private int len;
    @GetMapping
    public String test() {
        try {
            TimeUnit.MILLISECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return RandomStringUtils.randomStr(len);
    }
}
