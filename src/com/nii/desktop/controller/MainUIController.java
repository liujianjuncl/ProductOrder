package com.nii.desktop.controller;

import com.nii.desktop.util.conf.SessionUtil;
import com.nii.desktop.util.ui.ResourceLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by ljj on 2018/9/14
 */
public class MainUIController implements Initializable {
    
    @FXML
    private SplitPane splitPane;

    /* ���ֿ�*/
    @FXML
    private TextField numTextField;

    /**���ֿ�*/
    @FXML
    private TextField nameTextField;

    @FXML
    private WebView webview;

    @FXML
    private TextField urlTextField;

    /* ����*/
    @FXML
    private TabPane tabPane;
    
    @FXML
    private AnchorPane rightPane;
    
    @FXML
    private TableView<?> userTableView;

    /*web engine*/
    WebEngine webEngine;
    
    @FXML
    Button userManageButton;
    
    @FXML
    Button dailyManageButton;
    
    VBox userVbox;
    
    VBox dailyVbox;
    
    @FXML
    private Label currentUser;

    /***/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dailyManageButton.setStyle("-fx-background-color: #808080");
        loadDailyTableView();
        
        currentUser.setText("��ǰ�û���" + SessionUtil.USERS.get("loginUser").getUserName());
        
//        numTextField.setEditable(false);
//        adbDevice.setDeviceNumber(0);
//
//        webEngine = webview.getEngine();
//
//        loadListViewTestTab();
//        loadTableViewTestTab();

    }
    
    @FXML
    private void userLabelClickAction() {
        userManageButton.setStyle("-fx-background-color: #808080");
        dailyManageButton.setStyle(null);
        rightPane.getChildren().clear();
        loadUserTableView();
    }
    
    @FXML
    private void dailyLabelClickAction() {
        dailyManageButton.setStyle("-fx-background-color: #808080");
        userManageButton.setStyle(null);
        rightPane.getChildren().clear();
        loadDailyTableView();
    }


    @SuppressWarnings("static-access")
    private void loadUserTableView() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ResourceLoader.getFxmlResource("UserTableView.fxml"));

        try {
            
            userVbox = fxmlLoader.load();
            rightPane.setLeftAnchor(userVbox, 0.0);
            rightPane.setRightAnchor(userVbox, 0.0);
            rightPane.setBottomAnchor(userVbox, 0.0);
            rightPane.setTopAnchor(userVbox, 0.0);
            rightPane.getChildren().add(userVbox);
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("static-access")
    private void loadDailyTableView() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ResourceLoader.getFxmlResource("DailyTableView.fxml"));

        try {
            
            dailyVbox = fxmlLoader.load();
            rightPane.setLeftAnchor(dailyVbox, 0.0);
            rightPane.setRightAnchor(dailyVbox, 0.0);
            rightPane.setBottomAnchor(dailyVbox, 0.0);
            rightPane.setTopAnchor(dailyVbox, 0.0);
            rightPane.getChildren().add(dailyVbox);
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
