package com.nii.desktop.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

/**
 * Created by ljj on 2018/9/13
 */
public class User {
//
//    // 复选框
    private CheckBox checkbox = new CheckBox();

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

    public User() {

    }

    public User(String userNo, String userName, String password, String isPiecework, String isManager,
            String isDisable) {
        setUserNo(userNo);
        setUserName(userName);
        setPassword(password);
        setIsPiecework(isPiecework);
        setIsManager(isManager);
        setIsDisable(isDisable);
    }

    public User(CheckBox checkbox, String userNo, String userName, String isPiecework, String isManager,
            String isDisable) {
        setCheckbox(checkbox);
        setUserNo(userNo);
        setUserName(userName);
        setIsPiecework(isPiecework);
        setIsManager(isManager);
        setIsDisable(isDisable);
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

}
