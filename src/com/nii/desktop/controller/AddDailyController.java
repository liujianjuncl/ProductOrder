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

    // �������񵥺�
    @FXML
    private TextField billNoTextField;

    // �ƻ���������
    @FXML
    private TextField planQuantity;

    // ���ϴ���
    @FXML
    private TextField materialCode;

    // ��������
    @FXML
    private TextField materialName;

    // ����ͺ�
    @FXML
    private TextField model;

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
        // �����������񵥺Ż�ȡ����������Ϣ
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

        // ȫ����ʵ�������߼�����
        allProcessHandler();

        // �����߼�����
        processOnlyNumber();
    }

    // �����������񵥺Ų�ѯ����ǰ�������񵥵���Ϣ����ʾ��������
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

    // ������������е�����ͬ������������ִ�л��ܱ���
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

    // ȫ����ʵ�������߼�����
    public void allProcessHandler() {
        // ��ѡ��ѡʱ��ȫ����ʵ��������������
        allProcessChkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // TODO Auto-generated method stub
                if (newValue) { // ��ȫ����ѡʱ������ʵ������������������
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
                } else { // ��ȫ����ȥ��ѡʱ������ʵ��������������
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

        // ��ȫ����ʵ����������ʱ��������ͬ�����������򣬳��˸��ƹ���
        allProcessQtyTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[0-9]*");
                // ֻ������������
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

    // ��������������������봦��
    public void processOnlyNumber() {
        // ���ƹ���1����ֻ������������
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

        // ���ƹ���2����ֻ������������
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

        // ���ƹ���3����ֻ������������
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

        // ����1����ֻ������������
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

        // ����2����ֻ������������
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

        // ����3����ֻ������������
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

        // ����4����ֻ������������
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

        // ����5����ֻ������������
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

        // ����6����ֻ������������
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

        // ����6����ֻ������������
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

        // ����8����ֻ������������
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

        // ����9����ֻ������������
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

    // �ж����й�����ۼ�ʵ��������ǰһ�������ۼ�ʵ������������ڻ���ں�һ�������ۼ�ʵ������
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

    // ��ȡ���й����ۼ�ʵ������ֵ
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
//        new AddDailyController().addProductDailyTotal("����-CB����180813427");

        DailyProcessTotalQty d = new AddDailyController().getProcessTotalQty("CBZ1809013");
        System.out.println(d.getPlanQuantity());
        System.out.println(Integer.valueOf(""));
    }

}
