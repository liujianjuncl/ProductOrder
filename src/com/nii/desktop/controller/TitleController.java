package com.nii.desktop.controller;

import com.nii.desktop.decorate.StageMove;
import com.nii.desktop.util.conf.SessionUtil;
import com.nii.desktop.util.ui.UIManager;
import com.nii.desktop.widget.menu.GlobalMenu;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
        UIManager.getPrimaryStage().close();
        UIManager.switchLoginUI();
        SessionUtil.USERS.clear();
        SessionUtil.CONTROLLERS.clear();
        SessionUtil.DAILYS.clear();
    }
}
