package com.nii.desktop.model;

public class DailyProcessQty {

    private String billNo; // �������񵥱��
    
    private String dailyNo; // �����ձ�����

    private int planQuantity; // �ƻ���������

    private int resProcessQty1; // ���ƹ���1ʵ������

    private int resProcessQty2; // ���ƹ���2ʵ������

    private int resProcessQty3; // ���ƹ���3ʵ������

    private int processQty1; // ����1ʵ������

    private int processQty2; // ����2ʵ������

    private int processQty3; // ����3ʵ������

    private int processQty4; // ����4ʵ������

    private int processQty5; // ����5ʵ������

    private int processQty6; // ����6ʵ������

    private int processQty7; // ����7ʵ������

    private int processQty8; // ����8ʵ������

    private int processQty9; // ����9ʵ������

    public DailyProcessQty() {
        super();
    }

    public DailyProcessQty(String billNo, String dailyNo, int planQuantity, int resProcessQty1, int resProcessQty2,
            int resProcessQty3, int processQty1, int processQty2, int processQty3,
            int processQty4, int processQty5, int processQty6, int processQty7,
            int processQty8, int processQty9) {
        super();
        this.billNo = billNo;
        this.dailyNo = dailyNo;
        this.planQuantity = planQuantity;
        this.resProcessQty1 = resProcessQty1;
        this.resProcessQty2 = resProcessQty2;
        this.resProcessQty3 = resProcessQty3;
        this.processQty1 = processQty1;
        this.processQty2 = processQty2;
        this.processQty3 = processQty3;
        this.processQty4 = processQty4;
        this.processQty5 = processQty5;
        this.processQty6 = processQty6;
        this.processQty7 = processQty7;
        this.processQty8 = processQty8;
        this.processQty9 = processQty9;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }
    
    public String getDailyNo() {
        return dailyNo;
    }

    public void setDailyNo(String dailyNo) {
        this.dailyNo = dailyNo;
    }

    public int getPlanQuantity() {
        return planQuantity;
    }

    public void setPlanQuantity(int planQuantity) {
        this.planQuantity = planQuantity;
    }

    public int getResProcessQty1() {
        return resProcessQty1;
    }

    public void setResProcessQty1(int resProcessQty1) {
        this.resProcessQty1 = resProcessQty1;
    }

    public int getResProcessQty2() {
        return resProcessQty2;
    }

    public void setResProcessQty2(int resProcessQty2) {
        this.resProcessQty2 = resProcessQty2;
    }

    public int getResProcessQty3() {
        return resProcessQty3;
    }

    public void setResProcessQty3(int resProcessQty3) {
        this.resProcessQty3 = resProcessQty3;
    }

    public int getProcessQty1() {
        return processQty1;
    }

    public void setProcessQty1(int processQty1) {
        this.processQty1 = processQty1;
    }

    public int getProcessQty2() {
        return processQty2;
    }

    public void setProcessQty2(int processQty2) {
        this.processQty2 = processQty2;
    }

    public int getProcessQty3() {
        return processQty3;
    }

    public void setProcessQty3(int processQty3) {
        this.processQty3 = processQty3;
    }

    public int getProcessQty4() {
        return processQty4;
    }

    public void setProcessQty4(int processQty4) {
        this.processQty4 = processQty4;
    }

    public int getProcessQty5() {
        return processQty5;
    }

    public void setProcessQty5(int processQty5) {
        this.processQty5 = processQty5;
    }

    public int getProcessQty6() {
        return processQty6;
    }

    public void setProcessQty6(int processQty6) {
        this.processQty6 = processQty6;
    }

    public int getProcessQty7() {
        return processQty7;
    }

    public void setProcessQty7(int processQty7) {
        this.processQty7 = processQty7;
    }

    public int getProcessQty8() {
        return processQty8;
    }

    public void setProcessQty8(int processQty8) {
        this.processQty8 = processQty8;
    }

    public int getProcessQty9() {
        return processQty9;
    }

    public void setProcessQty9(int processQty9) {
        this.processQty9 = processQty9;
    }

}
