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
     * ���ֿ�
     */
    @FXML
    private TextField numTextField;

    /**���ֿ�*/
    @FXML
    private TextField nameTextField;

    @FXML
    private WebView webview;

    @FXML
    private TextField urlTextField;

    /** ����*/
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
        userManageButton.setStyle("-fx-background-color: red");
        dailyManageButton.setStyle(null);
        loadTableViewTestTab();
    }
    
    @FXML
    private void dailyLabelClickAction() {
        System.out.println("=========dailyLabelClickAction=========");
        dailyManageButton.setStyle("-fx-background-color: red");
        userManageButton.setStyle(null);
    }

    private void loadListViewTestTab() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ResourceLoader.getFxmlResource("ListViewTest.fxml"));
        
        try {
            Pane pane = fxmlLoader.load();

            Tab listViewTab = new Tab("ListView");
            listViewTab.setContent(pane);
            tabPane.getTabs().add(listViewTab);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
            
//            Tab tableViewTab = new Tab("TableView");
//            tableViewTab.setContent(pane);
//            tabPane.getTabs().add(tableViewTab);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * �س��¼�
     */
    @FXML
    private void urlTextFieldAction() {
        goBtClickAction();
    }

    /**
     * Go��ť����¼�
     */
    @FXML
    private void goBtClickAction() {
        webEngine.load(urlTextField.getText());
    }
}
