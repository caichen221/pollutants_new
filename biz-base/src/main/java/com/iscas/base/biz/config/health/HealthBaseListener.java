package com.iscas.base.biz.config.health;

import com.iscas.base.biz.service.common.SpringService;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/10 15:46
 * @since jdk1.8
 */
public class HealthBaseListener {
    protected volatile IHealthCheckHandler healthCheckHandler = null;

    public IHealthCheckHandler getHealthCheckHandler() {
        if (healthCheckHandler == null) {
            synchronized (LivenessStateListener.class) {
                if (healthCheckHandler == null) {
                    healthCheckHandler = SpringService.getApplicationContext().getBean(IHealthCheckHandler.class);
                }
            }
        }
        return healthCheckHandler;
    }
}
