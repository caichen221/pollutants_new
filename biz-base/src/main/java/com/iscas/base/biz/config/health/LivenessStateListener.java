package com.iscas.base.biz.config.health;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.context.event.EventListener;

//@Component
//@Lazy(value = false)
public class LivenessStateListener extends HealthBaseListener{

    @EventListener
    public void onStateChange(AvailabilityChangeEvent<LivenessState> event) {
        switch (event.getState()) {
            case CORRECT:
                // create file /tmp/healthy
                getHealthCheckHandler().livenessCorrect();
                break;
            case BROKEN:
                // remove file /tmp/healthy
                getHealthCheckHandler().livenessBroken();
                break;
            default:
        }
    }

}