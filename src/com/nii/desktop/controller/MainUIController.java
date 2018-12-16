package com.nii.desktop.controller;

import com.nii.desktop.util.conf.DailyUtil;
import com.nii.desktop.util.conf.SessionUtil;
import com.nii.desktop.util.conf.WorkUtil;
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

    /* Êý×Ö¿ò */
    @FXML
    private TextField numTextField;

    /** Ãû×Ö¿ò */
    @FXML
    private TextField nameTextField;

    @FXML
    private WebView webview;

    @FXML
    private TextField urlTextField;

    /* ÈÝÆ÷ */
    @FXML
    private TabPane tabPane;

    @FXML
    private AnchorPane rightPane;

    @FXML
    private TableView<?> userTableView;

    /* web engine */
    WebEngine webEngine;

    @FXML
    private Button userManageButton;

    @FXML
    private Button workDetailManageButton;

    @FXML
    private Button workManageButton;

    @FXML
    private Button dailyManageButton;

    private VBox userVbox;

    private VBox dailyVbox;
    
    private VBox workVbox;
    
    private VBox workDetailVbox;

    @FXML
    private Label currentUser;

    @FXML
    private Label dailyMoney;

    @FXML
    private Label billCount;
    
    @FXML
    private Label workMoney;
    
    @FXML
    private Label sumMoney;

    public void setDailyMoney(String m) {
        this.dailyMoney.setText(m);
    }
    
    public void setWorkMoney(String m) {
        this.workMoney.setText(m);
    }

    public void setBillCount(String count) {
        this.billCount.setText(count);
    }
    
    public void setSumMoney(String m) {
        this.sumMoney.setText(m);
    }

    /***/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (SessionUtil.CONTROLLERS.get("MainUIController") == null) {
            SessionUtil.CONTROLLERS.put("MainUIController", this);
        }
        dailyManageButton.setStyle("-fx-background-color: #808080");
        loadDailyTableView();

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
        workManageButton.setStyle(null);
        workDetailManageButton.setStyle(null);
        rightPane.getChildren().clear();
        loadUserTableView();
    }

    @FXML
    private void dailyLabelClickAction() {
        dailyManageButton.setStyle("-fx-background-color: #808080");
        userManageButton.setStyle(null);
        workManageButton.setStyle(null);
        workDetailManageButton.setStyle(null);
        rightPane.getChildren().clear();
        loadDailyTableView();
    }

    @FXML
    private void workDetailClickAction() {
        workDetailManageButton.setStyle("-fx-background-color: #808080");
        userManageButton.setStyle(null);
        dailyManageButton.setStyle(null);
        workManageButton.setStyle(null);
        rightPane.getChildren().clear();
        loadWorkDetailTableView();
    }

    @FXML
    private void workClickAction() {
        workManageButton.setStyle("-fx-background-color: #808080");
        userManageButton.setStyle(null);
        dailyManageButton.setStyle(null);
        workDetailManageButton.setStyle(null);
        rightPane.getChildren().clear();
        loadWorkTableView();
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
    
    @SuppressWarnings("static-access")
    private void loadWorkTableView() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ResourceLoader.getFxmlResource("WorkTableView.fxml"));

        try {
            workVbox = fxmlLoader.load();
            rightPane.setLeftAnchor(workVbox, 0.0);
            rightPane.setRightAnchor(workVbox, 0.0);
            rightPane.setBottomAnchor(workVbox, 0.0);
            rightPane.setTopAnchor(workVbox, 0.0);
            rightPane.getChildren().add(workVbox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("static-access")
    private void loadWorkDetailTableView() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ResourceLoader.getFxmlResource("WorkDetailTableView.fxml"));

        try {
            workDetailVbox = fxmlLoader.load();
            rightPane.setLeftAnchor(workDetailVbox, 0.0);
            rightPane.setRightAnchor(workDetailVbox, 0.0);
            rightPane.setBottomAnchor(workDetailVbox, 0.0);
            rightPane.setTopAnchor(workDetailVbox, 0.0);
            rightPane.getChildren().add(workDetailVbox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
