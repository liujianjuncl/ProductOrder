package com.nii.desktop.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.nii.desktop.util.ui.ResourceLoader;
import com.nii.desktop.util.ui.UIManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by ljj on 2018/9/27.
 */
public class DailyManageController implements Initializable {
    
    /* 系统stage */
    private static Stage dialogStage;

    public static Stage getdialogStage() {
        return dialogStage;
    }

    public static void setPrimaryStage(Stage stage) {
        dialogStage = stage;
    }
    
    @FXML
    private void addDailyAction() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ResourceLoader.getFxmlResource("AddDaily.fxml"));

        AnchorPane addDailyPane;

        try {
            addDailyPane = fxmlLoader.load();

            dialogStage = new Stage();
            dialogStage.setTitle("新建日报");

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(UIManager.getPrimaryStage());
            Scene scene = new Scene(addDailyPane);
            dialogStage.setScene(scene);

            dialogStage.setResizable(false);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void modifyDailyAction() {
        
    }
    
    @FXML
    private void deleteDailyAction() {
        
    }
    
    @FXML
    private void queryDailyAction() {
        
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
    }

}
