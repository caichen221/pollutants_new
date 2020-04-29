package com.iscas.biz.javafx;

import com.iscas.biz.javafx.splash.MySplashScreen;
import com.iscas.biz.javafx.view.LoginFxmlView;
import de.felixroske.jfxsupport.MyAbstractJavaFxApplicationSupport;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/20 11:22
 * @since jdk1.8
 */

@SpringBootApplication
//        (exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class FxApp extends MyAbstractJavaFxApplicationSupport {
    public static void main(String[] args) {
        launch(FxApp.class, LoginFxmlView.class, new MySplashScreen(), args);
    }

}