package com.nii.desktop.model;

import java.sql.Timestamp;

import javafx.scene.control.CheckBox;

public class WorkDetail implements Cloneable {

    private CheckBox checkbox; // 复选框

    // 作业明细编号
    private String workDetailNo;
    
    // 作业日期
    private Timestamp workDate;

    // 审核状态 1--已审核，0--未审核
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
    
    // 创建人员姓名
    private String createUserName;
    
    // 修改人员姓名
    private String modifyUserName;
    
    // 审核人员姓名
    private String auditorName;
    
    // 备注
    private String remark;
    
    public WorkDetail() {
		super();
	}

    public WorkDetail(CheckBox checkbox, String workDetailNo, Timestamp workDate, String status, String workNo, String workName,
            String unit, double unitPrice, int workNum, double money, String createUser, Timestamp createTime,
            String modifyUser, Timestamp modifyTime, String auditor, Timestamp auditorTime,
            String createUserName, String modifyUserName, String auditorName, String remark) {
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
        this.createUserName = createUserName;
        this.modifyUserName = modifyUserName;
        this.auditorName = auditorName;
        this.remark = remark;
    }

    public WorkDetail(String workDetailNo, Timestamp workDate, String status, String workNo, String workName, String unit, double unitPrice,
            int workNum, double money, String createUser, Timestamp createTime, String modifyUser, Timestamp modifyTime,
            String auditor, Timestamp auditorTime, String createUserName, String modifyUserName, String auditorName, String remark) {
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
        this.createUserName = createUserName;
        this.modifyUserName = modifyUserName;
        this.auditorName = auditorName;
        this.remark = remark;
    }
    
    public WorkDetail clone() throws CloneNotSupportedException {
        return (WorkDetail) super.clone();
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

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
    
}
