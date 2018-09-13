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
     * 表格
     */
    @FXML
    private TableView<User> userTableView;

    /**
     * 数据
     */
    ObservableList<User> data = FXCollections.observableArrayList();

    @FXML
    Label userLabel;

    /**
     * name列
     */
    @FXML
    TableColumn<User, String> userNoCol;

    /**
     * name列
     */
    @FXML
    TableColumn<User, Integer> userNameCol;

    /**
     * 是否计件
     */
    @FXML
    TableColumn<User, String> isPieceworkCol;

    /**
     * 是否管理员
     */
    @FXML
    TableColumn<User, String> isManagerCol;

    /**
     * 是否禁用
     */
    @FXML
    TableColumn<User, String> isDisableCol;

    /**
     * 操作列
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
        isPieceworkCol.setText("是");
        isManagerCol.setText("是");
        isDisableCol.setText("是");
        operation.setText("增加");
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
     * 初始化表格数据
     */
    private void initData() {
//        data.add(new Student("张三", 10, "数学好"));
//        data.add(new Student("李四", 13, "学习非常努力"));
//        data.add(new Student("王五", 16, "好学生"));
//        System.out.println(userLabel.getHeight() + "----" + userLabel.getWidth());
    }
}
