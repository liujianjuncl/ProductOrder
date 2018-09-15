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
 * Created by wzj on 2017/1/4.
 */
public class MainUIController implements Initializable {
    
    @FXML
    private SplitPane splitPane;

    /**
     * 数字框
     */
    @FXML
    private TextField numTextField;

    /**名字框*/
    @FXML
    private TextField nameTextField;

    @FXML
    private WebView webview;

    @FXML
    private TextField urlTextField;

    /** 容器*/
    @FXML
    private TabPane tabPane;
    
    @FXML
    private AnchorPane rightPane;
    
    @FXML
    private TableView userTableView;

    /**
     * web engine
     */
    WebEngine webEngine;
    
    @FXML
    Button userManageButton;
    
    @FXML
    Button dailyManageButton;

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
        userManageButton.setStyle("-fx-background-color: #808080");
        loadTableViewTestTab();
        
        
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
        System.out.println("=========userLabelClickAction=========");
        userManageButton.setStyle("-fx-background-color: #808080");
        dailyManageButton.setStyle(null);
        loadTableViewTestTab();
    }
    
    @FXML
    private void dailyLabelClickAction() {
        System.out.println("=========dailyLabelClickAction=========");
        dailyManageButton.setStyle("-fx-background-color: #808080");
        userManageButton.setStyle(null);
    }


    private void loadTableViewTestTab() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ResourceLoader.getFxmlResource("TableViewTest.fxml"));

        try {
            
            VBox vbox = fxmlLoader.load();
            rightPane.setLeftAnchor(vbox, 0.0);
            rightPane.setRightAnchor(vbox, 0.0);
            rightPane.setBottomAnchor(vbox, 0.0);
            rightPane.setTopAnchor(vbox, 0.0);
            rightPane.getChildren().add(vbox);
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 回车事件
     */
    @FXML
    private void urlTextFieldAction() {
        goBtClickAction();
    }

    /**
     * Go按钮点击事件
     */
    @FXML
    private void goBtClickAction() {
        webEngine.load(urlTextField.getText());
    }
}
