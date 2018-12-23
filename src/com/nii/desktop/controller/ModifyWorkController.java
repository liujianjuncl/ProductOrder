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

import com.nii.desktop.model.Work;
import com.nii.desktop.util.conf.DBUtil;
import com.nii.desktop.util.conf.SessionUtil;
import com.nii.desktop.util.conf.Encoder;
import com.nii.desktop.util.conf.PropsUtil;
import com.nii.desktop.util.conf.UserUtil;
import com.nii.desktop.util.conf.WorkUtil;
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

public class ModifyWorkController implements Initializable {

    @FXML
    private AnchorPane modifyWorkPane;

    @FXML
    private TextField workNameField;
    
    @FXML
    private TextField workNoField;

    @FXML
    private ComboBox unitCbox;

    @FXML
    private TextField unitPriceField;

    @FXML
    private ComboBox statusCbox;

    @FXML
    private Button confirmBtn;

    @FXML
    private Button cancelBtn;
    
    private Work editWork;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        unitCbox.setItems(FXCollections.observableArrayList("次", "分钟", "个"));
        statusCbox.setItems(FXCollections.observableArrayList("正常", "禁用"));
        
        editWork = SessionUtil.WORKS.get("editWork");
        workNoField.setText(editWork.getWorkNo());
        workNameField.setText(editWork.getWorkName());
        unitCbox.setValue(editWork.getUnit());
        unitPriceField.setText(Double.toString(editWork.getUnitPrice()));
        statusCbox.setValue(editWork.getStatus());
        
        if(!"是".equals(SessionUtil.USERS.get("loginUser").getIsManager())) {
            workNameField.setDisable(true);
            workNoField.setDisable(true);
            unitCbox.setDisable(true);
            unitPriceField.setDisable(true);
            statusCbox.setDisable(true);
        }
    }

    @FXML
    public void confirmBtnAction() {

        String workName = workNameField.getText();
        String unit = (String) unitCbox.getValue();
        String unitPrice = unitPriceField.getText();
        String status = (String) statusCbox.getValue();

        boolean result = WorkUtil.verifyWorkInfo(workName, unit, unitPrice, status);

        if (result) {
            Connection conn = null;
            PreparedStatement stmt = null;
            String workNo = editWork.getWorkNo();

            try {
                String sql = "update dbo.t_product_daily_work set workName = ?, unit = ?, unitPrice = ?, status = ? "
                        + " where workNo = ? ";
                conn = DBUtil.getConnection();
                stmt = conn.prepareStatement(sql);

                stmt.setString(1, workName);
                stmt.setString(2, unit);
                stmt.setDouble(3, Double.parseDouble(unitPrice));
                stmt.setInt(4, "禁用".equals(status) ? 1 : 0);
                stmt.setString(5, workNo);

                stmt.executeUpdate();

            } catch (Exception e) {
                Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
                return;
            } finally {
                DBUtil.release(conn, stmt);
            }

            AlertUtil.alertInfoLater(PropsUtil.getMessage("work.modify.success"));
            WorkTableViewController.getdialogStage().close();
            // 新建完成刷新数据
            ((WorkTableViewController) SessionUtil.CONTROLLERS.get("WorkTableViewController")).refresh();
        }
    }

    @FXML
    public void cancelBtnAction() {
        WorkTableViewController.getdialogStage().close();
        SessionUtil.WORKS.remove("editWork");
    }

}
