package com.nii.desktop.widget.menu;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 * Created by wzj on 2017/8/22.
 */
public class GlobalMenu extends ContextMenu {
    /**
     * ����
     */
    private static GlobalMenu INSTANCE = null;

    /**
     * ˽�й��캯��
     */
    private GlobalMenu() {
        MenuItem settingMenuItem = new MenuItem("����");
        MenuItem updateMenuItem = new MenuItem("������");
        MenuItem feedbackMenuItem = new MenuItem("�ٷ���̳");
        MenuItem aboutMenuItem = new MenuItem("�����뽨��");
        MenuItem companyMenuItem = new MenuItem("����");

        getItems().add(settingMenuItem);
        getItems().add(updateMenuItem);
        getItems().add(companyMenuItem);
        getItems().add(feedbackMenuItem);
        getItems().add(aboutMenuItem);
    }

    /**
     * ��ȡʵ��
     * 
     * @return GlobalMenu
     */
    public static GlobalMenu getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GlobalMenu();
        }

        return INSTANCE;
    }

}
