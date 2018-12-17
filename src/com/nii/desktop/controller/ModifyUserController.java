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
import com.nii.desktop.util.conf.SessionUtil;
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
    private ComboBox<String> isAuditorCbox;

    @FXML
    private ComboBox<String> isDisableCbox;

    @FXML
    private CheckBox defaultPasswordCheckBox;
    
    @FXML
    private ComboBox<String> auditorCbox;

    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        isPieceworkCbox.setItems(FXCollections.observableArrayList("是", "否"));
        isManagerCbox.setItems(FXCollections.observableArrayList("是", "否"));
        isAuditorCbox.setItems(FXCollections.observableArrayList("是", "否"));
        isDisableCbox.setItems(FXCollections.observableArrayList("是", "否"));
        auditorCbox.setItems(FXCollections.observableArrayList(UserUtil.getAllAuditors()));

        user = SessionUtil.USERS.get("editUser");
        User auditorUser = UserUtil.getUser(user.getAuditor());
        userNoLabelShow.setText(user.getUserNo());
        userNameField.setText(user.getUserName());
        isPieceworkCbox.setValue(user.getIsPiecework());
        isManagerCbox.setValue(user.getIsManager());
        isAuditorCbox.setValue(user.getIsAuditor());
        isDisableCbox.setValue(user.getIsDisable());
        if(auditorUser != null) {
            auditorCbox.setValue(auditorUser.getUserNo() + "-" + auditorUser.getUserName());
        }
        
        if(!"是".equals(SessionUtil.USERS.get("loginUser").getIsManager())) {
            userNameField.setDisable(true);
            isPieceworkCbox.setDisable(true);
            isManagerCbox.setDisable(true);
            isAuditorCbox.setDisable(true);
            isDisableCbox.setDisable(true);
            auditorCbox.setDisable(true);;
        }
        
       // 如果当前登录用户不是管理员，则不显示增加和删除按钮
        if (user.getUserNo().equals(SessionUtil.USERS.get("loginUser").getUserNo())) {
            isDisableCbox.setDisable(true);
        }

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
        String userName = userNameField.getText().trim();
        String password = passwordField.getText().trim();
        String isPiecework = (String) isPieceworkCbox.getValue();
        String isManager = (String) isManagerCbox.getValue();
        String isAuditor = (String) isAuditorCbox.getValue();
        String isDisable = (String) isDisableCbox.getValue();
        String auditorValue = ((String) auditorCbox.getValue());

        if (defaultPasswordCheckBox.isSelected()) {
            password = PropsUtil.getConfigValue("user.default.password"); // 默认密码
        }

        boolean result = UserUtil.verifyModifyUserInfo(userName, password, isPiecework, isManager, isDisable, isAuditor, auditorValue);

        if (result) {
            Connection conn = null;
            PreparedStatement stmt = null;

            String userNo = user.getUserNo();

            try {
                String sql = "update dbo.t_product_daily_user set userName = ?, isPiecework = ?, "
                        + "isManager = ?, isAuditor = ?, isDisable = ?, lastModifyTime = ?, auditor = ? ";
                if(!"".equals(password)) {
                    sql = sql + ", password = " + Encoder.encrypt(password);
                }
                sql = sql + "where isDelete = 0 and userNo = ?";
                conn = DBUtil.getConnection();
                stmt = conn.prepareStatement(sql);
                
                String auditorNo = auditorValue.substring(0, 4);

                stmt.setString(1, userName);
                stmt.setInt(2, isPiecework == "是" ? 1 : 0);
                stmt.setInt(3, isManager == "是" ? 1 : 0);
                stmt.setInt(4, isAuditor == "是" ? 1 : 0);
                stmt.setInt(5, isDisable == "是" ? 1 : 0);
                stmt.setTimestamp(6, new Timestamp(new Date().getTime()));
                stmt.setString(7, auditorNo);
                stmt.setString(8, userNo);

                stmt.executeUpdate();

            } catch (Exception e) {
                Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
                return;
            } finally {
                DBUtil.release(conn, stmt);
            }

            AlertUtil.alertInfoLater(PropsUtil.getMessage("user.modify.success"));
            UserTableViewController.getdialogStage().close();
            // 修改完成刷新数据
            ((UserTableViewController) SessionUtil.CONTROLLERS.get("UserTableViewController")).refresh();
            SessionUtil.USERS.remove("editUser");
        }
    }

    @FXML
    public void cancelBtnAction() {
        UserTableViewController.getdialogStage().close();
        SessionUtil.USERS.remove("editUser");
    }
}
