package com.iscas.biz.jf.listener;

import com.iscas.biz.jf.splash.MySplashScreen;
import com.iscas.biz.jf.support.JavaFxApplicationSupport;
import com.iscas.biz.jf.view.LoginFxmlView;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/5/1 20:09
 * @since jdk1.8
 */
public class StartedListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        JavaFxApplicationSupport.applicationContext = event.getApplicationContext();
        JavaFxApplicationSupport.launch(JavaFxApplicationSupport.class, LoginFxmlView.class, new MySplashScreen(), new String[] {});
    }
}
