package com.nii.desktop.util.ui;

import com.nii.desktop.decorate.StageDecorate;
import com.nii.desktop.type.CommonConstant;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by ljj on 2018/9/16.
 */
public final class UIManager {

    /* ��־ */
    private final static Logger LOGGER = LoggerFactory.getLogger(UIManager.class);

    /* title�ĸ��߶� */
    private final static int MAIN_TITLE_HEIGHT = 70;

    /* ϵͳstage */
    private static Stage primaryStage;

    /* ˽�й��캯�� */
    private UIManager() {

    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    /*�л�����¼����*/
    public static void switchLoginUI() {
        primaryStage.setTitle(CommonConstant.LOGIN_WINDOW_TITLE);

        // ���ر�����
//      primaryStage.initStyle(StageStyle.TRANSPARENT);

        Pane pane = null;
        try {
            pane = (Pane) FXMLLoader.load(ResourceLoader.getFxmlResource("LoginUI.fxml"));
        } catch (IOException e) {
            LOGGER.error("Switch login UI failed", e);
        }
        System.out.println(pane);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    /**
     * �л���������ҳ��
     */
    public static void switchMainUI() {
        primaryStage.setTitle(CommonConstant.LOGIN_WINDOW_TITLE);
        AnchorPane pane = null;
        try {
            pane = FXMLLoader.load(ResourceLoader.getFxmlResource("Container.fxml"));
        } catch (IOException e) {
            LOGGER.error("Switch main UI failed", e);
        }

        StageDecorate.decorate(primaryStage, pane, MAIN_TITLE_HEIGHT);

        setStageOnCenter();
        primaryStage.show();
    }

    private static void setStageOnCenter() {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }
}
