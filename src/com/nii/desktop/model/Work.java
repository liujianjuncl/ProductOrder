package com.nii.desktop.model;

public class Work {

    // �����ҵ���
    private String workNo;

    // �����ҵ����
    private String workName;

    // ������λ
    private String unit;

    // ��λ����
    private double unitPrice;

    // ʹ��״̬
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
