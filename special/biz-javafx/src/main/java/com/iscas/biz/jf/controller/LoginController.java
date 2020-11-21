package com.iscas.biz.jf.controller;

import com.iscas.biz.jf.support.FXMLController;
import com.iscas.biz.jf.support.GUIState;
import com.iscas.biz.jf.support.JavaFxApplicationSupport;
import com.iscas.biz.jf.view.IndexFxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/20 11:57
 * @since jdk1.8
 */
@FXMLController
@Slf4j
public class LoginController extends BaseController implements Initializable {

    @FXML
    private TextField userBox;
    @FXML
    private PasswordField pwdBox;

    private ResourceBundle resourceBundle;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = resources;
        Stage stage = GUIState.getStage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);


    }


    /**
     * 登陆
     * */
    public void processLogin(ActionEvent actionEvent) {

        try {
            JavaFxApplicationSupport.showNewView(IndexFxmlView.class);
        } catch (Throwable throwable) {
            JavaFxApplicationSupport.showError("跳转至主页面出错", throwable);
        }

    }

    /**
     * 取消登陆
     * */
    public void processCancel(ActionEvent actionEvent) {
        System.exit(0);
    }
}

