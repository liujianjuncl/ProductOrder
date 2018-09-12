package com.nii.desktop.widget.dialog;

import javafx.stage.Stage;

/**
 * Created by wzj on 2017/1/2.
 */
public abstract class ModalDialog<R> {
    /**
     * stage
     */
    protected Stage dialogStage;

    /**
     * is ok
     */
    protected boolean isOkClicked = false;

    /**
     * ���캯��
     */
    public ModalDialog() {
        isOkClicked = false;
    }

    /**
     * ����stage
     * 
     * @param stage stage
     */
    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    /**
     * is ok click
     * 
     * @return true ����� | false û�е��
     */
    public boolean isOkClicked() {
        return isOkClicked;
    }

    /**
     * set param
     * 
     * @param param
     */
    abstract public void setParam(R param);
}
