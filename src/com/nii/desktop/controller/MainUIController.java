package com.nii.desktop.controller;

import com.nii.desktop.util.conf.DailyUtil;
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

    /* Êý×Ö¿ò*/
    @FXML
    private TextField numTextField;

    /**Ãû×Ö¿ò*/
    @FXML
    private TextField nameTextField;

    @FXML
    private WebView webview;

    @FXML
    private TextField urlTextField;

    /* ÈÝÆ÷*/
    @FXML
    private TabPane tabPane;
    
    @FXML
    private AnchorPane rightPane;
    
    @FXML
    private TableView<?> userTableView;

    /*web engine*/
    WebEngine webEngine;
    
    @FXML
    private Button userManageButton;
    
    @FXML
    private Button indirectWorkManageButton;
    
    @FXML
    private Button indirectWorkProjectButton;
    
    @FXML
    private Button dailyManageButton;
    
    private VBox userVbox;
    
    private VBox dailyVbox;
    
    @FXML
    private Label currentUser;
    
    @FXML
    private Label money;
    
    @FXML
    private Label billCount;
    
    public void setMoney(String m) {
        this.money.setText(m);
    }
    
    public void setBillCount(String count) {
        this.billCount.setText(count);
    }

    /***/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (SessionUtil.CONTROLLERS.get("MainUIController") == null) {
            SessionUtil.CONTROLLERS.put("MainUIController", this);
        }
        dailyManageButton.setStyle("-fx-background-color: #808080");
        loadDailyTableView();
        
        DailyUtil.queryBillnoCount();
        money.setText("0.0");
        currentUser.setText(SessionUtil.USERS.get("loginUser").getUserName());
        
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
//        indirectWorkManageButton.setStyle(null);
//        indirectWorkProjectButton.setStyle(null);
        rightPane.getChildren().clear();
        loadUserTableView();
        money.setText("0.0");
    }
    
    @FXML
    private void dailyLabelClickAction() {
        dailyManageButton.setStyle("-fx-background-color: #808080");
        userManageButton.setStyle(null);
//        indirectWorkManageButton.setStyle(null);
//        indirectWorkProjectButton.setStyle(null);
        rightPane.getChildren().clear();
        loadDailyTableView();
        money.setText("0.0");
    }
    
    @FXML
//    private void indirectWorkClickAction() {
////        indirectWorkManageButton.setStyle("-fx-background-color: #808080");
//        userManageButton.setStyle(null);
//        dailyManageButton.setStyle(null);
////        indirectWorkProjectButton.setStyle(null);
//        rightPane.getChildren().clear();
//        money.setText("0.0");
//    }
    
//    @FXML
//    private void indirectWorkProjectClickAction() {
////        indirectWorkProjectButton.setStyle("-fx-background-color: #808080");
//        userManageButton.setStyle(null);
//        dailyManageButton.setStyle(null);
////        indirectWorkManageButton.setStyle(null);
//        rightPane.getChildren().clear();
//        money.setText("0.0");
//    }


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
