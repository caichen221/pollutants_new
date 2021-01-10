package com.iscas.base.biz.config.health;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.event.EventListener;

//@Component
//@Lazy(value = false)
public class ReadinessStateListener extends HealthBaseListener {
    private volatile IHealthCheckHandler healthCheckHandler = null;

    @EventListener
    public void onStateChange(AvailabilityChangeEvent<ReadinessState> event) {
        switch (event.getState()) {
            case ACCEPTING_TRAFFIC:
                // create file /tmp/healthy
                getHealthCheckHandler().readinessAccept();
                break;
            case REFUSING_TRAFFIC:
                // remove file /tmp/healthy
                getHealthCheckHandler().readinessRefuse();
                break;
        }
    }

}