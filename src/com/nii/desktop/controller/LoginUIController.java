package com.nii.desktop.controller;

import com.nii.desktop.decorate.StageMove;
import com.nii.desktop.dialog.HostServerDialog;
import com.nii.desktop.model.HostServer;
import com.nii.desktop.model.User;
import com.nii.desktop.util.conf.DataManager;
import com.nii.desktop.util.conf.Encoder;
import com.nii.desktop.util.conf.PropsUtil;
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

    /* ��־ */
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginUIController.class);

    @FXML
    private TextField userNoTextField;

    @FXML
    private TextField passwordTextField;

    /* ������� */
    @FXML
    private AnchorPane contentPanel;

    /* ��ʼ������ */
    public void initialize(URL location, ResourceBundle resources) {
        new StageMove(UIManager.getPrimaryStage()).bindDrag(contentPanel);
        
    }

    /* ��¼ */
    @FXML
    private void entryButtonClickAction() {
        String userNo = userNoTextField.getText().trim();
        String password = passwordTextField.getText().trim();

        if ("".equals(userNo.trim()) || "".equals(password.trim())) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("login.user.pwd.isnull"));
            return;
        }

        User user = UserUtil.getUser(userNo);

        if (user == null) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("login.user.notExist"));
            return;
        } else {
            if (!user.getPassword().equals(Encoder.encrypt(password))) {
                AlertUtil.alertInfoLater(PropsUtil.getMessage("login.user.password.error"));
                return;
            }
            DataManager.USERS.put("loginUser", user);
            UIManager.switchMainUI();
        }
    }

    /* ���� */
    @FXML
    private void clearButtonClickAction() {
        userNoTextField.clear();
        passwordTextField.clear();
    }

    /* ���÷����� */
    @FXML
    private void configButtonClickAction() {
        HostServer hostServer = new HostServer(PropsUtil.getConfigValue("host"));
        if (showHoserServerDialog(hostServer)) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("host.server") + hostServer.getServerName());
            PropsUtil.updateHostAndDBUrl("host", hostServer.getServerName());
        }
    }

    private boolean showHoserServerDialog(HostServer hostServer) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ResourceLoader.getFxmlResource("HostServerDialog.fxml"));

        Pane page = null;
        try {
            page = (Pane) loader.load();
        } catch (IOException e) {
            LOGGER.error("����HostServerDialog.fxml�ļ�ʧ��", e);
            return false;
        }

        Stage dialogStage = new Stage();
        dialogStage.setTitle("��������ַ����");

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
    
    @FXML
    public void userOnMouseExited() {
        System.out.println("userOnMouseExited");
    }

}
