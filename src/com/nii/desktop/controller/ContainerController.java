package com.nii.desktop.controller;

import com.nii.desktop.util.ui.ResourceLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by wzj on 2018/2/7.
 */
public class ContainerController implements Initializable {
    
    @FXML
    private AnchorPane rootPane;
    /**
     * container pane
     */
    @FXML
    private StackPane containerPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SplitPane pane = null;
        try {
            pane = FXMLLoader.load(ResourceLoader.getFxmlResource("MainUI.fxml"));
        } catch (IOException e) {
            Logger.getLogger(ContainerController.class.getName()).log(Level.SEVERE, null, e);
        }

        containerPane.getChildren().addAll(pane);
    }
}
