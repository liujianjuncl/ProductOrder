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
import java.util.regex.Pattern;

import com.nii.desktop.model.User;
import com.nii.desktop.model.Work;
import com.nii.desktop.model.WorkDetail;
import com.nii.desktop.util.conf.DBUtil;
import com.nii.desktop.util.conf.DateUtil;
import com.nii.desktop.util.conf.SessionUtil;
import com.nii.desktop.util.conf.Encoder;
import com.nii.desktop.util.conf.PropsUtil;
import com.nii.desktop.util.conf.UserUtil;
import com.nii.desktop.util.conf.WorkUtil;
import com.nii.desktop.util.ui.AlertUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

public class ModifyWorkDetailController implements Initializable {

    @FXML
    private AnchorPane modifyWorkDetailPane;

    @FXML
    private Label workDetailNo;

    @FXML
    private TextField workNoField;

    @FXML
    private TextField workNameField;

    @FXML
    private ComboBox unitCbox;

    @FXML
    private TextField unitPriceField;

    @FXML
    private TextField workNumField;

    @FXML
    private TextField workDetailMoney;

    @FXML
    private DatePicker workDate;
    
    @FXML
    private TextArea remarkField;

    private WorkDetail editWorkDetail;
    
    private User loginUser = SessionUtil.USERS.get("loginUser");

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 根据作业编号获取作业信息
        editWorkDetail = SessionUtil.WORKDETAILS.get("editWorkDetail");
        workDetailNo.setText(editWorkDetail.getWorkDetailNo());
        workDate.setValue(DateUtil.dateToLocalDate(editWorkDetail.getWorkDate()));
        workNoField.setText(editWorkDetail.getWorkNo());
        workNameField.setText(editWorkDetail.getWorkName());
        unitCbox.setValue(editWorkDetail.getUnit());
        unitPriceField.setText(Double.toString(editWorkDetail.getUnitPrice()));
        workNumField.setText(Integer.toString(editWorkDetail.getWorkNum()));
        workDetailMoney.setText(editWorkDetail.getWorkNum() * editWorkDetail.getUnitPrice() + "");
        remarkField.setText(editWorkDetail.getRemark());

        // 实际作业数量只允许输入数字
        workNumField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[0-9]*");
                if (!pattern.matcher(newValue).matches()) {
                    workNumField.setEditable(false);
                    workNumField.setText(oldValue);
                    workNumField.setEditable(true);
                }
                workDetailMoney.setText(Double
                        .toString(Integer.parseInt(workNumField.getText().trim()) * editWorkDetail.getUnitPrice()));
            }
        });
    }

    @FXML
    public void confirmBtnAction() {
        Timestamp workDateV = new Timestamp(DateUtil.localDateToDate(workDate.getValue()).getTime());

        if (!"是".equals(loginUser.getIsManager())) {
            if (workDateV.compareTo(DateUtil.lastMonth26Day()) < 0
                    || workDateV.compareTo(DateUtil.curMonth25Day()) > 0) {
                AlertUtil.alertInfoLater(PropsUtil.getMessage("workDetail.donot.modify.workDate"));
                return;
            }
        }

        String workNum = workNumField.getText().trim();
        String remark = remarkField.getText().trim();

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String sql = "update dbo.t_product_daily_work_detail set workNum = ?, money = ?, modifyUser = ?, modifyTime = ?, workDate = ?, modifyUserName = ?, remark = ? where workDetailNo = ?";
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, workNum);
            stmt.setDouble(2, Integer.parseInt(workNum) * editWorkDetail.getUnitPrice());
            stmt.setString(3, loginUser.getUserNo());
            stmt.setTimestamp(4, new Timestamp(new Date().getTime()));
            stmt.setTimestamp(5, new Timestamp(DateUtil.localDateToDate(workDate.getValue()).getTime()));
            stmt.setString(6, loginUser.getUserName());
            stmt.setString(7, remark);
            stmt.setString(8, editWorkDetail.getWorkDetailNo());

            stmt.executeUpdate();

        } catch (Exception e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
            return;
        } finally {
            DBUtil.release(conn, stmt);
        }

        AlertUtil.alertInfoLater(PropsUtil.getMessage("workDetail.modify.success"));
        WorkTableDetailViewController.getdialogStage().close();
        // 新建完成刷新数据
        ((WorkTableDetailViewController) SessionUtil.CONTROLLERS.get("WorkTableDetailViewController")).refresh();
    }

    @FXML
    public void cancelBtnAction() {
        WorkTableDetailViewController.getdialogStage().close();
        SessionUtil.WORKDETAILS.clear();
    }

}
