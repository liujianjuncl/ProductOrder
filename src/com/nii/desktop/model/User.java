package com.nii.desktop.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

/**
 * Created by ljj on 2018/9/13
 */
public class User {

    // ��ѡ��
    private CheckBox checkbox = new CheckBox();

    // �û����
    private SimpleStringProperty userNo = new SimpleStringProperty();

    // �û�����
    private SimpleStringProperty userName = new SimpleStringProperty();

    // ����
    private SimpleStringProperty password = new SimpleStringProperty();

    // �Ƿ�Ƽ� 1:�ǣ�0����
    private SimpleStringProperty isPiecework = new SimpleStringProperty();

    // �Ƿ����Ա 1:�ǣ�0����
    private SimpleStringProperty isManager = new SimpleStringProperty();

    // �Ƿ���� 1:�ǣ�0����
    private SimpleStringProperty isDisable = new SimpleStringProperty();

    // ����Ϊ final ������һ�ֹ淶����
    private StringProperty status = new SimpleStringProperty();

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

    // * �ر�˵���� xxxProperty ���������� fx �Ĺ淶��ֻҪ���������� Peoperty() ��Ϊ��������fx �����Զ����������Եı仯��
    public StringProperty statusProperty() {
        return status;
    }

    // ԭ�е� set �������������ֶ����ͱ仯���ı䣬��Ȼ����ͬ���Ĳ�����ֻ�Ƿ�������Ҫ�޸�һ��
    public void setStatus(String status) {
        this.status.set(status);
    }

    // ԭ�е� get �������������ֶ����ͱ仯���ı䣬��Ȼ����ͬ�������ͣ�ֻ�Ƿ�������Ҫ�޸�һ��
    public String getStatus() {
        return status.get();
    }

}