package com.nii.desktop.model;

public class Daily {

    private String dailyNo; // 生产日报编号

    private String billNo; // 生产任务单编号

    private String materialCode; // 物料代码

    private String materialName; // 物料名称

    private String model; // 规格型号

    private int planQuantity; // 计划生产数量

    private String resProcess1; // 改制工序1

    private double resProcess1Price; // 改制工序1单价

    private int resProcess1Quantity; // 改制工序1本次实作数量

    private String resProcess2; // 改制工序2

    private double resProcess2Price; // 改制工序2单价

    private int resProcess2Quantity; // 改制工序2本次实作数量

    private String resProcess3; // 改制工序3

    private double resProcess3Price; // 改制工序3单价

    private int resProcess3Quantity; // 改制工序3本次实作数量

    private String process1; // 工序1

    private double process1Price; // 工序1单价

    private int process1Quantity; // 工序1本次实作数量

    private String process2; // 工序2

    private double process2Price; // 工序2单价

    private int process2Quantity; // 工序2本次实作数量

    private String process3; // 工序3

    private double process3Price; // 工序3单价

    private int process3Quantity; // 工序3本次实作数量

    private String process4; // 工序4

    private double process4Price; // 工序4单价

    private int process4Quantity; // 工序4本次实作数量

    private String process5; // 工序5

    private double process5Price; // 工序5单价

    private int process5Quantity; // 工序5本次实作数量

    private String process6; // 工序6

    private double process6Price; // 工序6单价

    private int process6Quantity; // 工序6本次实作数量

    private String process7; // 工序7

    private double process7Price; // 工序7单价

    private int process7Quantity; // 工序7本次实作数量

    private String process8; // 工序8

    private double process8Price; // 工序8单价

    private int process8Quantity; // 工序8本次实作数量

    private String process9; // 工序9

    private double process9Price; // 工序9单价

    private int process9Quantity; // 工序9本次实作数量

    public Daily(String dailyNo, String billNo, String materialCode, String materialName, String model,
            int planQuantity, String resProcess1, double resProcess1Price, int resProcess1Quantity, String resProcess2,
            double resProcess2Price, int resProcess2Quantity, String resProcess3, double resProcess3Price,
            int resProcess3Quantity, String process1, double process1Price, int process1Quantity, String process2,
            double process2Price, int process2Quantity, String process3, double process3Price, int process3Quantity,
            String process4, double process4Price, int process4Quantity, String process5, double process5Price,
            int process5Quantity, String process6, double process6Price, int process6Quantity, String process7,
            double process7Price, int process7Quantity, String process8, double process8Price, int process8Quantity,
            String process9, double process9Price, int process9Quantity) {
        super();
        this.dailyNo = dailyNo;
        this.billNo = billNo;
        this.materialCode = materialCode;
        this.materialName = materialName;
        this.model = model;
        this.planQuantity = planQuantity;
        this.resProcess1 = resProcess1;
        this.resProcess1Price = resProcess1Price;
        this.resProcess1Quantity = resProcess1Quantity;
        this.resProcess2 = resProcess2;
        this.resProcess2Price = resProcess2Price;
        this.resProcess2Quantity = resProcess2Quantity;
        this.resProcess3 = resProcess3;
        this.resProcess3Price = resProcess3Price;
        this.resProcess3Quantity = resProcess3Quantity;
        this.process1 = process1;
        this.process1Price = process1Price;
        this.process1Quantity = process1Quantity;
        this.process2 = process2;
        this.process2Price = process2Price;
        this.process2Quantity = process2Quantity;
        this.process3 = process3;
        this.process3Price = process3Price;
        this.process3Quantity = process3Quantity;
        this.process4 = process4;
        this.process4Price = process4Price;
        this.process4Quantity = process4Quantity;
        this.process5 = process5;
        this.process5Price = process5Price;
        this.process5Quantity = process5Quantity;
        this.process6 = process6;
        this.process6Price = process6Price;
        this.process6Quantity = process6Quantity;
        this.process7 = process7;
        this.process7Price = process7Price;
        this.process7Quantity = process7Quantity;
        this.process8 = process8;
        this.process8Price = process8Price;
        this.process8Quantity = process8Quantity;
        this.process9 = process9;
        this.process9Price = process9Price;
        this.process9Quantity = process9Quantity;
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

    public double getResProcess1Price() {
        return resProcess1Price;
    }

    public void setResProcess1Price(double resProcess1Price) {
        this.resProcess1Price = resProcess1Price;
    }

    public int getResProcess1Quantity() {
        return resProcess1Quantity;
    }

    public void setResProcess1Quantity(int resProcess1Quantity) {
        this.resProcess1Quantity = resProcess1Quantity;
    }

    public String getResProcess2() {
        return resProcess2;
    }

    public void setResProcess2(String resProcess2) {
        this.resProcess2 = resProcess2;
    }

    public double getResProcess2Price() {
        return resProcess2Price;
    }

    public void setResProcess2Price(double resProcess2Price) {
        this.resProcess2Price = resProcess2Price;
    }

    public int getResProcess2Quantity() {
        return resProcess2Quantity;
    }

    public void setResProcess2Quantity(int resProcess2Quantity) {
        this.resProcess2Quantity = resProcess2Quantity;
    }

    public String getResProcess3() {
        return resProcess3;
    }

    public void setResProcess3(String resProcess3) {
        this.resProcess3 = resProcess3;
    }

    public double getResProcess3Price() {
        return resProcess3Price;
    }

    public void setResProcess3Price(double resProcess3Price) {
        this.resProcess3Price = resProcess3Price;
    }

    public int getResProcess3Quantity() {
        return resProcess3Quantity;
    }

    public void setResProcess3Quantity(int resProcess3Quantity) {
        this.resProcess3Quantity = resProcess3Quantity;
    }

    public String getProcess1() {
        return process1;
    }

    public void setProcess1(String process1) {
        this.process1 = process1;
    }

    public double getProcess1Price() {
        return process1Price;
    }

    public void setProcess1Price(double process1Price) {
        this.process1Price = process1Price;
    }

    public int getProcess1Quantity() {
        return process1Quantity;
    }

    public void setProcess1Quantity(int process1Quantity) {
        this.process1Quantity = process1Quantity;
    }

    public String getProcess2() {
        return process2;
    }

    public void setProcess2(String process2) {
        this.process2 = process2;
    }

    public double getProcess2Price() {
        return process2Price;
    }

    public void setProcess2Price(double process2Price) {
        this.process2Price = process2Price;
    }

    public int getProcess2Quantity() {
        return process2Quantity;
    }

    public void setProcess2Quantity(int process2Quantity) {
        this.process2Quantity = process2Quantity;
    }

    public String getProcess3() {
        return process3;
    }

    public void setProcess3(String process3) {
        this.process3 = process3;
    }

    public double getProcess3Price() {
        return process3Price;
    }

    public void setProcess3Price(double process3Price) {
        this.process3Price = process3Price;
    }

    public int getProcess3Quantity() {
        return process3Quantity;
    }

    public void setProcess3Quantity(int process3Quantity) {
        this.process3Quantity = process3Quantity;
    }

    public String getProcess4() {
        return process4;
    }

    public void setProcess4(String process4) {
        this.process4 = process4;
    }

    public double getProcess4Price() {
        return process4Price;
    }

    public void setProcess4Price(double process4Price) {
        this.process4Price = process4Price;
    }

    public int getProcess4Quantity() {
        return process4Quantity;
    }

    public void setProcess4Quantity(int process4Quantity) {
        this.process4Quantity = process4Quantity;
    }

    public String getProcess5() {
        return process5;
    }

    public void setProcess5(String process5) {
        this.process5 = process5;
    }

    public double getProcess5Price() {
        return process5Price;
    }

    public void setProcess5Price(double process5Price) {
        this.process5Price = process5Price;
    }

    public int getProcess5Quantity() {
        return process5Quantity;
    }

    public void setProcess5Quantity(int process5Quantity) {
        this.process5Quantity = process5Quantity;
    }

    public String getProcess6() {
        return process6;
    }

    public void setProcess6(String process6) {
        this.process6 = process6;
    }

    public double getProcess6Price() {
        return process6Price;
    }

    public void setProcess6Price(double process6Price) {
        this.process6Price = process6Price;
    }

    public int getProcess6Quantity() {
        return process6Quantity;
    }

    public void setProcess6Quantity(int process6Quantity) {
        this.process6Quantity = process6Quantity;
    }

    public String getProcess7() {
        return process7;
    }

    public void setProcess7(String process7) {
        this.process7 = process7;
    }

    public double getProcess7Price() {
        return process7Price;
    }

    public void setProcess7Price(double process7Price) {
        this.process7Price = process7Price;
    }

    public int getProcess7Quantity() {
        return process7Quantity;
    }

    public void setProcess7Quantity(int process7Quantity) {
        this.process7Quantity = process7Quantity;
    }

    public String getProcess8() {
        return process8;
    }

    public void setProcess8(String process8) {
        this.process8 = process8;
    }

    public double getProcess8Price() {
        return process8Price;
    }

    public void setProcess8Price(double process8Price) {
        this.process8Price = process8Price;
    }

    public int getProcess8Quantity() {
        return process8Quantity;
    }

    public void setProcess8Quantity(int process8Quantity) {
        this.process8Quantity = process8Quantity;
    }

    public String getProcess9() {
        return process9;
    }

    public void setProcess9(String process9) {
        this.process9 = process9;
    }

    public double getProcess9Price() {
        return process9Price;
    }

    public void setProcess9Price(double process9Price) {
        this.process9Price = process9Price;
    }

    public int getProcess9Quantity() {
        return process9Quantity;
    }

    public void setProcess9Quantity(int process9Quantity) {
        this.process9Quantity = process9Quantity;
    }

}
