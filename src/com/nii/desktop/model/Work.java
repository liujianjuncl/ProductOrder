package com.nii.desktop.model;

public class Work {

    // 间接作业编号
    private String workNo;

    // 间接作业名称
    private String workName;

    // 计量单位
    private String unit;

    // 单位单价
    private double unitPrice;

    // 使用状态
    private String status;

    public Work(String workNo, String workName, String unit, double unitPrice, String status) {
        super();
        this.workNo = workNo;
        this.workName = workName;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.status = status;
    }

    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
