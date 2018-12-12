package com.nii.desktop.model;

import java.sql.Timestamp;

import javafx.scene.control.CheckBox;

public class WorkDetail {

    private CheckBox checkbox; // ��ѡ��

    // ��ҵ��ϸ���
    private String workDetailNo;
    
    // ��ҵ����
    private Timestamp workDate;

    // ״̬
    private String status;

    // ��ҵ��� 
    private String workNo;

    // ��ҵ����
    private String workName;

    // ������λ
    private String unit;

    // ��λ����
    private double unitPrice;

    // ʵ����ҵ����
    private int workNum;

    // ��ҵ���
    private double money;

    // ������
    private String createUser;

    // ����ʱ��
    private Timestamp createTime;

    // �޸���
    private String modifyUser;

    // �޸�ʱ��
    private Timestamp modifyTime;

    // �����
    private String auditor;

    // ���ʱ��
    private Timestamp auditorTime;

    public WorkDetail(CheckBox checkbox, String workDetailNo, Timestamp workDate, String status, String workNo, String workName,
            String unit, double unitPrice, int workNum, double money, String createUser, Timestamp createTime,
            String modifyUser, Timestamp modifyTime, String auditor, Timestamp auditorTime) {
        super();
        this.checkbox = checkbox;
        this.workDetailNo = workDetailNo;
        this.workDate = workDate;
        this.status = status;
        this.workNo = workNo;
        this.workName = workName;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.workNum = workNum;
        this.money = money;
        this.createUser = createUser;
        this.createTime = createTime;
        this.modifyUser = modifyUser;
        this.modifyTime = modifyTime;
        this.auditor = auditor;
        this.auditorTime = auditorTime;
    }

    public WorkDetail(String workDetailNo, Timestamp workDate, String status, String workNo, String workName, String unit, double unitPrice,
            int workNum, double money, String createUser, Timestamp createTime, String modifyUser, Timestamp modifyTime,
            String auditor, Timestamp auditorTime) {
        super();
        this.workDetailNo = workDetailNo;
        this.workDate = workDate;
        this.status = status;
        this.workNo = workNo;
        this.workName = workName;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.workNum = workNum;
        this.money = money;
        this.createUser = createUser;
        this.createTime = createTime;
        this.modifyUser = modifyUser;
        this.modifyTime = modifyTime;
        this.auditor = auditor;
        this.auditorTime = auditorTime;
    }

    public CheckBox getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(CheckBox checkbox) {
        this.checkbox = checkbox;
    }

    public String getWorkDetailNo() {
        return workDetailNo;
    }

    public void setWorkDetailNo(String workDetailNo) {
        this.workDetailNo = workDetailNo;
    }

    public Timestamp getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Timestamp workDate) {
        this.workDate = workDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public int getWorkNum() {
        return workNum;
    }

    public void setWorkNum(int workNum) {
        this.workNum = workNum;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
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

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public Timestamp getAuditorTime() {
        return auditorTime;
    }

    public void setAuditorTime(Timestamp auditorTime) {
        this.auditorTime = auditorTime;
    }
}
