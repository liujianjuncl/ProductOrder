package com.nii.desktop.model;

import java.sql.Timestamp;

public class Daily {

    private String dailyNo; // 生产日报编号

    private String billNo; // 生产任务单编号

    private String materialCode; // 物料代码

    private String materialName; // 物料名称

    private String model; // 规格型号

    private int planQuantity; // 计划生产数量

    private String resProcess1; // 改制工序1

    private double resProcessPrice1; // 改制工序1单价

    private int resProcessQuantity1; // 改制工序1本次实作数量

    private String resProcess2; // 改制工序2

    private double resProcessPrice2; // 改制工序2单价

    private int resProcessQuantity2; // 改制工序2本次实作数量

    private String resProcess3; // 改制工序3

    private double resProcessPrice3; // 改制工序3单价

    private int resProcessQuantity3; // 改制工序3本次实作数量

    private String process1; // 工序1

    private double processPrice1; // 工序1单价

    private int processQuantity1; // 工序1本次实作数量

    private String process2; // 工序2

    private double processPrice2; // 工序2单价

    private int processQuantity2; // 工序2本次实作数量

    private String process3; // 工序3

    private double processPrice3; // 工序3单价

    private int processQuantity3; // 工序3本次实作数量

    private String process4; // 工序4

    private double processPrice4; // 工序4单价

    private int processQuantity4; // 工序4本次实作数量

    private String process5; // 工序5

    private double processPrice5; // 工序5单价

    private int processQuantity5; // 工序5本次实作数量

    private String process6; // 工序6

    private double processPrice6; // 工序6单价

    private int processQuantity6; // 工序6本次实作数量

    private String process7; // 工序7

    private double processPrice7; // 工序7单价

    private int processQuantity7; // 工序7本次实作数量

    private String process8; // 工序8

    private double processPrice8; // 工序8单价

    private int processQuantity8; // 工序8本次实作数量

    private String process9; // 工序9

    private double processPrice9; // 工序9单价

    private int processQuantity9; // 工序9本次实作数量

    private String createUser; // 创建用户

    private Timestamp createTime; // 创建时间

    private int isPiecework; // 是否计件

    private int isDelete; // 删除标志 1：删除 0：正常

    private int sequence; // 日报序号，用于记录同一个生产任务单中日报编号

    public Daily(String dailyNo, String billNo, String materialCode, String materialName, String model,
            int planQuantity, String resProcess1, double resProcessPrice1, int resProcessQuantity1, String resProcess2,
            double resProcessPrice2, int resProcessQuantity2, String resProcess3, double resProcessPrice3,
            int resProcessQuantity3, String process1, double processPrice1, int processQuantity1, String process2,
            double processPrice2, int processQuantity2, String process3, double processPrice3, int processQuantity3,
            String process4, double processPrice4, int processQuantity4, String process5, double processPrice5,
            int processQuantity5, String process6, double processPrice6, int processQuantity6, String process7,
            double processPrice7, int processQuantity7, String process8, double processPrice8, int processQuantity8,
            String process9, double processPrice9, int processQuantity9, String createUser, Timestamp createTime,
            int isPiecework, int isDelete, int sequence) {
        super();
        this.dailyNo = dailyNo;
        this.billNo = billNo;
        this.materialCode = materialCode;
        this.materialName = materialName;
        this.model = model;
        this.planQuantity = planQuantity;
        this.resProcess1 = resProcess1;
        this.resProcessPrice1 = resProcessPrice1;
        this.resProcessQuantity1 = resProcessQuantity1;
        this.resProcess2 = resProcess2;
        this.resProcessPrice2 = resProcessPrice2;
        this.resProcessQuantity2 = resProcessQuantity2;
        this.resProcess3 = resProcess3;
        this.resProcessPrice3 = resProcessPrice3;
        this.resProcessQuantity3 = resProcessQuantity3;
        this.process1 = process1;
        this.processPrice1 = processPrice1;
        this.processQuantity1 = processQuantity1;
        this.process2 = process2;
        this.processPrice2 = processPrice2;
        this.processQuantity2 = processQuantity2;
        this.process3 = process3;
        this.processPrice3 = processPrice3;
        this.processQuantity3 = processQuantity3;
        this.process4 = process4;
        this.processPrice4 = processPrice4;
        this.processQuantity4 = processQuantity4;
        this.process5 = process5;
        this.processPrice5 = processPrice5;
        this.processQuantity5 = processQuantity5;
        this.process6 = process6;
        this.processPrice6 = processPrice6;
        this.processQuantity6 = processQuantity6;
        this.process7 = process7;
        this.processPrice7 = processPrice7;
        this.processQuantity7 = processQuantity7;
        this.process8 = process8;
        this.processPrice8 = processPrice8;
        this.processQuantity8 = processQuantity8;
        this.process9 = process9;
        this.processPrice9 = processPrice9;
        this.processQuantity9 = processQuantity9;
        this.createUser = createUser;
        this.createTime = createTime;
        this.isPiecework = isPiecework;
        this.isDelete = isDelete;
        this.sequence = sequence;
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

    public int getPlanQuantity() {
        return planQuantity;
    }

    public void setPlanQuantity(int planQuantity) {
        this.planQuantity = planQuantity;
    }

    public String getResProcess1() {
        return resProcess1;
    }

    public void setResProcess1(String resProcess1) {
        this.resProcess1 = resProcess1;
    }

    public double getResProcessPrice1() {
        return resProcessPrice1;
    }

    public void setResProcessPrice1(double resProcessPrice1) {
        this.resProcessPrice1 = resProcessPrice1;
    }

    public int getResProcessQuantity1() {
        return resProcessQuantity1;
    }

    public void setResProcessQuantity1(int resProcessQuantity1) {
        this.resProcessQuantity1 = resProcessQuantity1;
    }

    public String getResProcess2() {
        return resProcess2;
    }

    public void setResProcess2(String resProcess2) {
        this.resProcess2 = resProcess2;
    }

    public double getResProcessPrice2() {
        return resProcessPrice2;
    }

    public void setResProcessPrice2(double resProcessPrice2) {
        this.resProcessPrice2 = resProcessPrice2;
    }

    public int getResProcessQuantity2() {
        return resProcessQuantity2;
    }

    public void setResProcessQuantity2(int resProcessQuantity2) {
        this.resProcessQuantity2 = resProcessQuantity2;
    }

    public String getResProcess3() {
        return resProcess3;
    }

    public void setResProcess3(String resProcess3) {
        this.resProcess3 = resProcess3;
    }

    public double getResProcessPrice3() {
        return resProcessPrice3;
    }

    public void setResProcessPrice3(double resProcessPrice3) {
        this.resProcessPrice3 = resProcessPrice3;
    }

    public int getResProcessQuantity3() {
        return resProcessQuantity3;
    }

    public void setResProcessQuantity3(int resProcessQuantity3) {
        this.resProcessQuantity3 = resProcessQuantity3;
    }

    public String getProcess1() {
        return process1;
    }

    public void setProcess1(String process1) {
        this.process1 = process1;
    }

    public double getProcessPrice1() {
        return processPrice1;
    }

    public void setProcessPrice1(double processPrice1) {
        this.processPrice1 = processPrice1;
    }

    public int getProcessQuantity1() {
        return processQuantity1;
    }

    public void setProcessQuantity1(int processQuantity1) {
        this.processQuantity1 = processQuantity1;
    }

    public String getProcess2() {
        return process2;
    }

    public void setProcess2(String process2) {
        this.process2 = process2;
    }

    public double getProcessPrice2() {
        return processPrice2;
    }

    public void setProcessPrice2(double processPrice2) {
        this.processPrice2 = processPrice2;
    }

    public int getProcessQuantity2() {
        return processQuantity2;
    }

    public void setProcessQuantity2(int processQuantity2) {
        this.processQuantity2 = processQuantity2;
    }

    public String getProcess3() {
        return process3;
    }

    public void setProcess3(String process3) {
        this.process3 = process3;
    }

    public double getProcessPrice3() {
        return processPrice3;
    }

    public void setProcessPrice3(double processPrice3) {
        this.processPrice3 = processPrice3;
    }

    public int getProcessQuantity3() {
        return processQuantity3;
    }

    public void setProcessQuantity3(int processQuantity3) {
        this.processQuantity3 = processQuantity3;
    }

    public String getProcess4() {
        return process4;
    }

    public void setProcess4(String process4) {
        this.process4 = process4;
    }

    public double getProcessPrice4() {
        return processPrice4;
    }

    public void setProcessPrice4(double processPrice4) {
        this.processPrice4 = processPrice4;
    }

    public int getProcessQuantity4() {
        return processQuantity4;
    }

    public void setProcessQuantity4(int processQuantity4) {
        this.processQuantity4 = processQuantity4;
    }

    public String getProcess5() {
        return process5;
    }

    public void setProcess5(String process5) {
        this.process5 = process5;
    }

    public double getProcessPrice5() {
        return processPrice5;
    }

    public void setProcessPrice5(double processPrice5) {
        this.processPrice5 = processPrice5;
    }

    public int getProcessQuantity5() {
        return processQuantity5;
    }

    public void setProcessQuantity5(int processQuantity5) {
        this.processQuantity5 = processQuantity5;
    }

    public String getProcess6() {
        return process6;
    }

    public void setProcess6(String process6) {
        this.process6 = process6;
    }

    public double getProcessPrice6() {
        return processPrice6;
    }

    public void setProcessPrice6(double processPrice6) {
        this.processPrice6 = processPrice6;
    }

    public int getProcessQuantity6() {
        return processQuantity6;
    }

    public void setProcessQuantity6(int processQuantity6) {
        this.processQuantity6 = processQuantity6;
    }

    public String getProcess7() {
        return process7;
    }

    public void setProcess7(String process7) {
        this.process7 = process7;
    }

    public double getProcessPrice7() {
        return processPrice7;
    }

    public void setProcessPrice7(double processPrice7) {
        this.processPrice7 = processPrice7;
    }

    public int getProcessQuantity7() {
        return processQuantity7;
    }

    public void setProcessQuantity7(int processQuantity7) {
        this.processQuantity7 = processQuantity7;
    }

    public String getProcess8() {
        return process8;
    }

    public void setProcess8(String process8) {
        this.process8 = process8;
    }

    public double getProcessPrice8() {
        return processPrice8;
    }

    public void setProcessPrice8(double processPrice8) {
        this.processPrice8 = processPrice8;
    }

    public int getProcessQuantity8() {
        return processQuantity8;
    }

    public void setProcessQuantity8(int processQuantity8) {
        this.processQuantity8 = processQuantity8;
    }

    public String getProcess9() {
        return process9;
    }

    public void setProcess9(String process9) {
        this.process9 = process9;
    }

    public double getProcessPrice9() {
        return processPrice9;
    }

    public void setProcessPrice9(double processPrice9) {
        this.processPrice9 = processPrice9;
    }

    public int getProcessQuantity9() {
        return processQuantity9;
    }

    public void setProcessQuantity9(int processQuantity9) {
        this.processQuantity9 = processQuantity9;
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
