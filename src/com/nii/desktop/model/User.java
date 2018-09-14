package com.nii.desktop.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by ljj on 2018/9/13
 */
public class User {

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

	public User(String userNo, String userName, String isPiecework, String isManager, String isDisable) {
		setUserNo(userNo);
		setUserName(userName);
		setIsPiecework(isPiecework);
		setIsManager(isManager);
		setIsDisable(isDisable);
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
		return userNo;
	}

	public void setUserName(String userName) {
		this.userName.set(userName);
	}

	public String getPassword() {
		return password.get();
	}
	
	public SimpleStringProperty passwordProperty() {
		return userNo;
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	public String getIsPiecework() {
		return isPiecework.get();
	}
	
	public SimpleStringProperty isPieceworkProperty() {
		return userNo;
	}

	public void setIsPiecework(String isPiecework) {
		this.isPiecework.set(isPiecework);
	}

	public String getIsManager() {
		return isManager.get();
	}
	
	public SimpleStringProperty isManagerProperty() {
		return userNo;
	}

	public void setIsManager(String isManager) {
		this.isManager.set(isManager);
	}

	public String getIsDisable() {
		return isDisable.get();
	}
	
	public SimpleStringProperty isDisableProperty() {
		return userNo;
	}

	public void setIsDisable(String isDisable) {
		this.isDisable.set(isDisable);
	}

}
