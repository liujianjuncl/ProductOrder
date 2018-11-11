package com.nii.desktop.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.nii.desktop.model.Daily;
import com.nii.desktop.model.DailyProcessQty;
import com.nii.desktop.util.conf.DBUtil;
import com.nii.desktop.util.conf.DailyUtil;
import com.nii.desktop.util.conf.DateUtil;
import com.nii.desktop.util.conf.SessionUtil;
import com.nii.desktop.util.conf.PropsUtil;
import com.nii.desktop.util.ui.AlertUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
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

    // ��������
    @FXML
    private DatePicker productDate;

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

        // ��������Ĭ�ϵ���
        productDate.setValue(DateUtil.dateToLocalDate(new Date()));

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
                    + "c.FQty as planQuantity, item1.FName as resProcess1, "
                    + "CAST((case when charindex('*', c.FHeadSelfJ01104) > 0 then '0' else c.FHeadSelfJ01104 end) as decimal(18, 4)) as resProcessPrice1, "
                    + "item2.FName as resProcess2, "
                    + "CAST((case when charindex('*', c.FHeadSelfJ01106) > 0 then '0' else c.FHeadSelfJ01106 end) as decimal(18, 4)) as resProcessPrice2, "
                    + "item3.FName as resProcess3, "
                    + "CAST((case when charindex('*', c.FHeadSelfJ01108) > 0 then '0' else c.FHeadSelfJ01108 end) as decimal(18, 4)) as resProcessPrice3, "
                    + "c.FHeadSelfJ0185 as process1,"
                    + "CAST((case when charindex('*', c.FHeadSelfJ0186) > 0 then '0' else c.FHeadSelfJ0186 end) as decimal(18, 4)) as processPrice1, "
                    + "c.FHeadSelfJ0187 as process2, "
                    + "CAST((case when charindex('*', c.FHeadSelfJ0188) > 0 then '0' else c.FHeadSelfJ0188 end) as decimal(18, 4)) as processPrice2, "
                    + "c.FHeadSelfJ0189 as process3, "
                    + "CAST((case when charindex('*', c.FHeadSelfJ0190) > 0 then '0' else c.FHeadSelfJ0190 end) as decimal(18, 4)) as processPrice3, "
                    + "c.FHeadSelfJ0191 as process4, "
                    + "CAST((case when charindex('*', c.FHeadSelfJ0192) > 0 then '0' else c.FHeadSelfJ0192 end) as decimal(18, 4)) as processPrice4, "
                    + "c.FHeadSelfJ0193 as process5, "
                    + "CAST((case when charindex('*', c.FHeadSelfJ0194) > 0 then '0' else c.FHeadSelfJ0194 end) as decimal(18, 4)) as processPrice5, "
                    + "c.FHeadSelfJ0195 as process6, "
                    + "CAST((case when charindex('*', c.FHeadSelfJ0196) > 0 then '0' else c.FHeadSelfJ0196 end) as decimal(18, 4)) as processPrice6 "
                    + "from dbo.ICMO c left join dbo.t_ICItemCore icc on c.FItemID = icc.FItemID "
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
                resProcessPrice1.setText(rs.getString("resProcessPrice1"));
                resProcessPrice2.setText(rs.getString("resProcessPrice2"));
                resProcessPrice3.setText(rs.getString("resProcessPrice3"));
                processPrice1.setText(rs.getString("processPrice1"));
                processPrice2.setText(rs.getString("processPrice2"));
                processPrice3.setText(rs.getString("processPrice3"));
                processPrice4.setText(rs.getString("processPrice4"));
                processPrice5.setText(rs.getString("processPrice5"));
                processPrice6.setText(rs.getString("processPrice6"));
            }

            handlerProcessQtyField();
        } catch (Exception e) {
            Logger.getLogger(AddDailyController.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {
            DBUtil.release(conn, stmt, rs);
        }

        return result;
    }

    // ��������Ϊ��ʱ����Ӧ��ʵ����������򲻿�����
    public void handlerProcessQtyField() {
        boolean result = false;
        if ((resProcess1.getText() != null && !"*".equals(resProcess1.getText()) && !"".equals(resProcess1.getText()))
                && resProcess2.getText() != null && !"*".equals(resProcess2.getText())
                && !"".equals(resProcess2.getText()) && resProcess3.getText() != null
                && !"*".equals(resProcess3.getText()) && !"".equals(resProcess3.getText())) {
            result = true;
        }
        
        if(result) {
            allProcessChkBox.setDisable(true);
            allProcessQtyTextField.setDisable(true);
        } else {
            allProcessChkBox.setDisable(false);
            allProcessQtyTextField.setDisable(false);
        }

        if (resProcess1.getText() == null || "*".equals(resProcess1.getText()) || "".equals(resProcess1.getText())) {
            resProcessQty1.setDisable(true);
        } else {
            resProcessQty1.setDisable(false);
        }

        if (resProcess2.getText() == null || "*".equals(resProcess2.getText()) || "".equals(resProcess2.getText())) {
            resProcessQty2.setDisable(true);
        } else {
            resProcessQty2.setDisable(false);
        }

        if (resProcess3.getText() == null || "*".equals(resProcess3.getText()) || "".equals(resProcess3.getText())) {
            resProcessQty3.setDisable(true);
        } else {
            resProcessQty3.setDisable(false);
        }

        if (process1.getText() == null || "*".equals(process1.getText()) || "".equals(process1.getText()) || result) {
            processQty1.setDisable(true);
        } else {
            processQty1.setDisable(false);
        }

        if (process2.getText() == null || "*".equals(process2.getText()) || "".equals(process2.getText()) || result) {
            processQty2.setDisable(true);
        } else {
            processQty2.setDisable(false);
        }

        if (process3.getText() == null || "*".equals(process3.getText()) || "".equals(process3.getText()) || result) {
            processQty3.setDisable(true);
        } else {
            processQty3.setDisable(false);
        }

        if (process4.getText() == null || "*".equals(process4.getText()) || "".equals(process4.getText()) || result) {
            processQty4.setDisable(true);
        } else {
            processQty4.setDisable(false);
        }

        if (process5.getText() == null || "*".equals(process5.getText()) || "".equals(process5.getText()) || result) {
            processQty5.setDisable(true);
        } else {
            processQty5.setDisable(false);
        }

        if (process6.getText() == null || "*".equals(process6.getText()) || "".equals(process6.getText()) || result) {
            processQty6.setDisable(true);
        } else {
            processQty6.setDisable(false);
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
                    handlerProcessQtyField();
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
                    processQty1.setText((process1.getText() == null || "*".equals(process1.getText())
                            || "".equals(process1.getText())) ? "" : newValue);
                    processQty2.setText((process2.getText() == null || "*".equals(process2.getText())
                            || "".equals(process2.getText())) ? "" : newValue);
                    processQty3.setText((process3.getText() == null || "*".equals(process3.getText())
                            || "".equals(process3.getText())) ? "" : newValue);
                    processQty4.setText((process4.getText() == null || "*".equals(process4.getText())
                            || "".equals(process4.getText())) ? "" : newValue);
                    processQty5.setText((process5.getText() == null || "*".equals(process5.getText())
                            || "".equals(process5.getText())) ? "" : newValue);
                    processQty6.setText((process6.getText() == null || "*".equals(process6.getText())
                            || "".equals(process6.getText())) ? "" : newValue);
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
    }

    // ���ȷ��ʱ������ձ�
    @FXML
    public void confirmBtnAction() {
        String billNo = billNoTextField.getText().trim();
        
        if ("".equals(billNo.trim())) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("billNo.isnot.null"));
            return;
        }
        
        // ����ձ�֮ǰ�����Ƚ������������񵥵���Ϣͬ�����ձ����ܱ���
        DailyProcessQty dpq = DailyUtil.getProcessTotalQty(billNo);
        if (dpq == null) {
            DailyUtil.addProductDailyTotal(billNo);
            dpq = DailyUtil.getProcessTotalQty(billNo);
        }

        String message = verifyProcess(dpq);

        if (!"OK".equals(message)) {
            AlertUtil.alertInfoLater(message);
        } else {
            String dailyNo = DailyUtil.getDailyNo();
            int planQty = dpq.getPlanQty();

            Daily daily = new Daily(dailyNo, billNo, materialCode.getText(), materialName.getText(), model.getText(),
                    planQty, productDate.getValue(), resProcess1.getText(), Double.valueOf(resProcessPrice1.getText()),
                    Integer.valueOf("".equals(resProcessQty1.getText()) ? "0" : resProcessQty1.getText()),
                    resProcess2.getText(), Double.valueOf(resProcessPrice2.getText()),
                    Integer.valueOf("".equals(resProcessQty2.getText()) ? "0" : resProcessQty2.getText()),
                    resProcess3.getText(), Double.valueOf(resProcessPrice3.getText()),
                    Integer.valueOf("".equals(resProcessQty3.getText()) ? "0" : resProcessQty3.getText()),
                    process1.getText(), Double.valueOf(processPrice1.getText()),
                    Integer.valueOf("".equals(processQty1.getText()) ? "0" : processQty1.getText()), process2.getText(),
                    Double.valueOf(processPrice2.getText()),
                    Integer.valueOf("".equals(processQty2.getText()) ? "0" : processQty2.getText()), process3.getText(),
                    Double.valueOf(processPrice3.getText()),
                    Integer.valueOf("".equals(processQty3.getText()) ? "0" : processQty3.getText()), process4.getText(),
                    Double.valueOf(processPrice4.getText()),
                    Integer.valueOf("".equals(processQty4.getText()) ? "0" : processQty4.getText()), process5.getText(),
                    Double.valueOf(processPrice5.getText()),
                    Integer.valueOf("".equals(processQty5.getText()) ? "0" : processQty5.getText()), process6.getText(),
                    Double.valueOf(processPrice6.getText()),
                    Integer.valueOf("".equals(processQty6.getText()) ? "0" : processQty6.getText()),
                    SessionUtil.USERS.get("loginUser").getUserNo(), new Timestamp(new Date().getTime()),
                    "��".equals(SessionUtil.USERS.get("loginUser").getIsPiecework()) ? 1 : 0, 0,
                    DailyUtil.getDailyDetailSeq(billNo));

            boolean result = DailyUtil.addDaily(dpq, daily);
            if (result) {
                AlertUtil.alertInfoLater(PropsUtil.getMessage("daily.add.success") + dailyNo);
                // �½����ˢ������
                ((DailyTableViewController) SessionUtil.CONTROLLERS.get("DailyTableViewController")).refresh();
                DailyTableViewController.getdialogStage().close();
            } else {
                AlertUtil.alertInfoLater(PropsUtil.getMessage("daily.add.fail") + dailyNo);
                DailyTableViewController.getdialogStage().close();
            }
        }
    }

    // �ж����й�����ۼ�ʵ��������ǰһ�������ۼ�ʵ������������ڻ���ں�һ�������ۼ�ʵ������
    public String verifyProcess(DailyProcessQty dpq) {
        int playQty = dpq.getPlanQty();

        int resProQty1 = "".equals(resProcessQty1.getText().trim()) ? 0
                : Integer.valueOf(resProcessQty1.getText().trim());
        int resProQty2 = "".equals(resProcessQty2.getText().trim()) ? 0
                : Integer.valueOf(resProcessQty2.getText().trim());
        int resProQty3 = "".equals(resProcessQty3.getText().trim()) ? 0
                : Integer.valueOf(resProcessQty3.getText().trim());
        int proQty1 = "".equals(processQty1.getText().trim()) ? 0 : Integer.valueOf(processQty1.getText().trim());
        int proQty2 = "".equals(processQty2.getText().trim()) ? 0 : Integer.valueOf(processQty2.getText().trim());
        int proQty3 = "".equals(processQty3.getText().trim()) ? 0 : Integer.valueOf(processQty3.getText().trim());
        int proQty4 = "".equals(processQty4.getText().trim()) ? 0 : Integer.valueOf(processQty4.getText().trim());
        int proQty5 = "".equals(processQty5.getText().trim()) ? 0 : Integer.valueOf(processQty5.getText().trim());
        int proQty6 = "".equals(processQty6.getText().trim()) ? 0 : Integer.valueOf(processQty6.getText().trim());

        // ������¼���ʵ���������Ѿ���ɵ�ʵ���������
        int resProTotalQty1 = resProQty1 + dpq.getResProQty1();
        int resProTotalQty2 = resProQty2 + dpq.getResProQty2();
        int resProTotalQty3 = resProQty3 + dpq.getResProQty3();
        int proTotalQty1 = proQty1 + dpq.getProQty1();
        int proTotalQty2 = proQty2 + dpq.getProQty2();
        int proTotalQty3 = proQty3 + dpq.getProQty3();
        int proTotalQty4 = proQty4 + dpq.getProQty4();
        int proTotalQty5 = proQty5 + dpq.getProQty5();
        int proTotalQty6 = proQty6 + dpq.getProQty6();

        int[] resTotalQty = { resProTotalQty1, resProTotalQty2, resProTotalQty3 };

        int[] totalQty = { proTotalQty1, proTotalQty2, proTotalQty3, proTotalQty4, proTotalQty5, proTotalQty6 };

        String verifyIsNullRes = DailyUtil.verifyIsNull(resProQty1, resProQty2, resProQty3, proQty1, proQty2, proQty3,
                proQty4, proQty5, proQty6);

        if (!"OK".equals(verifyIsNullRes)) {
            return verifyIsNullRes;
        }

        return DailyUtil.verifyProcessQty(resTotalQty, totalQty, playQty);
    }

    @FXML
    public void cancelBtnAction() {
        DailyTableViewController.getdialogStage().close();
    }

    public static void main(String[] args) {
//        new AddDailyController().addProductDailyTotal("����-CB����180813427");

        DailyProcessQty d = DailyUtil.getProcessTotalQty("CBZ1809013");
        System.out.println(d.getPlanQty());
        System.out.println(Integer.valueOf(""));
    }

}
