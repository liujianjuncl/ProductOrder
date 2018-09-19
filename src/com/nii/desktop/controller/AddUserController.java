package com.nii.desktop.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nii.desktop.util.conf.DBUtil;
import com.nii.desktop.util.conf.DataManager;
import com.nii.desktop.util.conf.Encoder;
import com.nii.desktop.util.conf.PropertiesUtil;
import com.nii.desktop.util.conf.UserUtil;
import com.nii.desktop.util.ui.AlertUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

public class AddUserController implements Initializable {

    @FXML
    private AnchorPane addUserPane;

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button confirmBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox isPieceworkCbox;

    @FXML
    private ComboBox isManagerCbox;

    @FXML
    private CheckBox defaultPasswordCheckBox;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        isPieceworkCbox.setItems(FXCollections.observableArrayList("是", "否"));
        isManagerCbox.setItems(FXCollections.observableArrayList("是", "否"));

        /**监听CheckBox*/
        defaultPasswordCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                if(newValue) {
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

        if (defaultPasswordCheckBox.isSelected()) {
            password = PropertiesUtil.getConfigValue("user.default.password"); // 默认密码
        }
        System.out.println(userName + "===" + password + "===" + isPiecework + "===" + isManager);

        boolean result = UserUtil.verifyUserInfo(userName, password, isPiecework, isManager);

        if (result) {
            Connection conn = null;
            PreparedStatement stmt = null;
            String userNo = null;

            try {
                String sql = "insert into dbo.t_product_daily_user (userNo, userName, password, isPiecework, "
                        + "isManager, isDisable, createTime) values (?,?,?,?,?,?,?)";
                conn = DBUtil.getConnection();
                stmt = conn.prepareStatement(sql);
                userNo = UserUtil.getMaxUserNo();

                stmt.setString(1, userNo);
                stmt.setString(2, userName);
                stmt.setString(3, Encoder.encrypt(password));
                stmt.setInt(4, isPiecework == "是" ? 1 : 0);
                stmt.setInt(5, isManager == "是" ? 1 : 0);
                stmt.setInt(6, 0);
                stmt.setTimestamp(7, new Timestamp(new Date().getTime()));

                stmt.execute();

            } catch (Exception e) {
                Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
                return;
            } finally {
                DBUtil.release(conn, stmt);
            }

            AlertUtil.alertInfoLater(PropertiesUtil.getMessage("user.add.success") + userNo);
            UserTableViewController.getdialogStage().close();
            //新建完成刷新数据
            ((UserTableViewController) DataManager.CONTROLLERS.get("UserTableViewController")).refresh();
        }
    }

    @FXML
    public void cancelBtnAction() {
        UserTableViewController.getdialogStage().close();
    }

    

}
