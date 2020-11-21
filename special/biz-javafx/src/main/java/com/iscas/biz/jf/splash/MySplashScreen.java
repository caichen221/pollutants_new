package com.iscas.biz.jf.splash;

import com.iscas.biz.jf.support.SplashScreen;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 启动页面
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/20 11:24
 * @since jdk1.8
 */
public class MySplashScreen extends SplashScreen {
    private static String DEFAULT_IMAGE = "/splash/splash.png";

    /**
     * Customize if the splash screen should be visible at all.
     *
     * @return true by default
     */
    @Override
    public boolean visible() {
        return true;
    }

    /**
     * Use your own splash image instead of the default one.
     *
     * @return "/splash/javafx.png"
     */
    @Override
    public String getImagePath() {
        String jarPath = System.getProperty("user.dir");
        File file = new File(jarPath, "splash/splash.png");
        if (file.exists()) {
            try {
                URL url = file.toURI().toURL();
                return url.toExternalForm();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return getClass().getResource(DEFAULT_IMAGE).toExternalForm();
//            return DEFAULT_IMAGE;
        }


    }

    @Override
    public Parent getParent() {
//        final ImageView imageView = new ImageView(getClass().getResource(getImagePath()).toExternalForm());
        final ImageView imageView = new ImageView(getImagePath());


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        imageView.setFitHeight(screenSize.getHeight());
        imageView.setFitWidth(screenSize.getWidth());
        final ProgressBar splashProgressBar = new ProgressBar();
        splashProgressBar.setPrefWidth(screenSize.getWidth());

        final VBox vbox = new VBox();
        vbox.setPrefHeight(screenSize.getHeight());
        vbox.setPrefWidth(screenSize.getWidth());
        vbox.getChildren().addAll(imageView, splashProgressBar);
//        VBox.setVgrow(imageView, Priority.ALWAYS);
        return vbox;
    }


}