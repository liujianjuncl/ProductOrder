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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

public class AddDailyController implements Initializable {

    @FXML
    private AnchorPane addUserPane;

    @FXML
    private TextField billNoTextField;

    @FXML
    private CheckBox allProcessChkBox;

    @FXML
    private TextField allProcessQuantity;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 复选框勾选时，全工序实作数量可以输入
        allProcessChkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // TODO Auto-generated method stub
                if (newValue) {
                    allProcessQuantity.setDisable(false);
                } else {
                    allProcessQuantity.setDisable(true);
                }
            }
        });

        /* 当全工序实作数量失去焦点时，将计划生产数量分配给所有工序 */
        billNoTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                String billNo = billNoTextField.getText().trim();
                System.out.println(billNo);
//                if (!"".equals(billNo)) {
//                    System.out.println(oldValue + "============" + newValue);
//                    User user = UserUtil.getUser(userNo);
//                    if (user != null && !newValue) {
//                        userNameTextField.setText(user.getUserName());
//                    }
//                }
            }
        });
    }

    @FXML
    public void confirmBtnAction() {

    }

    @FXML
    public void cancelBtnAction() {
        DailyManageController.getdialogStage().close();
    }

}
