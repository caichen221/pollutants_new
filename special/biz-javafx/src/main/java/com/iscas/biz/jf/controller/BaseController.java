package com.iscas.biz.jf.controller;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/22 21:20
 * @since jdk1.8
 */
public class BaseController {

    /**
     * 获取当前的stage
     * */
    public Stage getCurrentStage(Parent parent) {
        Scene scene = parent.getScene();
        if (scene != null) {
            return (Stage) scene.getWindow();
        }
        return null;
    }

    /**
     * 设置某些按钮为不可用
     * */
    public static void disableButtons(Node ... nodes) {
        for (Node node : nodes) {
            node.setDisable(true);
        }
    }

    /**
     * 设置某些按钮为不可用
     * */
    public static void enableButtons(Node ... nodes) {
        for (Node node : nodes) {
            node.setDisable(false);
        }
    }

}
