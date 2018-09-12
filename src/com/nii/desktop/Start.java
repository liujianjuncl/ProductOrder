package com.nii.desktop;

import com.nii.desktop.util.ui.UIManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * desktop �������� Created by wzj on 2016/12/18.
 */
public class Start extends Application {
	/**
	 * �������� 
	 */
	public void start(Stage primaryStage) throws Exception {
		UIManager.setPrimaryStage(primaryStage);

//		UIManager.switchMainUI();

        UIManager.switchLoginUI();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
