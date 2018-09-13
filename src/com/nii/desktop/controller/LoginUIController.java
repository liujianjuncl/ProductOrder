package com.nii.desktop.controller;

import com.nii.desktop.decorate.StageMove;
import com.nii.desktop.dialog.HostServerDialog;
import com.nii.desktop.model.host.HostServer;
import com.nii.desktop.util.conf.PropertiesUtil;
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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by wzj on 2016/12/25.
 */
public class LoginUIController implements Initializable {
    /**
     * ��־
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginUIController.class);

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passwordTextField;

    /**
     * �������
     */
    @FXML
    private AnchorPane contentPanel;

    /**
     * Called to initialize a controller after its root element has been completely
     * processed.
     *
     * @param location  The location used to resolve relative paths for the root
     *                  object, or <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or
     *                  <tt>null</tt> if
     */
    public void initialize(URL location, ResourceBundle resources) {
        new StageMove(UIManager.getPrimaryStage()).bindDrag(contentPanel);
    }

    /**
     * Click Entry Button
     */
    @FXML
    private void entryButtonClickAction() {
        String username = userNameTextField.getText().trim();
        String password = passwordTextField.getText().trim();

        if ("".equals(username) || "".equals(password)) {
            AlertUtil.alertInfoLater(PropertiesUtil.getDefaultProperties().getProperty("login.user.pwd.isnull"));
            return;
        }

        if (!(StringUtils.equals(userNameTextField.getText(), "1")
                && StringUtils.equals(passwordTextField.getText(), "1"))) {
            AlertUtil.alertErrorLater(PropertiesUtil.getDefaultProperties().getProperty("login.failed"));
            return;
        }

        UIManager.switchMainUI();
    }

    /**
     * Click Clear Button
     */
    @FXML
    private void clearButtonClickAction() {
        userNameTextField.clear();
        passwordTextField.clear();
    }

    /**
     * click config button
     */
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
