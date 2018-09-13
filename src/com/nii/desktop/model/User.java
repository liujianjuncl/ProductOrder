package com.nii.desktop.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by ljj on 2018/9/13
 */
public class User {

    // 用户编号
    private SimpleStringProperty userNo;

    // 用户姓名
    private SimpleStringProperty userName;

    // 密码
    private SimpleStringProperty password;

    // 是否计件 1:是，0：否
    private SimpleIntegerProperty isPiecework;

    // 是否管理员 1:是，0：否
    private SimpleIntegerProperty isManager;

    // 是否禁用 1:是，0：否
    private SimpleIntegerProperty isDisable;

    public User() {

    }

    public User(SimpleStringProperty userNo, SimpleStringProperty userName, SimpleStringProperty password,
            SimpleIntegerProperty isPiecework, SimpleIntegerProperty isManager, SimpleIntegerProperty isDisable) {
        this.userNo = userNo;
        this.userName = userName;
        this.password = password;
        this.isPiecework = isPiecework;
        this.isManager = isManager;
        this.isDisable = isDisable;
    }

    public SimpleStringProperty getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo.set(userNo);
    }

    public SimpleStringProperty getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public SimpleStringProperty getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public SimpleIntegerProperty getIsPiecework() {
        return isPiecework;
    }

    public void setIsPiecework(int isPiecework) {
        this.isPiecework.set(isPiecework);
    }

    public SimpleIntegerProperty getIsManager() {
        return isManager;
    }

    public void setIsManager(int isManager) {
        this.isManager.set(isManager);
    }

    public SimpleIntegerProperty getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(int isDisable) {
        this.isDisable.set(isDisable);
    }

}
