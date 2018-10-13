package com.nii.desktop.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.nii.desktop.model.Daily;
import com.nii.desktop.model.DailyProcessTotalQty;
import com.nii.desktop.util.conf.DBUtil;
import com.nii.desktop.util.conf.DailyUtil;
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
                    + "CAST((case when charindex('*', c.FHeadSelfJ0196) > 0 then '0' else c.FHeadSelfJ0196 end) as decimal(18, 4)) as processPrice6, "
                    + "c.FHeadSelfJ0197 as process7, "
                    + "CAST((case when charindex('*', c.FHeadSelfJ0198) > 0 then '0' else c.FHeadSelfJ0198 end) as decimal(18, 4)) as processPrice7, "
                    + "c.FHeadSelfJ0199 as process8, "
                    + "CAST((case when charindex('*', c.FHeadSelfJ01100) > 0 then '0' else c.FHeadSelfJ01100 end) as decimal(18, 4)) as processPrice8, "
                    + "c.FHeadSelfJ01101 as process9, "
                    + "CAST((case when charindex('*', c.FHeadSelfJ01100) > 0 then '0' else c.FHeadSelfJ01100 end) as decimal(18, 4)) as processPrice9 "
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

            handlerProcessQtyField();
        } catch (Exception e) {
            Logger.getLogger(AddDailyController.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {
            DBUtil.release(conn, stmt, rs);
        }

        return result;
    }

    // 工序名称为空时，对应的实作数量输入框不可输入
    public void handlerProcessQtyField() {
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

        if (process1.getText() == null || "*".equals(process1.getText()) || "".equals(process1.getText())) {
            processQty1.setDisable(true);
        } else {
            processQty1.setDisable(false);
        }

        if (process2.getText() == null || "*".equals(process2.getText()) || "".equals(process2.getText())) {
            processQty2.setDisable(true);
        } else {
            processQty2.setDisable(false);
        }

        if (process3.getText() == null || "*".equals(process3.getText()) || "".equals(process3.getText())) {
            processQty3.setDisable(true);
        } else {
            processQty3.setDisable(false);
        }

        if (process4.getText() == null || "*".equals(process4.getText()) || "".equals(process4.getText())) {
            processQty4.setDisable(true);
        } else {
            processQty4.setDisable(false);
        }

        if (process5.getText() == null || "*".equals(process5.getText()) || "".equals(process5.getText())) {
            processQty5.setDisable(true);
        } else {
            processQty5.setDisable(false);
        }

        if (process6.getText() == null || "*".equals(process6.getText()) || "".equals(process6.getText())) {
            processQty6.setDisable(true);
        } else {
            processQty6.setDisable(false);
        }

        if (process7.getText() == null || "*".equals(process7.getText()) || "".equals(process7.getText())) {
            processQty7.setDisable(true);
        } else {
            processQty7.setDisable(false);
        }

        if (process8.getText() == null || "*".equals(process8.getText()) || "".equals(process8.getText())) {
            processQty8.setDisable(true);
        } else {
            processQty8.setDisable(false);
        }

        if (process9.getText() == null || "*".equals(process9.getText()) || "".equals(process9.getText())) {
            processQty9.setDisable(true);
        } else {
            processQty9.setDisable(false);
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
                    handlerProcessQtyField();
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
                    processQty7.setText((process7.getText() == null || "*".equals(process7.getText())
                            || "".equals(process7.getText())) ? "" : newValue);
                    processQty8.setText((process8.getText() == null || "*".equals(process8.getText())
                            || "".equals(process8.getText())) ? "" : newValue);
                    processQty9.setText((process9.getText() == null || "*".equals(process9.getText())
                            || "".equals(process9.getText())) ? "" : newValue);
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

    // 点击确定时，添加日报
    @FXML
    public void confirmBtnAction() {
        String billNo = billNoTextField.getText().trim();
        // 添加日报之前，首先将该条生产任务单的信息同步到日报汇总表中
        DailyProcessTotalQty d = DailyUtil.getProcessTotalQty(billNo);
        if (d == null) {
            DailyUtil.addProductDailyTotal(billNo);
        }

        String message = verifyProcess(billNo);

        if (!"OK".equals(message)) {
            AlertUtil.alertInfoLater(message);
        } else {
            String dailyNo = DailyUtil.getDailyNo();
            int planQty = DailyUtil.getProcessTotalQty(billNo).getPlanQuantity();

            Daily daily = new Daily(dailyNo, billNo, materialCode.getText(), materialName.getText(), model.getText(),
                    planQty, resProcess1.getText(), Double.valueOf(resProcessPrice1.getText()),
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
                    Integer.valueOf("".equals(processQty6.getText()) ? "0" : processQty6.getText()), process7.getText(),
                    Double.valueOf(processPrice7.getText()),
                    Integer.valueOf("".equals(processQty7.getText()) ? "0" : processQty7.getText()), process8.getText(),
                    Double.valueOf(processPrice8.getText()),
                    Integer.valueOf("".equals(processQty8.getText()) ? "0" : processQty8.getText()), process9.getText(),
                    Double.valueOf(processPrice9.getText()),
                    Integer.valueOf("".equals(processQty9.getText()) ? "0" : processQty9.getText()), null,
                    new Timestamp(new Date().getTime()), 0, 0, DailyUtil.getDailyDetailSeq(billNo));

            if (DailyUtil.addDaily(daily)) {
                AlertUtil.alertInfoLater(PropsUtil.getMessage("daily.add.success") + dailyNo);
                DailyManageController.getdialogStage().close();
            } else {
                AlertUtil.alertInfoLater(PropsUtil.getMessage("daily.add.fail") + dailyNo);
                DailyManageController.getdialogStage().close();
            }
        }
    }

    // 判断所有工序的累计实作数量，前一个工序累计实作数量必须大于或等于后一个工序累计实作数量
    public String verifyProcess(String billNo) {
        DailyProcessTotalQty d = DailyUtil.getProcessTotalQty(billNo);
        int playQty = d.getPlanQuantity();

        String resProQty1 = resProcessQty1.getText().trim();
        String resProQty2 = resProcessQty2.getText().trim();
        String resProQty3 = resProcessQty3.getText().trim();
        String proQty1 = processQty1.getText().trim();
        String proQty2 = processQty2.getText().trim();
        String proQty3 = processQty3.getText().trim();
        String proQty4 = processQty4.getText().trim();
        String proQty5 = processQty5.getText().trim();
        String proQty6 = processQty6.getText().trim();
        String proQty7 = processQty7.getText().trim();
        String proQty8 = processQty8.getText().trim();
        String proQty9 = processQty9.getText().trim();

        int resProTotalQty1 = "".equals(resProQty1) ? 0 : Integer.valueOf(resProQty1) + d.getResProcessTotalQty1();
        int resProTotalQty2 = "".equals(resProQty2) ? 0 : Integer.valueOf(resProQty2) + d.getResProcessTotalQty2();
        int resProTotalQty3 = "".equals(resProQty3) ? 0 : Integer.valueOf(resProQty3) + d.getResProcessTotalQty3();
        int proTotalQty1 = "".equals(proQty1) ? 0 : Integer.valueOf(proQty1) + d.getProcessTotalQty1();
        int proTotalQty2 = "".equals(proQty2) ? 0 : Integer.valueOf(proQty2) + d.getProcessTotalQty2();
        int proTotalQty3 = "".equals(proQty3) ? 0 : Integer.valueOf(proQty3) + d.getProcessTotalQty3();
        int proTotalQty4 = "".equals(proQty4) ? 0 : Integer.valueOf(proQty4) + d.getProcessTotalQty4();
        int proTotalQty5 = "".equals(proQty5) ? 0 : Integer.valueOf(proQty5) + d.getProcessTotalQty5();
        int proTotalQty6 = "".equals(proQty6) ? 0 : Integer.valueOf(proQty6) + d.getProcessTotalQty6();
        int proTotalQty7 = "".equals(proQty7) ? 0 : Integer.valueOf(proQty7) + d.getProcessTotalQty7();
        int proTotalQty8 = "".equals(proQty8) ? 0 : Integer.valueOf(proQty8) + d.getProcessTotalQty8();
        int proTotalQty9 = "".equals(proQty9) ? 0 : Integer.valueOf(proQty9) + d.getProcessTotalQty9();

        int[] resTotalQty = { resProTotalQty1, resProTotalQty2, resProTotalQty3 };

        int[] totalQty = { proTotalQty1, proTotalQty2, proTotalQty3, proTotalQty4, proTotalQty5, proTotalQty6,
                proTotalQty7, proTotalQty8, proTotalQty9 };

        String verifyIsNullRes = DailyUtil.verifyIsNull(resProQty1, resProQty2, resProQty3, proQty1, proQty2, proQty3,
                proQty4, proQty5, proQty6, proQty7, proQty8, proQty9);

        if (!"OK".equals(verifyIsNullRes)) {
            return verifyIsNullRes;
        }

        return DailyUtil.verifyProcess(resTotalQty, totalQty, playQty);
    }

    @FXML
    public void cancelBtnAction() {
        DailyManageController.getdialogStage().close();
    }

    public static void main(String[] args) {
//        new AddDailyController().addProductDailyTotal("制造-CB车间180813427");

        DailyProcessTotalQty d = DailyUtil.getProcessTotalQty("CBZ1809013");
        System.out.println(d.getPlanQuantity());
        System.out.println(Integer.valueOf(""));
    }

}
