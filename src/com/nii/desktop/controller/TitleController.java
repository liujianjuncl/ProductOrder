package com.nii.desktop.controller;

import com.nii.desktop.decorate.StageMove;
import com.nii.desktop.util.conf.SessionUtil;
import com.nii.desktop.util.ui.UIManager;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by wzj on 2017/8/20.
 */
public class TitleController {

    /* stage */
    private Stage stage;

    /** ΩÁ√Ê */
    @FXML
    private VBox rootPane;

    /* title */
    @FXML
    public GridPane banner;

    public void setStage(Stage stage, int height) {
        this.stage = stage;
        banner.setPrefHeight(height);
        VBox.setVgrow(banner, Priority.ALWAYS);
        new StageMove(this.stage).bindDrag(banner);
    }

    public void addContent(Parent content) {
        this.rootPane.getChildren().add(content);
        VBox.setVgrow(content, Priority.ALWAYS);
    }

    @FXML
    public void onActionExitLink() {
    	SessionUtil.USERS.clear();
        SessionUtil.CONTROLLERS.clear();
        SessionUtil.DAILYS.clear();
        SessionUtil.WORKS.clear();
        SessionUtil.WORKDETAILS.clear();
        SessionUtil.PARAMS.clear();
        SessionUtil.STAGES.clear();
        UIManager.getPrimaryStage().close();
        UIManager.switchLoginUI();
    }
}
