package com.nii.desktop.controller;

import com.nii.desktop.decorate.StageMove;
import com.nii.desktop.widget.menu.GlobalMenu;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by wzj on 2017/8/20.
 */
public class TitleController {
    /* ���˫������ */
    private final static int DOUBLE_CLICK = 2;

    /* stage */
    private Stage stage;

    /** ���� */
    @FXML
    private VBox rootPane;

    /* title */
    @FXML
    public HBox banner;

    /* �˵� */
    @FXML
    public Button menuButton;

    public void setStage(Stage stage, int height) {
        this.stage = stage;
        bannerClickAction();
        banner.setPrefHeight(height);
        new StageMove(this.stage).bindDrag(banner);
    }

    public void addContent(Parent content) {
        this.rootPane.getChildren().add(content);
        VBox.setVgrow(content, Priority.ALWAYS);

    }

    private void bannerClickAction() {
        banner.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == DOUBLE_CLICK) {
                        maxButtonClickAction();
                    }
                }
            }
        });
    }

    /* ���ڹر��¼� */
    @FXML
    public void closeButtonCilciAction() {
        stage.close();
    }

    /* ��������¼� */
    @FXML
    public void maxButtonClickAction() {
        stage.setMaximized(!stage.isMaximized());
    }

    /* ������С���¼� */
    @FXML
    public void minButtonClickAction() {
        stage.setIconified(true);
    }

    /* �˵�����¼� */
    @FXML
    public void menuButtonClickAction() {
        GlobalMenu.getInstance().show(menuButton, Side.BOTTOM, 0, 0);
    }
}
