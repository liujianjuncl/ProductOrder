package com.nii.desktop.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.nii.desktop.util.conf.DBUtil;
import com.nii.desktop.util.conf.DataManager;
import com.nii.desktop.util.conf.Encoder;
import com.nii.desktop.util.conf.PropsUtil;
import com.nii.desktop.util.conf.UserUtil;
import com.nii.desktop.util.ui.AlertUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddDailyController implements Initializable {

    @FXML
    private AnchorPane addUserPane;

    // �������񵥺�
    @FXML
    private TextField billNoTextField;

    // ���ϴ���
    @FXML
    private Label materialCode;

    // ��������
    @FXML
    private Label materialName;

    // ����ͺ�
    @FXML
    private Label model;

    // �ƻ���������
    @FXML
    private Label planQuantity;

    @FXML
    private CheckBox allProcessChkBox;

    // ȫ����ʵ������
    @FXML
    private TextField allProcessQtyTextField;

    // ���ƹ���1
    @FXML
    private Label resProcess1;

    // ���ƹ���2
    @FXML
    private Label resProcess2;

    // ���ƹ���3
    @FXML
    private Label resProcess3;

    // ����1
    @FXML
    private Label process1;

    // ����2
    @FXML
    private Label process2;

    // ����3
    @FXML
    private Label process3;

    // ����4
    @FXML
    private Label process4;

    // ����5
    @FXML
    private Label process5;

    // ����6
    @FXML
    private Label process6;

    // ����7
    @FXML
    private Label process7;

    // ����8
    @FXML
    private Label process8;

    // ����9
    @FXML
    private Label process9;

    // ���ƹ���1����
    @FXML
    private Label resProcessPrice1;

    // ���ƹ���2����
    @FXML
    private Label resProcessPrice2;

    // ���ƹ���3����
    @FXML
    private Label resProcessPrice3;

    // ����1����
    @FXML
    private Label processPrice1;

    // ����2����
    @FXML
    private Label processPrice2;

    // ����3����
    @FXML
    private Label processPrice3;

    // ����4����
    @FXML
    private Label processPrice4;

    // ����5����
    @FXML
    private Label processPrice5;

    // ����6����
    @FXML
    private Label processPrice6;

    // ����7����
    @FXML
    private Label processPrice7;

    // ����8����
    @FXML
    private Label processPrice8;

    // ����9����
    @FXML
    private Label processPrice9;

    // ���ƹ���1ʵ������
    @FXML
    private TextField resProcessQty1;

    // ���ƹ���2ʵ������
    @FXML
    private TextField resProcessQty2;

    // ���ƹ���3ʵ������
    @FXML
    private TextField resProcessQty3;

    // ����1ʵ������
    @FXML
    private TextField processQty1;

    // ����2ʵ������
    @FXML
    private TextField processQty2;

    // ����3ʵ������
    @FXML
    private TextField processQty3;

    // ����4ʵ������
    @FXML
    private TextField processQty4;

    // ����5ʵ������
    @FXML
    private TextField processQty5;

    // ����6ʵ������
    @FXML
    private TextField processQty6;

    // ����7ʵ������
    @FXML
    private TextField processQty7;

    // ����8ʵ������
    @FXML
    private TextField processQty8;

    // ����9ʵ������
    @FXML
    private TextField processQty9;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // ��ѡ��ѡʱ��ȫ����ʵ��������������
        allProcessChkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // TODO Auto-generated method stub
                if (newValue) {
                    allProcessQtyTextField.setDisable(false);
                } else {
                    allProcessQtyTextField.setDisable(true);
                }
            }
        });

        /* ��ȫ����ʵ������ʧȥ����ʱ�����ƻ�����������������й��� */
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

    public void addProductDailyTotal(String billNo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql1 = "select c.FBillNo as billNo, icc.FNumber as materialCode, icc.FName as materialName, icc.FModel as model, "
                    + "c.FQty as planQuantity, item1.FName as resProcess1, c.FHeadSelfJ01104 as resProcessPrice1, 0, "
                    + "item2.FName as resProcess2, c.FHeadSelfJ01106 as resProcessPrice2, 0, "
                    + "item3.FName as resProcess3, c.FHeadSelfJ01108 as resProcessPrice3, 0, "
                    + "c.FHeadSelfJ0185 as process1,c.FHeadSelfJ0186 as processPrice1, 0, "
                    + "c.FHeadSelfJ0187 as process2, c.FHeadSelfJ0188 as processPrice2, 0, "
                    + "c.FHeadSelfJ0189 as process3,c.FHeadSelfJ0190 as processPrice3, 0, "
                    + "c.FHeadSelfJ0191 as process4,c.FHeadSelfJ0192 as processPrice4, 0, "
                    + "c.FHeadSelfJ0193 as process5,c.FHeadSelfJ0194 as processPrice5, 0, "
                    + "c.FHeadSelfJ0195 as process6,c.FHeadSelfJ0196 as processPrice6, 0, "
                    + "c.FHeadSelfJ0197 as process7,c.FHeadSelfJ0198 as processPrice7, 0, "
                    + "c.FHeadSelfJ0199 as process8,c.FHeadSelfJ01100 as processPrice8, 0, "
                    + "c.FHeadSelfJ01101 as process9,c.FHeadSelfJ01102 as processPrice9, 0 " + "from dbo.ICMO c "
                    + "left join dbo.t_ICItemCore icc on c.FItemID = icc.FItemID "
                    + "left join dbo.t_Item item1 on c.FHeadSelfJ01103 = item1.FitemID "
                    + "left join dbo.t_Item item2 on c.FHeadSelfJ01105 = item2.FitemID "
                    + "left join dbo.t_Item item3 on c.FHeadSelfJ01107 = item3.FitemID " + "where c.FBillNo = ? ";

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql1);
            stmt.setString(1, billNo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String sql = "insert into dbo.t_product_daily_bill_total  " + sql1;
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, billNo);
                stmt.execute();
            }
        } catch (Exception e) {
            Logger.getLogger(AddDailyController.class.getName()).log(Level.SEVERE, null, e);
            return;
        } finally {
            DBUtil.release(conn, stmt, rs);
        }
    }

    @FXML
    public void confirmBtnAction() {

    }

    @FXML
    public void cancelBtnAction() {
        DailyManageController.getdialogStage().close();
    }

    public static void main(String[] args) {
        new AddDailyController().addProductDailyTotal("����-VP����180813428");
    }

}
