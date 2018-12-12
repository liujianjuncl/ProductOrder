package com.nii.desktop.model;

import java.sql.Timestamp;

import javafx.scene.control.CheckBox;

public class WorkDetail {

    private CheckBox checkbox; // 复选框

    // 作业明细编号
    private String workDetailNo;
    
    // 作业日期
    private Timestamp workDate;

    // 状态
    private String status;

    // 作业编号 
    private String workNo;

    // 作业名称
    private String workName;

    // 计量单位
    private String unit;

    // 单位单价
    private double unitPrice;

    // 实际作业数量
    private int workNum;

    // 作业金额
    private double money;

    // 创建人
    private String createUser;

    // 创建时间
    private Timestamp createTime;

    // 修改人
    private String modifyUser;

    // 修改时间
    private Timestamp modifyTime;

    // 审核人
    private String auditor;

    // 审核时间
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
