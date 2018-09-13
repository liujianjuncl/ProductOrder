package com.nii.desktop.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

import com.nii.desktop.model.Student;
import com.nii.desktop.model.User;

/**
 * Created by wzj on 2018/1/7.
 */
public class TableViewTestController implements Initializable {

    /**
     * ���
     */
    @FXML
    private TableView<User> userTableView;

    /**
     * ����
     */
    ObservableList<User> data = FXCollections.observableArrayList();

    @FXML
    Label userLabel;

    /**
     * name��
     */
    @FXML
    TableColumn<User, String> userNoCol;

    /**
     * name��
     */
    @FXML
    TableColumn<User, Integer> userNameCol;

    /**
     * �Ƿ�Ƽ�
     */
    @FXML
    TableColumn<User, String> isPieceworkCol;

    /**
     * �Ƿ����Ա
     */
    @FXML
    TableColumn<User, String> isManagerCol;

    /**
     * �Ƿ����
     */
    @FXML
    TableColumn<User, String> isDisableCol;

    /**
     * ������
     */
    @FXML
    TableColumn<User, String> operation;

    /**
     * Called to initialize a controller after its root element has been completely
     * processed.
     *
     * @param location  The location used to resolve relative paths for the root
     *                  object, or <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or
     *                  <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userNoCol.setText("000012312");
        userNameCol.setText("asldkas");
        isPieceworkCol.setText("��");
        isManagerCol.setText("��");
        isDisableCol.setText("��");
        operation.setText("����");
//        nameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
//        ageCol.setCellValueFactory(new PropertyValueFactory<Student, Integer>("age"));
//        descCol.setCellValueFactory(new PropertyValueFactory<Student, String>("desc"));

        userTableView.setItems(data);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        });

    }

    /**
     * ��ʼ���������
     */
    private void initData() {
//        data.add(new Student("����", 10, "��ѧ��"));
//        data.add(new Student("����", 13, "ѧϰ�ǳ�Ŭ��"));
//        data.add(new Student("����", 16, "��ѧ��"));
//        System.out.println(userLabel.getHeight() + "----" + userLabel.getWidth());
    }
}
