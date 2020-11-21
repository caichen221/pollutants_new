package com.iscas.biz.config.log;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/2/21 18:16
 * @since jdk1.8
 */
@Service
public class SotreLogService implements IStoreLogService {

    @Override
    public void store(Map<String, Object> logInfo) {
        System.out.println(logInfo);
    }
}
