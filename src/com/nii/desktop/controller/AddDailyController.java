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

    // 生产任务单号
    @FXML
    private TextField billNoTextField;

    // 物料代码
    @FXML
    private Label materialCode;

    // 物料名称
    @FXML
    private Label materialName;

    // 规格型号
    @FXML
    private Label model;

    // 计划生产数量
    @FXML
    private Label planQuantity;

    @FXML
    private CheckBox allProcessChkBox;

    // 全工序实作数量
    @FXML
    private TextField allProcessQtyTextField;

    // 改制工序1
    @FXML
    private Label resProcess1;

    // 改制工序2
    @FXML
    private Label resProcess2;

    // 改制工序3
    @FXML
    private Label resProcess3;

    // 工序1
    @FXML
    private Label process1;

    // 工序2
    @FXML
    private Label process2;

    // 工序3
    @FXML
    private Label process3;

    // 工序4
    @FXML
    private Label process4;

    // 工序5
    @FXML
    private Label process5;

    // 工序6
    @FXML
    private Label process6;

    // 工序7
    @FXML
    private Label process7;

    // 工序8
    @FXML
    private Label process8;

    // 工序9
    @FXML
    private Label process9;

    // 改制工序1单价
    @FXML
    private Label resProcessPrice1;

    // 改制工序2单价
    @FXML
    private Label resProcessPrice2;

    // 改制工序3单价
    @FXML
    private Label resProcessPrice3;

    // 工序1单价
    @FXML
    private Label processPrice1;

    // 工序2单价
    @FXML
    private Label processPrice2;

    // 工序3单价
    @FXML
    private Label processPrice3;

    // 工序4单价
    @FXML
    private Label processPrice4;

    // 工序5单价
    @FXML
    private Label processPrice5;

    // 工序6单价
    @FXML
    private Label processPrice6;

    // 工序7单价
    @FXML
    private Label processPrice7;

    // 工序8单价
    @FXML
    private Label processPrice8;

    // 工序9单价
    @FXML
    private Label processPrice9;

    // 改制工序1实作数量
    @FXML
    private TextField resProcessQty1;

    // 改制工序2实作数量
    @FXML
    private TextField resProcessQty2;

    // 改制工序3实作数量
    @FXML
    private TextField resProcessQty3;

    // 工序1实作数量
    @FXML
    private TextField processQty1;

    // 工序2实作数量
    @FXML
    private TextField processQty2;

    // 工序3实作数量
    @FXML
    private TextField processQty3;

    // 工序4实作数量
    @FXML
    private TextField processQty4;

    // 工序5实作数量
    @FXML
    private TextField processQty5;

    // 工序6实作数量
    @FXML
    private TextField processQty6;

    // 工序7实作数量
    @FXML
    private TextField processQty7;

    // 工序8实作数量
    @FXML
    private TextField processQty8;

    // 工序9实作数量
    @FXML
    private TextField processQty9;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 复选框勾选时，全工序实作数量可以输入
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
        new AddDailyController().addProductDailyTotal("制造-VP车间180813428");
    }

}
