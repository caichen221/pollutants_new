package com.iscas.biz.javafx.controller;

import com.iscas.biz.javafx.FxApp;
import com.iscas.biz.javafx.view.IndexFxmlView;
import de.felixroske.jfxsupport.FXMLController;
import de.felixroske.jfxsupport.GUIState;
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
 * //TODO
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
            FxApp.showNewView(IndexFxmlView.class);
        } catch (Throwable throwable) {
            FxApp.showError("跳转至主页面出错", throwable);
        }

    }

    /**
     * 取消登陆
     * */
    public void processCancel(ActionEvent actionEvent) {
        System.exit(0);
    }
}

