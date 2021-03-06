package com.nii.desktop.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
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

    // 生产日期
    @FXML
    private DatePicker productDate;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 根据生产任务单号获取生产任务单信息
        billNoTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    if (!getProductDailyInfo(billNoTextField.getText().trim())) {
                        AlertUtil.alertInfoLater(PropsUtil.getMessage("billNo.isNotExist"));
                    }
                }
            }
        });

        /* 当生产任务单号失去焦点时，重新查询生产任务单 */
        billNoTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                if (!newValue && !getProductDailyInfo(billNoTextField.getText().trim())) {
                    AlertUtil.alertInfoLater(PropsUtil.getMessage("billNo.isNotExist"));
                    planQuantity.clear();
                    materialCode.clear();
                    materialName.clear();
                    model.clear();
                    resProcess1.setText("");
                    resProcess2.setText("");
                    resProcess3.setText("");
                    process1.setText("");
                    process2.setText("");
                    process3.setText("");
                    process4.setText("");
                    process5.setText("");
                    process6.setText("");
                    resProcessPrice1.setText("");
                    resProcessPrice2.setText("");
                    resProcessPrice3.setText("");
                    processPrice1.setText("");
                    processPrice2.setText("");
                    processPrice3.setText("");
                    processPrice4.setText("");
                    processPrice5.setText("");
                    processPrice6.setText("");
                    resProcessQty1.clear();
                    resProcessQty2.clear();
                    resProcessQty3.clear();
                    processQty1.clear();
                    processQty2.clear();
                    processQty3.clear();
                    processQty4.clear();
                    processQty5.clear();
                    processQty6.clear();
                }
            }
        });

        // 生产日期默认当天
        productDate.setValue(DateUtil.dateToLocalDate(new Date()));
    }

    // 根据生产任务单号查询出当前生产任务单的信息并显示到界面上
    public boolean getProductDailyInfo(String billNo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            String sql = "select c.FBillNo as billNo, icc.FNumber as materialCode, icc.FName as materialName, icc.FModel as model, "
                    + "c.FQty as planQuantity, "
                    + "(case when charindex('*', CAST(item1.FName as varchar)) > 0 or item1.FName is null then '' else item1.FName end) as resProcess1,"
                    + "(case when charindex('0', c.FHeadSelfJ01104) > 0 or charindex('.', c.FHeadSelfJ01104) > 0 then cast(c.FHeadSelfJ01104 as varchar) else '0' end) as resProcessPrice1,"
                    + "(case when charindex('*', CAST(item2.FName as varchar)) > 0 or item2.FName is null then '' else item2.FName end) as resProcess2, "
                    + "(case when charindex('0', c.FHeadSelfJ01106) > 0 or charindex('.', c.FHeadSelfJ01106) > 0 then cast(c.FHeadSelfJ01106 as varchar) else '0' end) as resProcessPrice2,"
                    + "(case when charindex('*', CAST(item3.FName as varchar)) > 0 or item3.FName is null then '' else item3.FName end) as resProcess3,"
                    + "(case when charindex('0', c.FHeadSelfJ01108) > 0 or charindex('.', c.FHeadSelfJ01108) > 0 then cast(c.FHeadSelfJ01108 as varchar) else '0' end) as resProcessPrice3,"
                    + "(case when charindex('*', CAST(c.FHeadSelfJ0185 as varchar)) > 0 or c.FHeadSelfJ0185 is null then '' else c.FHeadSelfJ0185 end) as process1,"
                    + "(case when charindex('0', c.FHeadSelfJ0186) > 0 or charindex('.', c.FHeadSelfJ0186) > 0 then cast(c.FHeadSelfJ0186 as varchar) else '0' end) as processPrice1,"
                    + "(case when charindex('*', CAST(c.FHeadSelfJ0187 as varchar)) > 0 or c.FHeadSelfJ0187 is null then '' else c.FHeadSelfJ0187 end) as process2,"
                    + "(case when charindex('0', c.FHeadSelfJ0188) > 0 or charindex('.', c.FHeadSelfJ0188) > 0 then cast(c.FHeadSelfJ0188 as varchar) else '0' end) as processPrice2,"
                    + "(case when charindex('*', CAST(c.FHeadSelfJ0189 as varchar)) > 0 or c.FHeadSelfJ0189 is null then '' else c.FHeadSelfJ0189 end) as process3,"
                    + "(case when charindex('0', c.FHeadSelfJ0190) > 0 or charindex('.', c.FHeadSelfJ0190) > 0 then cast(c.FHeadSelfJ0190 as varchar) else '0' end) as processPrice3,"
                    + "(case when charindex('*', CAST(c.FHeadSelfJ0191 as varchar)) > 0 or c.FHeadSelfJ0191 is null then '' else c.FHeadSelfJ0191 end) as process4,"
                    + "(case when charindex('0', c.FHeadSelfJ0192) > 0 or charindex('.', c.FHeadSelfJ0192) > 0 then cast(c.FHeadSelfJ0192 as varchar) else '0' end) as processPrice4,"
                    + "(case when charindex('*', CAST(c.FHeadSelfJ0193 as varchar)) > 0 or c.FHeadSelfJ0193 is null then '' else c.FHeadSelfJ0193 end) as process5,"
                    + "(case when charindex('0', c.FHeadSelfJ0194) > 0 or charindex('.', c.FHeadSelfJ0194) > 0 then cast(c.FHeadSelfJ0194 as varchar) else '0' end) as processPrice5,"
                    + "(case when charindex('*', CAST(c.FHeadSelfJ0195 as varchar)) > 0 or c.FHeadSelfJ0195 is null then '' else c.FHeadSelfJ0195 end) as process6,"
                    + "(case when charindex('0', c.FHeadSelfJ0196) > 0 or charindex('.', c.FHeadSelfJ0196) > 0 then cast(c.FHeadSelfJ0196 as varchar) else '0' end) as processPrice6 "
                    + "from dbo.ICMO c left join dbo.t_ICItemCore icc on c.FItemID = icc.FItemID "
                    + "left join dbo.t_Item item1 on c.FHeadSelfJ01103 = item1.FitemID "
                    + "left join dbo.t_Item item2 on c.FHeadSelfJ01105 = item2.FitemID "
                    + "left join dbo.t_Item item3 on c.FHeadSelfJ01107 = item3.FitemID  "
                    + "where c.FBillNo = ?  and FCancellation = 0 ";

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

    // 工序名称为空时，对应的实作数量输入框不可输入
    public void handlerProcessQtyField() {
        boolean result = false;
        if ((resProcess1.getText() != null && !"*".equals(resProcess1.getText()) && !"".equals(resProcess1.getText()))
                && resProcess2.getText() != null && !"*".equals(resProcess2.getText())
                && !"".equals(resProcess2.getText()) && resProcess3.getText() != null
                && !"*".equals(resProcess3.getText()) && !"".equals(resProcess3.getText())) {
            result = true;
        }

        allProcessQtyTextField.setDisable(true);
        allProcessChkBox.setSelected(false);
        allProcessQtyTextField.setText("");
        resProcessQty1.setText("");
        resProcessQty2.setText("");
        resProcessQty3.setText("");
        processQty1.setText("");
        processQty2.setText("");
        processQty3.setText("");
        processQty4.setText("");
        processQty5.setText("");
        processQty6.setText("");

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

    // 全工序实作数量逻辑处理
    public void allProcessHandler() {
        // 复选框勾选时，全工序实作数量可以输入
        allProcessChkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // TODO Auto-generated method stub
                if (newValue) { // 当全工序勾选时，全工序实作数量可输入
                    allProcessQtyTextField.setDisable(false);
                } else { // 当全工序去勾选时，全工序实作数量不可输入
                    allProcessQtyTextField.setDisable(true);
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
    }

    // 点击确定时，添加日报
    @FXML
    public void confirmBtnAction() throws ParseException {
        String billNo = billNoTextField.getText().trim();

        if ("".equals(billNo.trim())) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("billNo.isnot.null"));
            return;
        }

        LocalDate proDate = productDate.getValue();

        // 本月25号
        Date curDate25Day = DateUtil.curMonth25Day();
        // 上月26号
        Date lastDate26Day = DateUtil.lastMonth26Day();

        // 获取登录用户
        User loginUser = SessionUtil.USERS.get("loginUser");

        // 普通员工只允许增加上个月26号到本月25号的生产日报！
        if (!"是".equals(loginUser.getIsManager()) && (lastDate26Day.compareTo(DateUtil.localDateToDate(proDate)) > 0
                || curDate25Day.compareTo(DateUtil.localDateToDate(proDate)) < 0)) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("donot.daily.modify"));
            return;
        }

        // 添加日报之前，首先将该条生产任务单的信息同步到日报汇总表中
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
                    Integer.valueOf("".equals(resProcessQty1.getText().trim()) ? "0" : resProcessQty1.getText().trim()),
                    resProcess2.getText(), Double.valueOf(resProcessPrice2.getText()),
                    Integer.valueOf("".equals(resProcessQty2.getText().trim()) ? "0" : resProcessQty2.getText().trim()),
                    resProcess3.getText(), Double.valueOf(resProcessPrice3.getText()),
                    Integer.valueOf("".equals(resProcessQty3.getText().trim()) ? "0" : resProcessQty3.getText().trim()),
                    process1.getText(), Double.valueOf(processPrice1.getText()),
                    Integer.valueOf("".equals(processQty1.getText().trim()) ? "0" : processQty1.getText().trim()), process2.getText(),
                    Double.valueOf(processPrice2.getText()),
                    Integer.valueOf("".equals(processQty2.getText().trim()) ? "0" : processQty2.getText().trim()), process3.getText(),
                    Double.valueOf(processPrice3.getText()),
                    Integer.valueOf("".equals(processQty3.getText().trim()) ? "0" : processQty3.getText().trim()), process4.getText(),
                    Double.valueOf(processPrice4.getText()),
                    Integer.valueOf("".equals(processQty4.getText().trim()) ? "0" : processQty4.getText().trim()), process5.getText(),
                    Double.valueOf(processPrice5.getText()),
                    Integer.valueOf("".equals(processQty5.getText().trim()) ? "0" : processQty5.getText().trim()), process6.getText(),
                    Double.valueOf(processPrice6.getText()),
                    Integer.valueOf("".equals(processQty6.getText().trim()) ? "0" : processQty6.getText().trim()),
                    loginUser.getUserNo(), new Timestamp(new Date().getTime()),
                    loginUser.getUserNo(), new Timestamp(new Date().getTime()),
                    "是".equals(loginUser.getIsPiecework()) ? 1 : 0, 0,
                    DailyUtil.getDailyDetailSeq(billNo), loginUser.getUserName(), loginUser.getUserName());

            boolean result = DailyUtil.addDaily(dpq, daily);
            if (result) {
                AlertUtil.alertInfoLater(PropsUtil.getMessage("daily.add.success") + dailyNo);
                // 新建完成刷新数据
                ((DailyTableViewController) SessionUtil.CONTROLLERS.get("DailyTableViewController")).refreshCurDay();
                DailyTableViewController.getdialogStage().close();
            } else {
                AlertUtil.alertInfoLater(PropsUtil.getMessage("daily.add.fail") + dailyNo);
                DailyTableViewController.getdialogStage().close();
            }
        }
    }

    // 判断所有工序的累计实作数量，工序累计实作数量不能大于生产任务单计划生产数量
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

        // 将本次录入的实作数量和已经完成的实作数量相加
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
//        new AddDailyController().addProductDailyTotal("制造-CB车间180813427");

        DailyProcessQty d = DailyUtil.getProcessTotalQty("CBZ1809013");
        System.out.println(d.getPlanQty());
        System.out.println(Integer.valueOf(""));
    }

}
