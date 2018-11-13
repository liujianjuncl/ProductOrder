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

    private LocalDate proDate; // 生产日期

    private String resPro1; // 改制工序1

    private double resProPrice1; // 改制工序1单价

    private int resProQty1; // 改制工序1本次实作数量

    private String resPro2; // 改制工序2

    private double resProPrice2; // 改制工序2单价

    private int resProQty2; // 改制工序2本次实作数量

    private String resPro3; // 改制工序3

    private double resProPrice3; // 改制工序3单价

    private int resProQty3; // 改制工序3本次实作数量

    private String pro1; // 工序1

    private double proPrice1; // 工序1单价

    private int proQty1; // 工序1本次实作数量

    private String pro2; // 工序2

    private double proPrice2; // 工序2单价

    private int proQty2; // 工序2本次实作数量

    private String pro3; // 工序3

    private double proPrice3; // 工序3单价

    private int proQty3; // 工序3本次实作数量

    private String pro4; // 工序4

    private double proPrice4; // 工序4单价

    private int proQty4; // 工序4本次实作数量

    private String pro5; // 工序5

    private double proPrice5; // 工序5单价

    private int proQty5; // 工序5本次实作数量

    private String pro6; // 工序6

    private double proPrice6; // 工序6单价

    private int proQty6; // 工序6本次实作数量

    private String createUser; // 创建用户

    private Timestamp createTime; // 创建时间
    
    private String modifyUser; //修改用户
    
    private Timestamp modifyTime; //修改时间

    private int isPiecework; // 是否计件

    private int isDelete; // 删除标志 1：删除 0：正常

    private int sequence; // 日报序号，用于记录同一个生产任务单中日报编号

    public Daily(String dailyNo, String billNo, String materialCode, String materialName, String model, int planQty,
            LocalDate proDate, String resPro1, double resProPrice1, int resProQty1, String resPro2, double resProPrice2,
            int resProQty2, String resPro3, double resProPrice3, int resProQty3, String pro1, double proPrice1,
            int proQty1, String pro2, double proPrice2, int proQty2, String pro3,
            double proPrice3, int proQty3, String pro4, double proPrice4, int proQty4,
            String pro5, double proPrice5, int proQty5, String pro6, double proPrice6,
            int proQty6, String createUser, Timestamp createTime, String modifyUser, Timestamp modifyTime, int isPiecework, int isDelete, int sequence) {
        super();
        this.dailyNo = dailyNo;
        this.billNo = billNo;
        this.materialCode = materialCode;
        this.materialName = materialName;
        this.model = model;
        this.planQty = planQty;
        this.proDate = proDate;
        this.resPro1 = resPro1;
        this.resProPrice1 = resProPrice1;
        this.resProQty1 = resProQty1;
        this.resPro2 = resPro2;
        this.resProPrice2 = resProPrice2;
        this.resProQty2 = resProQty2;
        this.resPro3 = resPro3;
        this.resProPrice3 = resProPrice3;
        this.resProQty3 = resProQty3;
        this.pro1 = pro1;
        this.proPrice1 = proPrice1;
        this.proQty1 = proQty1;
        this.pro2 = pro2;
        this.proPrice2 = proPrice2;
        this.proQty2 = proQty2;
        this.pro3 = pro3;
        this.proPrice3 = proPrice3;
        this.proQty3 = proQty3;
        this.pro4 = pro4;
        this.proPrice4 = proPrice4;
        this.proQty4 = proQty4;
        this.pro5 = pro5;
        this.proPrice5 = proPrice5;
        this.proQty5 = proQty5;
        this.pro6 = pro6;
        this.proPrice6 = proPrice6;
        this.proQty6 = proQty6;
        this.createUser = createUser;
        this.createTime = createTime;
        this.modifyUser = modifyUser;
        this.modifyTime = modifyTime;
        this.isPiecework = isPiecework;
        this.isDelete = isDelete;
        this.sequence = sequence;
    }

    public Daily(CheckBox checkbox, String dailyNo, String billNo, String materialCode, String materialName,
            String model, int planQty, LocalDate proDate) {
        super();
        this.checkbox = checkbox;
        this.dailyNo = dailyNo;
        this.billNo = billNo;
        this.materialCode = materialCode;
        this.materialName = materialName;
        this.model = model;
        this.planQty = planQty;
        this.proDate = proDate;
    }

    public Daily(String dailyNo, String billNo, String materialCode, String materialName, String model, int planQty,
            LocalDate proDate) {
        super();
        this.dailyNo = dailyNo;
        this.billNo = billNo;
        this.materialCode = materialCode;
        this.materialName = materialName;
        this.model = model;
        this.planQty = planQty;
        this.proDate = proDate;
    }

    public CheckBox getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(CheckBox checkbox) {
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

    public LocalDate getProDate() {
        return proDate;
    }

    public void setProDate(LocalDate proDate) {
        this.proDate = proDate;
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
        return pro1;
    }

    public void setPro1(String pro1) {
        this.pro1 = pro1;
    }

    public double getProPrice1() {
        return proPrice1;
    }

    public void setProPrice1(double proPrice1) {
        this.proPrice1 = proPrice1;
    }

    public int getProQty1() {
        return proQty1;
    }

    public void setProQty1(int proQty1) {
        this.proQty1 = proQty1;
    }

    public String getPro2() {
        return pro2;
    }

    public void setPro2(String pro2) {
        this.pro2 = pro2;
    }

    public double getProPrice2() {
        return proPrice2;
    }

    public void setProPrice2(double proPrice2) {
        this.proPrice2 = proPrice2;
    }

    public int getProQty2() {
        return proQty2;
    }

    public void setProQty2(int proQty2) {
        this.proQty2 = proQty2;
    }

    public String getPro3() {
        return pro3;
    }

    public void setPro3(String pro3) {
        this.pro3 = pro3;
    }

    public double getProPrice3() {
        return proPrice3;
    }

    public void setProPrice3(double proPrice3) {
        this.proPrice3 = proPrice3;
    }

    public int getProQty3() {
        return proQty3;
    }

    public void setProQty3(int proQty3) {
        this.proQty3 = proQty3;
    }

    public String getPro4() {
        return pro4;
    }

    public void setPro4(String pro4) {
        this.pro4 = pro4;
    }

    public double getProPrice4() {
        return proPrice4;
    }

    public void setProPrice4(double proPrice4) {
        this.proPrice4 = proPrice4;
    }

    public int getProQty4() {
        return proQty4;
    }

    public void setProQty4(int proQty4) {
        this.proQty4 = proQty4;
    }

    public String getPro5() {
        return pro5;
    }

    public void setPro5(String pro5) {
        this.pro5 = pro5;
    }

    public double getProPrice5() {
        return proPrice5;
    }

    public void setProPrice5(double proPrice5) {
        this.proPrice5 = proPrice5;
    }

    public int getProQty5() {
        return proQty5;
    }

    public void setProQty5(int proQty5) {
        this.proQty5 = proQty5;
    }

    public String getPro6() {
        return pro6;
    }

    public void setPro6(String pro6) {
        this.pro6 = pro6;
    }

    public double getProPrice6() {
        return proPrice6;
    }

    public void setProPrice6(double proPrice6) {
        this.proPrice6 = proPrice6;
    }

    public int getProQty6() {
        return proQty6;
    }

    public void setProQty6(int proQty6) {
        this.proQty6 = proQty6;
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

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
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
