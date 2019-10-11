package com.nii.desktop.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nii.desktop.util.conf.DBUtil;
import com.nii.desktop.util.conf.SessionUtil;
import com.nii.desktop.util.conf.PropsUtil;
import com.nii.desktop.util.conf.WorkUtil;
import com.nii.desktop.util.ui.AlertUtil;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddWorkController implements Initializable {

    @FXML
    private AnchorPane addWorkPane;

    @FXML
    private TextField workNameField;

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

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        unitCbox.setItems(FXCollections.observableArrayList("次", "分钟", "个"));
        statusCbox.setItems(FXCollections.observableArrayList("正常", "禁用"));
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
            String workNo = null;

            try {
                String sql = "insert into dbo.t_product_daily_work (workNo, workName, unit, unitPrice, status)"
                        + "  values (?,?,?,?,?)";
                conn = DBUtil.getConnection();
                stmt = conn.prepareStatement(sql);
                workNo = WorkUtil.getWorkNo();

                stmt.setString(1, workNo);
                stmt.setString(2, workName);
                stmt.setString(3, unit);
                stmt.setDouble(4, Double.parseDouble(unitPrice));
                stmt.setInt(5, "禁用".equals(status) ? 1 : 0);

                stmt.executeUpdate();

            } catch (Exception e) {
                Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
                return;
            } finally {
                DBUtil.release(conn, stmt);
            }

            AlertUtil.alertInfoLater(PropsUtil.getMessage("work.add.success") + workNo);
            WorkTableViewController.getdialogStage().close();
            // 新建完成刷新数据
            ((WorkTableViewController) SessionUtil.CONTROLLERS.get("WorkTableViewController")).refresh();
        }
    }

    @FXML
    public void cancelBtnAction() {
        WorkTableViewController.getdialogStage().close();
    }

}
