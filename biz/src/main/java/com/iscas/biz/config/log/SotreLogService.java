package com.iscas.biz.config.log;

import com.iscas.biz.service.LogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/2/21 18:16
 * @since jdk1.8
 */
@Service
public class SotreLogService implements IStoreLogService {
    @Autowired
    private LogInfoService logInfoService;

    @Override
    public void store(LogInfo logInfo) {
        LogManager.executeLog(() -> logInfoService.save(logInfo));
    }
}
