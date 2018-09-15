package com.nii.desktop.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.nii.desktop.dialog.HostServerDialog;
import com.nii.desktop.model.HostServer;
import com.nii.desktop.model.User;
import com.nii.desktop.util.ui.ResourceLoader;
import com.nii.desktop.util.ui.UIManager;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * Created by wzj on 2018/1/7.
 */
public class TableViewTestController implements Initializable {

    @FXML
    private VBox userVBox;
    
    /*表格*/
    @FXML
    private TableView<User> userTableView;

    /*数据*/
    private ObservableList<User> data = FXCollections.observableArrayList();

    @FXML
    private Label userLabel;

    @FXML
    private HBox userHbox;

    /*selectCol*/
    @FXML
    private TableColumn<User, CheckBox> selectCol;

    /*userNo列*/
    @FXML
    private TableColumn<User, String> userNoCol;

    /*name列*/
    @FXML
    private TableColumn<User, String> userNameCol;

    /*是否计件*/
    @FXML
    private TableColumn<User, String> isPieceworkCol;

    /*是否管理员*/
    @FXML
    private TableColumn<User, String> isManagerCol;

    /*是否禁用*/
    @FXML
    private TableColumn<User, String> isDisableCol;

    /*系统stage*/
    private static Stage dialogStage;
    
    public static Stage getdialogStage() {
        return dialogStage;
     }

     public static void setPrimaryStage(Stage stage) {
         dialogStage = stage;
     }
     
    /** 初始化 */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        
        selectCol.setCellValueFactory(new PropertyValueFactory<User, CheckBox>("checkbox"));
        userNoCol.setCellValueFactory(new PropertyValueFactory<User, String>("userNo"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
        isPieceworkCol.setCellValueFactory(new PropertyValueFactory<User, String>("isPiecework"));
        isManagerCol.setCellValueFactory(new PropertyValueFactory<User, String>("isManager"));
        isDisableCol.setCellValueFactory(new PropertyValueFactory<User, String>("isDisable"));

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
        System.out.println("===========init=============");
        data.add(new User(new CheckBox(), "123456", "张三", "是", "是", "是"));
        data.add(new User(new CheckBox(), "654321", "张三", "是", "是", "是"));
        data.add(new User(new CheckBox(), "123456", "张三", "是", "是", "是"));
        data.add(new User(new CheckBox(), "123456", "张三", "是", "是", "是"));
        data.add(new User(new CheckBox(), "123456", "张三", "是", "是", "是"));
        data.add(new User(new CheckBox(), "123456", "张三", "是", "是", "是"));
        data.add(new User(new CheckBox(), "123456", "张三", "是", "是", "是"));
        data.add(new User(new CheckBox(), "123456", "张三", "是", "是", "是"));
        data.add(new User(new CheckBox(), "123456", "张三", "是", "是", "是"));
        data.add(new User(new CheckBox(), "123456", "张三", "是", "是", "是"));
        data.add(new User(new CheckBox(), "123456", "张三", "是", "是", "是"));
        data.add(new User(new CheckBox(), "123456", "张三", "是", "是", "是"));
        data.add(new User(new CheckBox(), "123456", "张三", "是", "是", "是"));
        data.add(new User(new CheckBox(), "123456", "张三", "是", "是", "是"));
        data.add(new User(new CheckBox(), "123456", "张三", "是", "是", "是"));
        data.add(new User(new CheckBox(), "123456", "张三", "是", "是", "是"));
        data.add(new User(new CheckBox(), "123456", "张三", "是", "是", "是"));

    }

    @FXML
    private void addUser() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ResourceLoader.getFxmlResource("AddUser.fxml"));
        
        AnchorPane addUserPane;
        
        try {
            addUserPane = fxmlLoader.load();

            dialogStage = new Stage();
            dialogStage.setTitle("添加用户");

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(UIManager.getPrimaryStage());
            Scene scene = new Scene(addUserPane);
            dialogStage.setScene(scene);

            dialogStage.setResizable(false);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
