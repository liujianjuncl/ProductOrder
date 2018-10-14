package com.nii.desktop;

import com.nii.desktop.util.ui.UIManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * desktop �������� Created by ljj on 2018/9/13.
 */
public class Start extends Application {
    
    /* �������� */
    public void start(Stage primaryStage) throws Exception {
        UIManager.setPrimaryStage(primaryStage);

//        UIManager.switchMainUI();

        UIManager.switchLoginUI();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
