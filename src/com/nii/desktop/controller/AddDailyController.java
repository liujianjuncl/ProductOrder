package com.nii.desktop.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.nii.desktop.model.DailyProcessTotalQty;
import com.nii.desktop.util.conf.DBUtil;
import com.nii.desktop.util.conf.DataManager;
import com.nii.desktop.util.conf.Encoder;
import com.nii.desktop.util.conf.PropsUtil;
import com.nii.desktop.util.conf.UserUtil;
import com.nii.desktop.util.ui.AlertUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class AddDailyController implements Initializable {

    @FXML
    private AnchorPane addUserPane;

    // 生产任务单号
    @FXML
    private TextField billNoTextField;

    // 计划生产数量
    @FXML
    private TextField planQuantity;

    // 物料代码
    @FXML
    private TextField materialCode;

    // 物料名称
    @FXML
    private TextField materialName;

    // 规格型号
    @FXML
    private TextField model;

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

    private int resProcessTotalQty1;

    private int resProcessTotalQty2;

    private int resProcessTotalQty3;

    private int processTotalQty1;

    private int processTotalQty2;

    private int processTotalQty3;

    private int processTotalQty4;

    private int processTotalQty5;

    private int processTotalQty6;

    private int processTotalQty7;

    private int processTotalQty8;

    private int processTotalQty9;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 根据生产任务单号获取生产任务单信息
        billNoTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    System.out.println(billNoTextField.getText().trim());
                    if (!getProductDailyInfo(billNoTextField.getText().trim())) {
                        AlertUtil.alertInfoLater(PropsUtil.getMessage("billNo.isNotExist"));
                    }
                }
            }
        });

        // 全工序实作数量逻辑处理
        allProcessHandler();

        // 工序逻辑处理
        processOnlyNumber();
    }

    // 根据生产任务单号查询出当前生产任务单的信息并显示到界面上
    public boolean getProductDailyInfo(String billNo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            String sql = "select c.FBillNo as billNo, icc.FNumber as materialCode, icc.FName as materialName, icc.FModel as model, "
                    + "c.FQty as planQuantity, item1.FName as resProcess1, c.FHeadSelfJ01104 as resProcessPrice1, "
                    + "item2.FName as resProcess2, c.FHeadSelfJ01106 as resProcessPrice2, "
                    + "item3.FName as resProcess3, c.FHeadSelfJ01108 as resProcessPrice3, "
                    + "c.FHeadSelfJ0185 as process1,c.FHeadSelfJ0186 as processPrice1, "
                    + "c.FHeadSelfJ0187 as process2, c.FHeadSelfJ0188 as processPrice2, "
                    + "c.FHeadSelfJ0189 as process3,c.FHeadSelfJ0190 as processPrice3, "
                    + "c.FHeadSelfJ0191 as process4,c.FHeadSelfJ0192 as processPrice4, "
                    + "c.FHeadSelfJ0193 as process5,c.FHeadSelfJ0194 as processPrice5, "
                    + "c.FHeadSelfJ0195 as process6,c.FHeadSelfJ0196 as processPrice6, "
                    + "c.FHeadSelfJ0197 as process7,c.FHeadSelfJ0198 as processPrice7, "
                    + "c.FHeadSelfJ0199 as process8,c.FHeadSelfJ01100 as processPrice8, "
                    + "c.FHeadSelfJ01101 as process9,c.FHeadSelfJ01102 as processPrice9 " + "from dbo.ICMO c "
                    + "left join dbo.t_ICItemCore icc on c.FItemID = icc.FItemID "
                    + "left join dbo.t_Item item1 on c.FHeadSelfJ01103 = item1.FitemID "
                    + "left join dbo.t_Item item2 on c.FHeadSelfJ01105 = item2.FitemID "
                    + "left join dbo.t_Item item3 on c.FHeadSelfJ01107 = item3.FitemID " + "where c.FBillNo = ? ";

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, billNo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                result = true;
                planQuantity.setText(String.valueOf(rs.getDouble("planQuantity")));
                materialCode.setText(rs.getString("materialCode"));
                materialName.setText(rs.getString("materialName"));
                model.setText(rs.getString("model"));
                resProcess1.setText(rs.getString("resProcess1"));
                resProcess2.setText(rs.getString("resProcess2"));
                resProcess3.setText(rs.getString("resProcess3"));
                process1.setText(rs.getString("process1"));
                process2.setText(rs.getString("process2"));
                process3.setText(rs.getString("process3"));
                process4.setText(rs.getString("process4"));
                process5.setText(rs.getString("process5"));
                process6.setText(rs.getString("process6"));
                process7.setText(rs.getString("process7"));
                process8.setText(rs.getString("process8"));
                process9.setText(rs.getString("process9"));
                resProcessPrice1.setText(rs.getString("resProcessPrice1"));
                resProcessPrice2.setText(rs.getString("resProcessPrice2"));
                resProcessPrice3.setText(rs.getString("resProcessPrice3"));
                processPrice1.setText(rs.getString("processPrice1"));
                processPrice2.setText(rs.getString("processPrice2"));
                processPrice3.setText(rs.getString("processPrice3"));
                processPrice4.setText(rs.getString("processPrice4"));
                processPrice5.setText(rs.getString("processPrice5"));
                processPrice6.setText(rs.getString("processPrice6"));
                processPrice7.setText(rs.getString("processPrice7"));
                processPrice8.setText(rs.getString("processPrice8"));
                processPrice9.setText(rs.getString("processPrice9"));
            }
        } catch (Exception e) {
            Logger.getLogger(AddDailyController.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {
            DBUtil.release(conn, stmt, rs);
        }

        return result;
    }

    // 将生产任务表中的数据同步到生产任务执行汇总表中
    public void addProductDailyTotal(String billNo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql1 = "select c.FBillNo as billNo, icc.FNumber as materialCode, icc.FName as materialName, icc.FModel as model, "
                    + "c.FQty as planQuantity, item1.FName as resProcess1, c.FHeadSelfJ01104 as resProcessPrice1, 0, "
                    + "item2.FName as resProcess2, c.FHeadSelfJ01106 as resProcessPrice2, 0, "
                    + "item3.FName as resProcess3, c.FHeadSelfJ01108 as resProcessPrice3, 0, "
                    + "(case when charindex('*', c.FHeadSelfJ0185) > 0 then null else c.FHeadSelfJ0185 end) as process1,"
                    + "(case when charindex('*', c.FHeadSelfJ0186) > 0 then null else c.FHeadSelfJ0186 end) as processPrice1, 0, "
                    + "(case when charindex('*', c.FHeadSelfJ0187) > 0 then null else c.FHeadSelfJ0187 end) as process2, "
                    + "(case when charindex('*', c.FHeadSelfJ0188) > 0 then null else c.FHeadSelfJ0188 end) as processPrice2, 0, "
                    + "(case when charindex('*', c.FHeadSelfJ0189) > 0 then null else c.FHeadSelfJ0189 end) as process3,"
                    + "(case when charindex('*', c.FHeadSelfJ0190) > 0 then null else c.FHeadSelfJ0190 end) as processPrice3, 0, "
                    + "(case when charindex('*', c.FHeadSelfJ0191) > 0 then null else c.FHeadSelfJ0191 end) as process4,"
                    + "(case when charindex('*', c.FHeadSelfJ0192) > 0 then null else c.FHeadSelfJ0192 end) as processPrice4, 0, "
                    + "(case when charindex('*', c.FHeadSelfJ0193) > 0 then null else c.FHeadSelfJ0193 end) as process5,"
                    + "(case when charindex('*', c.FHeadSelfJ0194) > 0 then null else c.FHeadSelfJ0194 end) as processPrice5, 0, "
                    + "(case when charindex('*', c.FHeadSelfJ0195) > 0 then null else c.FHeadSelfJ0195 end) as process6,"
                    + "(case when charindex('*', c.FHeadSelfJ0196) > 0 then null else c.FHeadSelfJ0196 end) as processPrice6, 0, "
                    + "(case when charindex('*', c.FHeadSelfJ0197) > 0 then null else c.FHeadSelfJ0197 end) as process7,"
                    + "(case when charindex('*', c.FHeadSelfJ0198) > 0 then null else c.FHeadSelfJ0198 end) as processPrice7, 0, "
                    + "(case when charindex('*', c.FHeadSelfJ0199) > 0 then null else c.FHeadSelfJ0199 end) as process8,"
                    + "(case when charindex('*', c.FHeadSelfJ01100) > 0 then null else c.FHeadSelfJ01100 end) as processPrice8, 0, "
                    + "(case when charindex('*', c.FHeadSelfJ01101) > 0 then null else c.FHeadSelfJ01101 end) as process9,"
                    + "(case when charindex('*', c.FHeadSelfJ01102) > 0 then null else c.FHeadSelfJ01102 end) as processPrice9, 0 "
                    + "from dbo.ICMO c left join dbo.t_ICItemCore icc on c.FItemID = icc.FItemID "
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

    // 全工序实作数量逻辑处理
    public void allProcessHandler() {
        // 复选框勾选时，全工序实作数量可以输入
        allProcessChkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // TODO Auto-generated method stub
                if (newValue) { // 当全工序勾选时，所有实作数量均不允许输入
                    allProcessQtyTextField.setDisable(false);
                    resProcessQty1.setDisable(true);
                    resProcessQty2.setDisable(true);
                    resProcessQty3.setDisable(true);
                    processQty1.setDisable(true);
                    processQty2.setDisable(true);
                    processQty3.setDisable(true);
                    processQty4.setDisable(true);
                    processQty5.setDisable(true);
                    processQty6.setDisable(true);
                    processQty7.setDisable(true);
                    processQty8.setDisable(true);
                    processQty9.setDisable(true);
                } else { // 当全工序去勾选时，所有实作数量可以输入
                    allProcessQtyTextField.setDisable(true);
                    resProcessQty1.setDisable(false);
                    resProcessQty2.setDisable(false);
                    resProcessQty3.setDisable(false);
                    processQty1.setDisable(false);
                    processQty2.setDisable(false);
                    processQty3.setDisable(false);
                    processQty4.setDisable(false);
                    processQty5.setDisable(false);
                    processQty6.setDisable(false);
                    processQty7.setDisable(false);
                    processQty8.setDisable(false);
                    processQty9.setDisable(false);
                }
            }
        });

        // 当全工序实作数量输入时，数量将同步给其他工序，除了改制工序
        allProcessQtyTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[0-9]*");
                // 只允许输入数字
                if (pattern.matcher(newValue).matches()) {
                    processQty1.setText(newValue);
                    processQty2.setText(newValue);
                    processQty3.setText(newValue);
                    processQty4.setText(newValue);
                    processQty5.setText(newValue);
                    processQty6.setText(newValue);
                    processQty7.setText(newValue);
                    processQty8.setText(newValue);
                    processQty9.setText(newValue);
                } else {
                    allProcessQtyTextField.setEditable(false);
                    allProcessQtyTextField.setText(oldValue);
                    allProcessQtyTextField.setEditable(true);
                }
            }
        });
    }

    // 工序输入框限制数字输入处理
    public void processOnlyNumber() {
        // 改制工序1数量只允许输入数字
        resProcessQty1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[0-9]*");
                if (!pattern.matcher(newValue).matches()) {
                    resProcessQty1.setEditable(false);
                    resProcessQty1.setText(oldValue);
                    resProcessQty1.setEditable(true);
                }
            }
        });

        // 改制工序2数量只允许输入数字
        resProcessQty2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[0-9]*");
                if (!pattern.matcher(newValue).matches()) {
                    resProcessQty2.setEditable(false);
                    resProcessQty2.setText(oldValue);
                    resProcessQty2.setEditable(true);
                }
            }
        });

        // 改制工序3数量只允许输入数字
        resProcessQty3.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[0-9]*");
                if (!pattern.matcher(newValue).matches()) {
                    resProcessQty3.setEditable(false);
                    resProcessQty3.setText(oldValue);
                    resProcessQty3.setEditable(true);
                }
            }
        });

        // 工序1数量只允许输入数字
        processQty1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[0-9]*");
                if (!pattern.matcher(newValue).matches()) {
                    processQty1.setEditable(false);
                    processQty1.setText(oldValue);
                    processQty1.setEditable(true);
                }
            }
        });

        // 工序2数量只允许输入数字
        processQty2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[0-9]*");
                if (!pattern.matcher(newValue).matches()) {
                    processQty2.setEditable(false);
                    processQty2.setText(oldValue);
                    processQty2.setEditable(true);
                }
            }
        });

        // 工序3数量只允许输入数字
        processQty3.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[0-9]*");
                if (!pattern.matcher(newValue).matches()) {
                    processQty3.setEditable(false);
                    processQty3.setText(oldValue);
                    processQty3.setEditable(true);
                }
            }
        });

        // 工序4数量只允许输入数字
        processQty4.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[0-9]*");
                if (!pattern.matcher(newValue).matches()) {
                    processQty4.setEditable(false);
                    processQty4.setText(oldValue);
                    processQty4.setEditable(true);
                }
            }
        });

        // 工序5数量只允许输入数字
        processQty5.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[0-9]*");
                if (!pattern.matcher(newValue).matches()) {
                    processQty5.setEditable(false);
                    processQty5.setText(oldValue);
                    processQty5.setEditable(true);
                }
            }
        });

        // 工序6数量只允许输入数字
        processQty6.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[0-9]*");
                if (!pattern.matcher(newValue).matches()) {
                    processQty6.setEditable(false);
                    processQty6.setText(oldValue);
                    processQty6.setEditable(true);
                }
            }
        });

        // 工序6数量只允许输入数字
        processQty7.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[0-9]*");
                if (!pattern.matcher(newValue).matches()) {
                    processQty7.setEditable(false);
                    processQty7.setText(oldValue);
                    processQty7.setEditable(true);
                }
            }
        });

        // 工序8数量只允许输入数字
        processQty8.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[0-9]*");
                if (!pattern.matcher(newValue).matches()) {
                    processQty8.setEditable(false);
                    processQty8.setText(oldValue);
                    processQty8.setEditable(true);
                }
            }
        });

        // 工序9数量只允许输入数字
        processQty9.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[0-9]*");
                if (!pattern.matcher(newValue).matches()) {
                    processQty9.setEditable(false);
                    processQty9.setText(oldValue);
                    processQty9.setEditable(true);
                }
            }
        });
    }

    // 判断所有工序的累计实作数量，前一个工序累计实作数量必须大于或等于后一个工序累计实作数量
    public String verifyProcess(String billNo) {
        DailyProcessTotalQty d = getProcessTotalQty(billNo);

        int resProTotalQty1 = "".equals(resProcessQty1.getText().trim()) ? 0
                : Integer.valueOf(resProcessQty1.getText().trim()) + d.getResProcessTotalQty1();
        int resProTotalQty2 = "".equals(resProcessQty2.getText().trim()) ? 0
                : Integer.valueOf(resProcessQty2.getText().trim()) + d.getResProcessTotalQty2();
        int resProTotalQty3 = "".equals(resProcessQty3.getText().trim()) ? 0
                : Integer.valueOf(resProcessQty3.getText().trim()) + d.getResProcessTotalQty3();
        int proTotalQty1 = "".equals(processQty1.getText().trim()) ? 0
                : Integer.valueOf(processQty1.getText().trim()) + d.getProcessTotalQty1();
        int proTotalQty2 = "".equals(processQty2.getText().trim()) ? 0
                : Integer.valueOf(processQty2.getText().trim()) + d.getProcessTotalQty2();
        int proTotalQty3 = "".equals(processQty3.getText().trim()) ? 0
                : Integer.valueOf(processQty3.getText().trim()) + d.getProcessTotalQty3();
        int proTotalQty4 = "".equals(processQty4.getText().trim()) ? 0
                : Integer.valueOf(processQty4.getText().trim()) + d.getProcessTotalQty4();
        int proTotalQty5 = "".equals(processQty5.getText().trim()) ? 0
                : Integer.valueOf(processQty5.getText().trim()) + d.getProcessTotalQty5();
        int proTotalQty6 = "".equals(processQty6.getText().trim()) ? 0
                : Integer.valueOf(processQty6.getText().trim()) + d.getProcessTotalQty6();
        int proTotalQty7 = "".equals(processQty7.getText().trim()) ? 0
                : Integer.valueOf(processQty7.getText().trim()) + d.getProcessTotalQty7();
        int proTotalQty8 = "".equals(processQty8.getText().trim()) ? 0
                : Integer.valueOf(processQty8.getText().trim()) + d.getProcessTotalQty8();
        int proTotalQty9 = "".equals(processQty9.getText().trim()) ? 0
                : Integer.valueOf(processQty9.getText().trim()) + d.getProcessTotalQty9();

        int[] totalQty = { proTotalQty1, proTotalQty2, proTotalQty3, proTotalQty4, proTotalQty5, proTotalQty6,
                proTotalQty7, proTotalQty8, proTotalQty9 };
        
        for(int i = 0; i < totalQty.length; i++) {
            for(int j = i + 1; j < totalQty.length; j++) {
                if(totalQty[i] < totalQty[j]) {
                    return "resProcessQty.asc";
                }
            }
        }
        return "OK";
    }

    // 获取所有工序累计实作数量值
    public DailyProcessTotalQty getProcessTotalQty(String billNo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        DailyProcessTotalQty dailyProcessTotalQty = null;

        try {
            String sql = "select * from t_product_daily_bill_total t where t.billNo = ? ";

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, billNo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int playQty = rs.getInt("planQuantity");
                int resProcessTotalQty1 = rs.getInt("resProcessTotalQty1");
                int resProcessTotalQty2 = rs.getInt("resProcessTotalQty2");
                int resProcessTotalQty3 = rs.getInt("resProcessTotalQty3");
                int processTotalQty1 = rs.getInt("processTotalQty1");
                int processTotalQty2 = rs.getInt("processTotalQty2");
                int processTotalQty3 = rs.getInt("processTotalQty3");
                int processTotalQty4 = rs.getInt("processTotalQty4");
                int processTotalQty5 = rs.getInt("processTotalQty5");
                int processTotalQty6 = rs.getInt("processTotalQty6");
                int processTotalQty7 = rs.getInt("processTotalQty7");
                int processTotalQty8 = rs.getInt("processTotalQty8");
                int processTotalQty9 = rs.getInt("processTotalQty9");

                dailyProcessTotalQty = new DailyProcessTotalQty(billNo, playQty, resProcessTotalQty1,
                        resProcessTotalQty2, resProcessTotalQty3, processTotalQty1, processTotalQty2, processTotalQty3,
                        processTotalQty4, processTotalQty5, processTotalQty6, processTotalQty7, processTotalQty8,
                        processTotalQty9);
            }
        } catch (Exception e) {
            Logger.getLogger(AddDailyController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }

        return dailyProcessTotalQty;
    }

    @FXML
    public void confirmBtnAction() {
        String message = verifyProcess(billNoTextField.getText());
        AlertUtil.alertInfoLater(PropsUtil.getMessage(message));
    }

    @FXML
    public void cancelBtnAction() {
        DailyManageController.getdialogStage().close();
    }

    public static void main(String[] args) {
//        new AddDailyController().addProductDailyTotal("制造-CB车间180813427");

        DailyProcessTotalQty d = new AddDailyController().getProcessTotalQty("CBZ1809013");
        System.out.println(d.getPlanQuantity());
        System.out.println(Integer.valueOf(""));
    }

}
