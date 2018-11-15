package com.nii.desktop.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.nii.desktop.model.Daily;
import com.nii.desktop.model.DailyProcessQty;
import com.nii.desktop.model.User;
import com.nii.desktop.util.conf.DBUtil;
import com.nii.desktop.util.conf.DailyUtil;
import com.nii.desktop.util.conf.DateUtil;
import com.nii.desktop.util.conf.SessionUtil;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class ModifyDailyController implements Initializable {

    @FXML
    private AnchorPane addUserPane;

    // �����ձ�����
    @FXML
    private Label dailyNoLabel;

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

    // ��������
    @FXML
    private DatePicker productDate;

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

    // ������Ҫ�޸ĵ�ȫ���ձ�����
    Daily oldEditDaily = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // �����������񵥺Ż�ȡ����������Ϣ

        oldEditDaily = SessionUtil.DAILYS.get("editDaily");
        showProductDailyInfo(oldEditDaily);
    }

    // �����������񵥺Ų�ѯ����ǰ�������񵥵���Ϣ����ʾ��������
    public void showProductDailyInfo(Daily daily) {
        dailyNoLabel.setText(daily.getDailyNo());
        billNoTextField.setText(daily.getBillNo());
        planQuantity.setText(String.valueOf(daily.getPlanQty()));
        materialCode.setText(daily.getMaterialCode());
        materialName.setText(daily.getMaterialName());
        model.setText(daily.getModel());
        productDate.setValue(daily.getProDate());
        resProcess1.setText(daily.getResPro1());
        resProcess2.setText(daily.getResPro2());
        resProcess3.setText(daily.getResPro3());
        process1.setText(daily.getPro1());
        process2.setText(daily.getPro2());
        process3.setText(daily.getPro3());
        process4.setText(daily.getPro4());
        process5.setText(daily.getPro5());
        process6.setText(daily.getPro6());
        resProcessPrice1.setText(String.valueOf(daily.getResProPrice1()));
        resProcessPrice2.setText(String.valueOf(daily.getResProPrice2()));
        resProcessPrice3.setText(String.valueOf(daily.getResProPrice3()));
        processPrice1.setText(String.valueOf(daily.getProPrice1()));
        processPrice2.setText(String.valueOf(daily.getProPrice2()));
        processPrice3.setText(String.valueOf(daily.getProPrice3()));
        processPrice4.setText(String.valueOf(daily.getProPrice4()));
        processPrice5.setText(String.valueOf(daily.getProPrice5()));
        processPrice6.setText(String.valueOf(daily.getProPrice6()));
        resProcessQty1.setText(String.valueOf(daily.getResProQty1()));
        resProcessQty2.setText(String.valueOf(daily.getResProQty2()));
        resProcessQty3.setText(String.valueOf(daily.getResProQty3()));
        processQty1.setText(String.valueOf(daily.getProQty1()));
        processQty2.setText(String.valueOf(daily.getProQty2()));
        processQty3.setText(String.valueOf(daily.getProQty3()));
        processQty4.setText(String.valueOf(daily.getProQty4()));
        processQty5.setText(String.valueOf(daily.getProQty5()));
        processQty6.setText(String.valueOf(daily.getProQty6()));

        handlerProcessQtyField();
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

        if (result) {
            allProcessChkBox.setDisable(true);
        } else {
            allProcessChkBox.setDisable(false);
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

        allProcessHandler();
        processOnlyNumber();
    }

    // ȫ����ʵ�������߼�����
    public void allProcessHandler() {
        // ��ѡ��ѡʱ��ȫ����ʵ��������������
        allProcessChkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // TODO Auto-generated method stub
                if (newValue) { // ��ȫ����ѡʱ��ȫ����ʵ������������
                    allProcessQtyTextField.setDisable(false);
                } else { // ��ȫ����ȥ��ѡʱ��ȫ����ʵ��������������
                    allProcessQtyTextField.setDisable(true);
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

    // ���ȷ��ʱ���޸��ձ�
    @FXML
    public void confirmBtnAction() throws ParseException {
        String billNo = billNoTextField.getText().trim();
        String dailyNo = dailyNoLabel.getText();
        // ��ȡ���й���Ļ���ʵ������
        LocalDate proDate = productDate.getValue();

        // ����25��
        Date curDate25Day = DateUtil.curMonth25Day();
        // ����26��
        Date lastDate26Day = DateUtil.lastMonth26Day();

        Daily daily = DailyUtil.getDailyByNo(dailyNo);

        // ��ȡ��¼�û�
        User user = SessionUtil.USERS.get("loginUser");

        // ��ͨԱ��ֻ�����޸��ϸ���26�ŵ�����25�ŵ������ձ���
        if (!"��".equals(user.getIsManager())
                && lastDate26Day.compareTo(DateUtil.localDateToDate(daily.getProDate())) >= 0
                && curDate25Day.compareTo(DateUtil.localDateToDate(daily.getProDate())) <= 0) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("donot.daily.modify"));
            return;
        }

        String message = verifyProcess(billNo);

        if (!"OK".equals(message)) {
            AlertUtil.alertInfoLater(message);
        } else {
            DailyProcessQty dailyProcessQty = new DailyProcessQty(billNo, dailyNo, productDate.getValue(),
                    Integer.valueOf(planQuantity.getText()),
                    Integer.valueOf("".equals(resProcessQty1.getText()) ? "0" : resProcessQty1.getText()),
                    Integer.valueOf("".equals(resProcessQty2.getText()) ? "0" : resProcessQty2.getText()),
                    Integer.valueOf("".equals(resProcessQty3.getText()) ? "0" : resProcessQty3.getText()),
                    Integer.valueOf("".equals(processQty1.getText()) ? "0" : processQty1.getText()),
                    Integer.valueOf("".equals(processQty2.getText()) ? "0" : processQty2.getText()),
                    Integer.valueOf("".equals(processQty3.getText()) ? "0" : processQty3.getText()),
                    Integer.valueOf("".equals(processQty4.getText()) ? "0" : processQty4.getText()),
                    Integer.valueOf("".equals(processQty5.getText()) ? "0" : processQty5.getText()),
                    Integer.valueOf("".equals(processQty6.getText()) ? "0" : processQty6.getText()));

            boolean result = DailyUtil.modifyDaily(dailyProcessQty, oldEditDaily, proDate);
            if (result) {
                AlertUtil.alertInfoLater(PropsUtil.getMessage("daily.modify.success"));
                // �½����ˢ������
                ((DailyTableViewController) SessionUtil.CONTROLLERS.get("DailyTableViewController")).refresh();
                DailyTableViewController.getdialogStage().close();
                SessionUtil.DAILYS.remove("editDaily");
            } else {
                AlertUtil.alertInfoLater(PropsUtil.getMessage("daily.modify.fail") + dailyNo);
                DailyTableViewController.getdialogStage().close();
            }
        }
    }

    // �ж����й�����ۼ�ʵ��������ǰһ�������ۼ�ʵ������������ڻ���ں�һ�������ۼ�ʵ������
    public String verifyProcess(String billNo) {
        DailyProcessQty dpq = DailyUtil.getProcessTotalQty(billNo);
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
        SessionUtil.DAILYS.remove("editDaily");
    }

    public static void main(String[] args) {
//        new AddDailyController().addProductDailyTotal("����-CB����180813427");

        DailyProcessQty d = DailyUtil.getProcessTotalQty("CBZ1809013");
        System.out.println(d.getPlanQty());
        System.out.println(Integer.valueOf(""));
    }

}
