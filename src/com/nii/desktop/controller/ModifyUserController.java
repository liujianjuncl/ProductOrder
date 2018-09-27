package com.nii.desktop.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nii.desktop.model.User;
import com.nii.desktop.util.conf.DBUtil;
import com.nii.desktop.util.conf.DataManager;
import com.nii.desktop.util.conf.Encoder;
import com.nii.desktop.util.conf.PropsUtil;
import com.nii.desktop.util.conf.UserUtil;
import com.nii.desktop.util.ui.AlertUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ModifyUserController implements Initializable {

    @FXML
    private AnchorPane modifyUserPane;

    @FXML
    private Label userNoLabelShow;

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button confirmBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<String> isPieceworkCbox;

    @FXML
    private ComboBox<String> isManagerCbox;

    @FXML
    private ComboBox<String> isDisableCbox;

    @FXML
    private CheckBox defaultPasswordCheckBox;

    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        isPieceworkCbox.setItems(FXCollections.observableArrayList("是", "否"));
        isManagerCbox.setItems(FXCollections.observableArrayList("是", "否"));
        isDisableCbox.setItems(FXCollections.observableArrayList("是", "否"));

        user = DataManager.USERS.get("editUser");
        userNoLabelShow.setText(user.getUserNo());
        userNameField.setText(user.getUserName());
        isPieceworkCbox.setValue(user.getIsPiecework());
        isManagerCbox.setValue(user.getIsManager());
        isDisableCbox.setValue(user.getIsDisable());

        /** 监听CheckBox */
        defaultPasswordCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    passwordField.setDisable(true);
                } else {
                    passwordField.setDisable(false);
                }

            }
        });
    }

    @FXML
    public void confirmBtnAction() {

        String userName = userNameField.getText();
        String password = passwordField.getText();
        String isPiecework = (String) isPieceworkCbox.getValue();
        String isManager = (String) isManagerCbox.getValue();
        String isDisable = (String) isDisableCbox.getValue();

        if (defaultPasswordCheckBox.isSelected()) {
            password = PropsUtil.getConfigValue("user.default.password"); // 默认密码
        }

        boolean result = UserUtil.verifyUserInfo(userName, password, isPiecework, isManager, isDisable);

        if (result) {
            Connection conn = null;
            PreparedStatement stmt = null;

            String userNo = user.getUserNo();

            try {
                String sql = "update dbo.t_product_daily_user set userName = ?, password = ?, isPiecework = ?, "
                        + "isManager = ?, isDisable = ?, lastModifyTime = ? where isDelete = 0 and userNo = ?";
                conn = DBUtil.getConnection();
                stmt = conn.prepareStatement(sql);

                stmt.setString(1, userName);
                stmt.setString(2, Encoder.encrypt(password));
                stmt.setInt(3, isPiecework == "是" ? 1 : 0);
                stmt.setInt(4, isManager == "是" ? 1 : 0);
                stmt.setInt(5, isDisable == "是" ? 1 : 0);
                stmt.setTimestamp(6, new Timestamp(new Date().getTime()));
                stmt.setString(7, userNo);

                stmt.execute();

            } catch (Exception e) {
                Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
                return;
            } finally {
                DBUtil.release(conn, stmt);
            }

            AlertUtil.alertInfoLater(PropsUtil.getMessage("user.modify.success"));
            UserTableViewController.getdialogStage().close();
            // 修改完成刷新数据
            ((UserTableViewController) DataManager.CONTROLLERS.get("UserTableViewController")).refresh();
            DataManager.USERS.remove("editUser");
        }
    }

    @FXML
    public void cancelBtnAction() {
        UserTableViewController.getdialogStage().close();
    }
}
