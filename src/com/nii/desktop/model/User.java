package com.nii.desktop.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

/**
 * Created by ljj on 2018/9/13
 */
public class User {

    // 复选框
    private CheckBox checkbox;

    // 用户编号
    private SimpleStringProperty userNo = new SimpleStringProperty();

    // 用户姓名
    private SimpleStringProperty userName = new SimpleStringProperty();

    // 密码
    private SimpleStringProperty password = new SimpleStringProperty();

    // 是否计件 1:是，0：否
    private SimpleStringProperty isPiecework = new SimpleStringProperty();

    // 是否管理员 1:是，0：否
    private SimpleStringProperty isManager = new SimpleStringProperty();

    // 是否禁用 1:是，0：否
    private SimpleStringProperty isDisable = new SimpleStringProperty();

    // 是否审核员 1:是，0：否
    private SimpleStringProperty isAuditor = new SimpleStringProperty();

    // 审核员
    private SimpleStringProperty auditor = new SimpleStringProperty();
    
    // 审核员姓名
    private SimpleStringProperty auditorName = new SimpleStringProperty();

    // 定义为 final 好像是一种规范做法
    private StringProperty status = new SimpleStringProperty();

    public User() {

    }

    public User(String userNo, String userName, String password, String isPiecework, String isManager, String isDisable,
            String isAuditor, String auditor, String auditorName) {
        setUserNo(userNo);
        setUserName(userName);
        setPassword(password);
        setIsPiecework(isPiecework);
        setIsManager(isManager);
        setIsDisable(isDisable);
        setIsAuditor(isAuditor);
        setAuditor(auditor);
        setauditorName(auditorName);
    }

    public User(CheckBox checkbox, String userNo, String userName, String isPiecework, String isManager,
            String isDisable, String isAuditor, String auditor, String auditorName) {
        setCheckbox(checkbox);
        setUserNo(userNo);
        setUserName(userName);
        setIsPiecework(isPiecework);
        setIsManager(isManager);
        setIsDisable(isDisable);
        setIsAuditor(isAuditor);
        setAuditor(auditor);
        setauditorName(auditorName);
    }

    public CheckBox getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(CheckBox checkbox) {
        this.checkbox = checkbox;
    }

    public String getUserNo() {
        return userNo.get();
    }

    public SimpleStringProperty userNoProperty() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo.set(userNo);
    }

    public String getUserName() {
        return userName.get();
    }

    public SimpleStringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getIsPiecework() {
        return isPiecework.get();
    }

    public SimpleStringProperty isPieceworkProperty() {
        return isPiecework;
    }

    public void setIsPiecework(String isPiecework) {
        this.isPiecework.set(isPiecework);
    }

    public String getIsManager() {
        return isManager.get();
    }

    public SimpleStringProperty isManagerProperty() {
        return isManager;
    }

    public void setIsManager(String isManager) {
        this.isManager.set(isManager);
    }

    public String getIsDisable() {
        return isDisable.get();
    }

    public SimpleStringProperty isDisableProperty() {
        return isDisable;
    }

    public void setIsDisable(String isDisable) {
        this.isDisable.set(isDisable);
    }

    public String getIsAuditor() {
        return isAuditor.get();
    }

    public SimpleStringProperty isAuditorProperty() {
        return isAuditor;
    }

    public void setIsAuditor(String isAuditor) {
        this.isAuditor.set(isAuditor);
    }
    
    public String getAuditor() {
        return auditor.get();
    }

    public SimpleStringProperty auditorProperty() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor.set(auditor);
    }
    
    public String getauditorName() {
        return auditorName.get();
    }

    public SimpleStringProperty auditorNameProperty() {
        return auditorName;
    }

    public void setauditorName(String auditorName) {
        this.auditorName.set(auditorName);
    }

    // * 特别说明： xxxProperty 方法名，是 fx 的规范，只要属性名加上 Peoperty() 作为方法名，fx 就能自动监听该属性的变化！
    public StringProperty statusProperty() {
        return status;
    }

    // 原有的 set 方法，并不受字段类型变化而改变，仍然接受同样的参数，只是方法体需要修改一下
    public void setStatus(String status) {
        this.status.set(status);
    }

    // 原有的 get 方法，并不受字段类型变化而改变，仍然返回同样的类型，只是方法体需要修改一下
    public String getStatus() {
        return status.get();
    }

}
