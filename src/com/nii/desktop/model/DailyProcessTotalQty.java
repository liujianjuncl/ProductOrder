package com.nii.desktop.model;

public class DailyProcessTotalQty {

    private String billNo; // 生产任务单编号

    private int planQuantity; // 计划生产数量

    private int resProcessTotalQty1; // 改制工序1累计实作数量

    private int resProcessTotalQty2; // 改制工序2累计实作数量

    private int resProcessTotalQty3; // 改制工序3累计实作数量

    private int processTotalQty1; // 工序1累计实作数量

    private int processTotalQty2; // 工序2累计实作数量

    private int processTotalQty3; // 工序3累计实作数量

    private int processTotalQty4; // 工序4累计实作数量

    private int processTotalQty5; // 工序5累计实作数量

    private int processTotalQty6; // 工序6累计实作数量

    private int processTotalQty7; // 工序7累计实作数量

    private int processTotalQty8; // 工序8累计实作数量

    private int processTotalQty9; // 工序9累计实作数量

    public DailyProcessTotalQty() {
        super();
    }

    public DailyProcessTotalQty(String billNo, int planQuantity, int resProcessTotalQty1, int resProcessTotalQty2,
            int resProcessTotalQty3, int processTotalQty1, int processTotalQty2, int processTotalQty3,
            int processTotalQty4, int processTotalQty5, int processTotalQty6, int processTotalQty7,
            int processTotalQty8, int processTotalQty9) {
        super();
        this.billNo = billNo;
        this.planQuantity = planQuantity;
        this.resProcessTotalQty1 = resProcessTotalQty1;
        this.resProcessTotalQty2 = resProcessTotalQty2;
        this.resProcessTotalQty3 = resProcessTotalQty3;
        this.processTotalQty1 = processTotalQty1;
        this.processTotalQty2 = processTotalQty2;
        this.processTotalQty3 = processTotalQty3;
        this.processTotalQty4 = processTotalQty4;
        this.processTotalQty5 = processTotalQty5;
        this.processTotalQty6 = processTotalQty6;
        this.processTotalQty7 = processTotalQty7;
        this.processTotalQty8 = processTotalQty8;
        this.processTotalQty9 = processTotalQty9;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public int getPlanQuantity() {
        return planQuantity;
    }

    public void setPlanQuantity(int planQuantity) {
        this.planQuantity = planQuantity;
    }

    public int getResProcessTotalQty1() {
        return resProcessTotalQty1;
    }

    public void setResProcessTotalQty1(int resProcessTotalQty1) {
        this.resProcessTotalQty1 = resProcessTotalQty1;
    }

    public int getResProcessTotalQty2() {
        return resProcessTotalQty2;
    }

    public void setResProcessTotalQty2(int resProcessTotalQty2) {
        this.resProcessTotalQty2 = resProcessTotalQty2;
    }

    public int getResProcessTotalQty3() {
        return resProcessTotalQty3;
    }

    public void setResProcessTotalQty3(int resProcessTotalQty3) {
        this.resProcessTotalQty3 = resProcessTotalQty3;
    }

    public int getProcessTotalQty1() {
        return processTotalQty1;
    }

    public void setProcessTotalQty1(int processTotalQty1) {
        this.processTotalQty1 = processTotalQty1;
    }

    public int getProcessTotalQty2() {
        return processTotalQty2;
    }

    public void setProcessTotalQty2(int processTotalQty2) {
        this.processTotalQty2 = processTotalQty2;
    }

    public int getProcessTotalQty3() {
        return processTotalQty3;
    }

    public void setProcessTotalQty3(int processTotalQty3) {
        this.processTotalQty3 = processTotalQty3;
    }

    public int getProcessTotalQty4() {
        return processTotalQty4;
    }

    public void setProcessTotalQty4(int processTotalQty4) {
        this.processTotalQty4 = processTotalQty4;
    }

    public int getProcessTotalQty5() {
        return processTotalQty5;
    }

    public void setProcessTotalQty5(int processTotalQty5) {
        this.processTotalQty5 = processTotalQty5;
    }

    public int getProcessTotalQty6() {
        return processTotalQty6;
    }

    public void setProcessTotalQty6(int processTotalQty6) {
        this.processTotalQty6 = processTotalQty6;
    }

    public int getProcessTotalQty7() {
        return processTotalQty7;
    }

    public void setProcessTotalQty7(int processTotalQty7) {
        this.processTotalQty7 = processTotalQty7;
    }

    public int getProcessTotalQty8() {
        return processTotalQty8;
    }

    public void setProcessTotalQty8(int processTotalQty8) {
        this.processTotalQty8 = processTotalQty8;
    }

    public int getProcessTotalQty9() {
        return processTotalQty9;
    }

    public void setProcessTotalQty9(int processTotalQty9) {
        this.processTotalQty9 = processTotalQty9;
    }

}
