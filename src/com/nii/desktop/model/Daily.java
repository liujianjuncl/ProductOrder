package com.nii.desktop.model;

import java.sql.Timestamp;
import java.time.LocalDate;

import javafx.scene.control.CheckBox;

public class Daily {

    private CheckBox checkbox; // 复选框

    private String dailyNo; // 生产日报编号

    private String billNo; // 生产任务单编号

    private String materialCode; // 物料代码

    private String materialName; // 物料名称

    private String model; // 规格型号

    private int planQty; // 计划生产数量
    
    private LocalDate productDate;  //生产日期

    private String resPro1; // 改制工序1

    private double resProPrice1; // 改制工序1单价

    private int resProQty1; // 改制工序1本次实作数量

    private String resPro2; // 改制工序2

    private double resProPrice2; // 改制工序2单价

    private int resProQty2; // 改制工序2本次实作数量

    private String resPro3; // 改制工序3

    private double resProPrice3; // 改制工序3单价

    private int resProQty3; // 改制工序3本次实作数量

    private String process1; // 工序1

    private double processPrice1; // 工序1单价

    private int processQty1; // 工序1本次实作数量

    private String process2; // 工序2

    private double processPrice2; // 工序2单价

    private int processQty2; // 工序2本次实作数量

    private String process3; // 工序3

    private double processPrice3; // 工序3单价

    private int processQty3; // 工序3本次实作数量

    private String process4; // 工序4

    private double processPrice4; // 工序4单价

    private int processQty4; // 工序4本次实作数量

    private String process5; // 工序5

    private double processPrice5; // 工序5单价

    private int processQty5; // 工序5本次实作数量

    private String process6; // 工序6

    private double processPrice6; // 工序6单价

    private int processQty6; // 工序6本次实作数量

    private String process7; // 工序7

    private double processPrice7; // 工序7单价

    private int processQty7; // 工序7本次实作数量

    private String process8; // 工序8

    private double processPrice8; // 工序8单价

    private int processQty8; // 工序8本次实作数量

    private String process9; // 工序9

    private double processPrice9; // 工序9单价

    private int processQty9; // 工序9本次实作数量

    private String createUser; // 创建用户

    private Timestamp createTime; // 创建时间

    private int isPiecework; // 是否计件

    private int isDelete; // 删除标志 1：删除 0：正常

    private int sequence; // 日报序号，用于记录同一个生产任务单中日报编号

    public Daily(String dailyNo, String billNo, String materialCode, String materialName, String model,
            int planQty, LocalDate productDate, String resPro1, double resProPrice1, int resProQty1, String resPro2,
            double resProPrice2, int resProQty2, String resPro3, double resProPrice3,
            int resProQty3, String process1, double processPrice1, int processQty1, String process2,
            double processPrice2, int processQty2, String process3, double processPrice3, int processQty3,
            String process4, double processPrice4, int processQty4, String process5, double processPrice5,
            int processQty5, String process6, double processPrice6, int processQty6, String process7,
            double processPrice7, int processQty7, String process8, double processPrice8, int processQty8,
            String process9, double processPrice9, int processQty9, String createUser, Timestamp createTime,
            int isPiecework, int isDelete, int sequence) {
        super();
        this.dailyNo = dailyNo;
        this.billNo = billNo;
        this.materialCode = materialCode;
        this.materialName = materialName;
        this.model = model;
        this.planQty = planQty;
        this.productDate = productDate;
        this.resPro1 = resPro1;
        this.resProPrice1 = resProPrice1;
        this.resProQty1 = resProQty1;
        this.resPro2 = resPro2;
        this.resProPrice2 = resProPrice2;
        this.resProQty2 = resProQty2;
        this.resPro3 = resPro3;
        this.resProPrice3 = resProPrice3;
        this.resProQty3 = resProQty3;
        this.process1 = process1;
        this.processPrice1 = processPrice1;
        this.processQty1 = processQty1;
        this.process2 = process2;
        this.processPrice2 = processPrice2;
        this.processQty2 = processQty2;
        this.process3 = process3;
        this.processPrice3 = processPrice3;
        this.processQty3 = processQty3;
        this.process4 = process4;
        this.processPrice4 = processPrice4;
        this.processQty4 = processQty4;
        this.process5 = process5;
        this.processPrice5 = processPrice5;
        this.processQty5 = processQty5;
        this.process6 = process6;
        this.processPrice6 = processPrice6;
        this.processQty6 = processQty6;
        this.process7 = process7;
        this.processPrice7 = processPrice7;
        this.processQty7 = processQty7;
        this.process8 = process8;
        this.processPrice8 = processPrice8;
        this.processQty8 = processQty8;
        this.process9 = process9;
        this.processPrice9 = processPrice9;
        this.processQty9 = processQty9;
        this.createUser = createUser;
        this.createTime = createTime;
        this.isPiecework = isPiecework;
        this.isDelete = isDelete;
        this.sequence = sequence;
    }

    public Daily(CheckBox checkbox, String dailyNo, String billNo, String materialCode, String materialName,
            String model, int planQty, LocalDate productDate) {
        super();
        this.checkbox = checkbox;
        this.dailyNo = dailyNo;
        this.billNo = billNo;
        this.materialCode = materialCode;
        this.materialName = materialName;
        this.model = model;
        this.planQty = planQty;
        this.productDate = productDate;
    }

    public Daily(String dailyNo, String billNo, String materialCode, String materialName, String model,
            int planQty, LocalDate productDate) {
        super();
        this.dailyNo = dailyNo;
        this.billNo = billNo;
        this.materialCode = materialCode;
        this.materialName = materialName;
        this.model = model;
        this.planQty = planQty;
        this.productDate = productDate;
    }

    public CheckBox getCheckbox() {
        return checkbox;
    }

    public void setDailyNo(CheckBox checkbox) {
        this.checkbox = checkbox;
    }

    public String getDailyNo() {
        return dailyNo;
    }

    public void setDailyNo(String dailyNo) {
        this.dailyNo = dailyNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPlanQty() {
        return planQty;
    }

    public void setPlanQty(int planQty) {
        this.planQty = planQty;
    }
    
    public LocalDate getProductDate() {
        return productDate;
    }

    public void setPlanQty(LocalDate productDate) {
        this.productDate = productDate;
    }

    public String getResPro1() {
        return resPro1;
    }

    public void setResPro1(String resPro1) {
        this.resPro1 = resPro1;
    }

    public double getResProPrice1() {
        return resProPrice1;
    }

    public void setResProPrice1(double resProPrice1) {
        this.resProPrice1 = resProPrice1;
    }

    public int getResProQty1() {
        return resProQty1;
    }

    public void setResProQty1(int resProQty1) {
        this.resProQty1 = resProQty1;
    }

    public String getResPro2() {
        return resPro2;
    }

    public void setResPro2(String resPro2) {
        this.resPro2 = resPro2;
    }

    public double getResProPrice2() {
        return resProPrice2;
    }

    public void setResProPrice2(double resProPrice2) {
        this.resProPrice2 = resProPrice2;
    }

    public int getResProQty2() {
        return resProQty2;
    }

    public void setResProQty2(int resProQty2) {
        this.resProQty2 = resProQty2;
    }

    public String getResPro3() {
        return resPro3;
    }

    public void setResPro3(String resPro3) {
        this.resPro3 = resPro3;
    }

    public double getResProPrice3() {
        return resProPrice3;
    }

    public void setResProPrice3(double resProPrice3) {
        this.resProPrice3 = resProPrice3;
    }

    public int getResProQty3() {
        return resProQty3;
    }

    public void setResProQty3(int resProQty3) {
        this.resProQty3 = resProQty3;
    }

    public String getPro1() {
        return process1;
    }

    public void setPro1(String process1) {
        this.process1 = process1;
    }

    public double getProPrice1() {
        return processPrice1;
    }

    public void setProPrice1(double processPrice1) {
        this.processPrice1 = processPrice1;
    }

    public int getProQty1() {
        return processQty1;
    }

    public void setProQty1(int processQty1) {
        this.processQty1 = processQty1;
    }

    public String getPro2() {
        return process2;
    }

    public void setPro2(String process2) {
        this.process2 = process2;
    }

    public double getProPrice2() {
        return processPrice2;
    }

    public void setProPrice2(double processPrice2) {
        this.processPrice2 = processPrice2;
    }

    public int getProQty2() {
        return processQty2;
    }

    public void setProQty2(int processQty2) {
        this.processQty2 = processQty2;
    }

    public String getPro3() {
        return process3;
    }

    public void setPro3(String process3) {
        this.process3 = process3;
    }

    public double getProPrice3() {
        return processPrice3;
    }

    public void setProPrice3(double processPrice3) {
        this.processPrice3 = processPrice3;
    }

    public int getProQty3() {
        return processQty3;
    }

    public void setProQty3(int processQty3) {
        this.processQty3 = processQty3;
    }

    public String getPro4() {
        return process4;
    }

    public void setPro4(String process4) {
        this.process4 = process4;
    }

    public double getProPrice4() {
        return processPrice4;
    }

    public void setProPrice4(double processPrice4) {
        this.processPrice4 = processPrice4;
    }

    public int getProQty4() {
        return processQty4;
    }

    public void setProQty4(int processQty4) {
        this.processQty4 = processQty4;
    }

    public String getPro5() {
        return process5;
    }

    public void setPro5(String process5) {
        this.process5 = process5;
    }

    public double getProPrice5() {
        return processPrice5;
    }

    public void setProPrice5(double processPrice5) {
        this.processPrice5 = processPrice5;
    }

    public int getProQty5() {
        return processQty5;
    }

    public void setProQty5(int processQty5) {
        this.processQty5 = processQty5;
    }

    public String getPro6() {
        return process6;
    }

    public void setPro6(String process6) {
        this.process6 = process6;
    }

    public double getProPrice6() {
        return processPrice6;
    }

    public void setProPrice6(double processPrice6) {
        this.processPrice6 = processPrice6;
    }

    public int getProQty6() {
        return processQty6;
    }

    public void setProQty6(int processQty6) {
        this.processQty6 = processQty6;
    }

    public String getPro7() {
        return process7;
    }

    public void setPro7(String process7) {
        this.process7 = process7;
    }

    public double getProPrice7() {
        return processPrice7;
    }

    public void setProPrice7(double processPrice7) {
        this.processPrice7 = processPrice7;
    }

    public int getProQty7() {
        return processQty7;
    }

    public void setProQty7(int processQty7) {
        this.processQty7 = processQty7;
    }

    public String getPro8() {
        return process8;
    }

    public void setPro8(String process8) {
        this.process8 = process8;
    }

    public double getProPrice8() {
        return processPrice8;
    }

    public void setProPrice8(double processPrice8) {
        this.processPrice8 = processPrice8;
    }

    public int getProQty8() {
        return processQty8;
    }

    public void setProQty8(int processQty8) {
        this.processQty8 = processQty8;
    }

    public String getPro9() {
        return process9;
    }

    public void setPro9(String process9) {
        this.process9 = process9;
    }

    public double getProPrice9() {
        return processPrice9;
    }

    public void setProPrice9(double processPrice9) {
        this.processPrice9 = processPrice9;
    }

    public int getProQty9() {
        return processQty9;
    }

    public void setProQty9(int processQty9) {
        this.processQty9 = processQty9;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public int getIsPiecework() {
        return isPiecework;
    }

    public void setIsPiecework(int isPiecework) {
        this.isPiecework = isPiecework;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

}
