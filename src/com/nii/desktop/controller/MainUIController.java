package com.nii.desktop.controller;

import com.nii.desktop.util.ui.ResourceLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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

    /* 数字框*/
    @FXML
    private TextField numTextField;

    /**名字框*/
    @FXML
    private TextField nameTextField;

    @FXML
    private WebView webview;

    @FXML
    private TextField urlTextField;

    /* 容器*/
    @FXML
    private TabPane tabPane;
    
    @FXML
    private AnchorPane rightPane;
    
    @FXML
    private TableView userTableView;

    /*web engine*/
    WebEngine webEngine;
    
    @FXML
    Button userManageButton;
    
    @FXML
    Button dailyManageButton;
    
    VBox userVbox;
    
    VBox dailyVbox;

    /***/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userManageButton.setStyle("-fx-background-color: #808080");
        loadUserTableView();
        
        
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
        rightPane.getChildren().removeAll(dailyVbox);
        loadUserTableView();
    }
    
    @FXML
    private void dailyLabelClickAction() {
        dailyManageButton.setStyle("-fx-background-color: #808080");
        userManageButton.setStyle(null);
        rightPane.getChildren().removeAll(userVbox);
        loadDailyTableView();
    }


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
    
    /*回车事件*/
    @FXML
    private void urlTextFieldAction() {
        goBtClickAction();
    }

    /*Go按钮点击事件*/
    @FXML
    private void goBtClickAction() {
        webEngine.load(urlTextField.getText());
    }
}
