package com.nii.desktop.controller;

import com.nii.desktop.decorate.StageMove;
import com.nii.desktop.dialog.HostServerDialog;
import com.nii.desktop.model.HostServer;
import com.nii.desktop.model.User;
import com.nii.desktop.util.conf.DataManager;
import com.nii.desktop.util.conf.Encoder;
import com.nii.desktop.util.conf.PropertiesUtil;
import com.nii.desktop.util.conf.UserUtil;
import com.nii.desktop.util.ui.AlertUtil;
import com.nii.desktop.util.ui.ResourceLoader;
import com.nii.desktop.util.ui.UIManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ljj on 2018/9/15.
 */
public class LoginUIController implements Initializable {

    /* 日志 */
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginUIController.class);

    @FXML
    private TextField userNoTextField;

    @FXML
    private TextField passwordTextField;

    /* 下面面板 */
    @FXML
    private AnchorPane contentPanel;

    /* 初始化方法 */
    public void initialize(URL location, ResourceBundle resources) {
        new StageMove(UIManager.getPrimaryStage()).bindDrag(contentPanel);
    }

    /* 登录 */
    @FXML
    private void entryButtonClickAction() {
        String userNo = userNoTextField.getText().trim();
        String password = passwordTextField.getText().trim();

        if ("".equals(userNo.trim()) || "".equals(password.trim())) {
            AlertUtil.alertInfoLater(PropertiesUtil.getDefaultProperties().getProperty("login.user.pwd.isnull"));
            return;
        }

        User user = UserUtil.getUser(userNo);

        if (user == null) {
            AlertUtil.alertInfoLater(PropertiesUtil.getDefaultProperties().getProperty("login.user.notExist"));
            return;
        } else {
            if (!user.getPassword().equals(Encoder.encrypt(password))) {
                AlertUtil.alertInfoLater(PropertiesUtil.getDefaultProperties().getProperty("login.user.password.error"));
                return;
            }
            DataManager.USERS.put("loginUser", user);
            UIManager.switchMainUI();
        }
    }

    /* 重置 */
    @FXML
    private void clearButtonClickAction() {
        userNoTextField.clear();
        passwordTextField.clear();
    }

    /* 配置服务器 */
    @FXML
    private void configButtonClickAction() {
        HostServer hostServer = new HostServer("10.10.10.10");
        if (showHoserServerDialog(hostServer)) {
            AlertUtil.alertInfoLater(hostServer.getServerName());
        }
    }

    private boolean showHoserServerDialog(HostServer hostServer) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ResourceLoader.getFxmlResource("HostServerDialog.fxml"));

        Pane page = null;
        try {
            page = (Pane) loader.load();
        } catch (IOException e) {
            LOGGER.error("Load HostServerDialog.fxml failed.", e);
            return false;
        }

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Config Host Server");

        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(UIManager.getPrimaryStage());
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        HostServerDialog<HostServer> controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setParam(hostServer);

        dialogStage.showAndWait();

        return controller.isOkClicked();
    }

}
