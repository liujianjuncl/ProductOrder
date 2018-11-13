package com.nii.desktop.model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class DailyProcessQty {

    private String billNo; // �������񵥱��

    private String dailyNo; // �����ձ�����

    private int planQty; // �ƻ���������
    
    private LocalDate productDate; //��������
    
    private int resProQty1; // ���ƹ���1ʵ������

    private int resProQty2; // ���ƹ���2ʵ������

    private int resProQty3; // ���ƹ���3ʵ������

    private int proQty1; // ����1ʵ������

    private int proQty2; // ����2ʵ������

    private int proQty3; // ����3ʵ������

    private int proQty4; // ����4ʵ������

    private int proQty5; // ����5ʵ������

    private int proQty6; // ����6ʵ������

    public DailyProcessQty() {
        super();
    }

    public DailyProcessQty(String billNo, String dailyNo, LocalDate productDate, int planQty, int resProQty1, int resProQty2, int resProQty3,
            int proQty1, int proQty2, int proQty3, int proQty4, int proQty5, int proQty6) {
        super();
        this.productDate = productDate;
        this.billNo = billNo;
        this.dailyNo = dailyNo;
        this.planQty = planQty;
        this.resProQty1 = resProQty1;
        this.resProQty2 = resProQty2;
        this.resProQty3 = resProQty3;
        this.proQty1 = proQty1;
        this.proQty2 = proQty2;
        this.proQty3 = proQty3;
        this.proQty4 = proQty4;
        this.proQty5 = proQty5;
        this.proQty6 = proQty6;
    }
    
    public LocalDate getProductDate() {
        return productDate;
    }
    
    public void setProductDate(LocalDate productDate) {
        this.productDate = productDate;
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

    public int getPlanQty() {
        return planQty;
    }

    public void setPlanQty(int planQty) {
        this.planQty = planQty;
    }

    public int getResProQty1() {
        return resProQty1;
    }

    public void setResProQty1(int resProQty1) {
        this.resProQty1 = resProQty1;
    }

    public int getResProQty2() {
        return resProQty2;
    }

    public void setResProQty2(int resProQty2) {
        this.resProQty2 = resProQty2;
    }

    public int getResProQty3() {
        return resProQty3;
    }

    public void setResProQty3(int resProQty3) {
        this.resProQty3 = resProQty3;
    }

    public int getProQty1() {
        return proQty1;
    }

    public void setProQty1(int proQty1) {
        this.proQty1 = proQty1;
    }

    public int getProQty2() {
        return proQty2;
    }

    public void setProQty2(int proQty2) {
        this.proQty2 = proQty2;
    }

    public int getProQty3() {
        return proQty3;
    }

    public void setProQty3(int proQty3) {
        this.proQty3 = proQty3;
    }

    public int getProQty4() {
        return proQty4;
    }

    public void setProQty4(int proQty4) {
        this.proQty4 = proQty4;
    }

    public int getProQty5() {
        return proQty5;
    }

    public void setProQty5(int proQty5) {
        this.proQty5 = proQty5;
    }

    public int getProQty6() {
        return proQty6;
    }

    public void setProQty6(int proQty6) {
        this.proQty6 = proQty6;
    }
}
