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

import com.nii.desktop.model.User;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

public class ModifyUserController implements Initializable {

    @FXML
    AnchorPane modifyUserPane;

    @FXML
    Label userNoLabelShow;

    @FXML
    TextField userNameField;

    @FXML
    PasswordField passwordField;

    @FXML
    Button confirmBtn;

    @FXML
    Button cancelBtn;

    @FXML
    ComboBox isPieceworkCbox;

    @FXML
    ComboBox isManagerCbox;

    @FXML
    ComboBox isDisableCbox;

    @FXML
    CheckBox defaultPasswordCheckBox;

    private User user;

    static UserTableViewController tableViewController;

    public static void setTableViewController(UserTableViewController tableViewController) {
        tableViewController = tableViewController;
    }

    public static UserTableViewController getTableViewController() {
        return tableViewController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        isPieceworkCbox.setItems(FXCollections.observableArrayList("��", "��"));
        isManagerCbox.setItems(FXCollections.observableArrayList("��", "��"));
        isDisableCbox.setItems(FXCollections.observableArrayList("��", "��"));

        user = DataManager.USERS.get("editUser");
        System.out.println("================" + user.getUserNo());
        userNoLabelShow.setText(user.getUserNo());
        userNameField.setText(user.getUserName());
        isPieceworkCbox.setValue(user.getIsPiecework());
        isManagerCbox.setValue(user.getIsManager());
        isDisableCbox.setValue(user.getIsDisable());

        /** ����CheckBox */
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
            password = PropertiesUtil.getStringValue("user.default.password"); // Ĭ������
        }

        boolean result = UserUtil.verifyUserInfo(userName, password, isPiecework, isManager, isDisable);

        if (result) {
            Connection conn = null;
            PreparedStatement stmt = null;

            String userNo = user.getUserNo();

            try {
                String sql = "update dbo.t_product_daily_user set userName = ?, password = ?, isPiecework = ?, "
                        + "isManager = ?, isDisable = ?, lastModifyTime = ?, disableTime = ? where isDelete = 0 and userNo = ?";
                conn = DBUtil.getConnection();
                stmt = conn.prepareStatement(sql);

                stmt.setString(1, userName);
                stmt.setString(2, Encoder.EncoderByMd5(password));
                stmt.setInt(3, isPiecework == "��" ? 1 : 0);
                stmt.setInt(4, isManager == "��" ? 1 : 0);
                stmt.setInt(5, isDisable == "��" ? 1 : 0);
                stmt.setTimestamp(6, new Timestamp(new Date().getTime()));
                stmt.setTimestamp(7, isDisable == "��" ? new Timestamp(new Date().getTime()) : null);
                stmt.setString(8, userNo);

                stmt.execute();

            } catch (Exception e) {
                Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
                return;
            } finally {
                DBUtil.release(conn, stmt);
            }

            AlertUtil.alertInfoLater(PropertiesUtil.getStringValue("user.modify.success"));
            UserTableViewController.getdialogStage().close();
            // �޸����ˢ������
            ((UserTableViewController) DataManager.CONTROLLERS.get("UserTableViewController")).refresh();
        }
    }

    @FXML
    public void cancelBtnAction() {
        UserTableViewController.getdialogStage().close();
    }
}
