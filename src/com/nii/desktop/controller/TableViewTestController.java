package com.nii.desktop.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;

import java.net.URL;
import java.util.ResourceBundle;

import com.nii.desktop.model.Student;
import com.nii.desktop.model.User;

/**
 * Created by wzj on 2018/1/7.
 */
public class TableViewTestController implements Initializable {

    /**
     * ���
     */
    @FXML
    private TableView<User> userTableView;

    /**
     * ����
     */
    ObservableList<User> data = FXCollections.observableArrayList();

    @FXML
    Label userLabel;

    /**
     * name��
     */
    @FXML
    TableColumn<User, String> userNoCol;

    /**
     * name��
     */
    @FXML
    TableColumn<User, String> userNameCol;

    /**
     * �Ƿ�Ƽ�
     */
    @FXML
    TableColumn<User, String> isPieceworkCol;

    /**
     * �Ƿ����Ա
     */
    @FXML
    TableColumn<User, String> isManagerCol;

    /**
     * �Ƿ����
     */
    @FXML
    TableColumn<User, String> isDisableCol;

    /**
     * ������
     */
    @FXML
    TableColumn operationCol;

    /**
     * Called to initialize a controller after its root element has been completely
     * processed.
     *
     * @param location  The location used to resolve relative paths for the root
     *                  object, or <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or
     *                  <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        userNoCol.setText("000012312");
//        userNameCol.setText("asldkas");
//        isPieceworkCol.setText("��");
//        isManagerCol.setText("��");
//        isDisableCol.setText("��");
//        operation.setText("����");
        userNoCol.setCellValueFactory(new PropertyValueFactory<User, String>("�û����"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("�û�����"));
        isPieceworkCol.setCellValueFactory(new PropertyValueFactory<User, String>("�Ƿ�Ƽ�"));
        isManagerCol.setCellValueFactory(new PropertyValueFactory<User, String>("�Ƿ����Ա"));
        isDisableCol.setCellValueFactory(new PropertyValueFactory<User, String>("�Ƿ����"));
        
      

    }
    
  //������ť��  
    operationCol.setCellFactory((col) -> {  
        TableCell<User, String> cell = new TableCell<User, String>() {  
            public HBox paddedButton = new HBox();  
            Label delBtn = new Label("ɾ��");  
            Label editBtn = new Label("�༭");  
            Label pwdBtn = new Label("�����޸�");  
      
            {  
                paddedButton.getStylesheets().add("/com/redphase/ui/user/user.css");  
      
                delBtn.getStyleClass().addAll("ob");  
                ImageView delImageView = new ImageView(new Image("/com/redphase/ui/iconfont/del.png"));  
                delImageView.setFitWidth(20);  
                delImageView.setFitHeight(20);  
                delBtn.setGraphic(delImageView);  
      
                editBtn.getStyleClass().addAll("ob");  
                ImageView editImageView = new ImageView(new Image("/com/redphase/ui/iconfont/edit.png"));  
                editImageView.setFitWidth(20);  
                editImageView.setFitHeight(20);  
                editBtn.setGraphic(editImageView);  
      
                pwdBtn.getStyleClass().addAll("ob");  
                ImageView pwdImageView = new ImageView(new Image("/com/redphase/ui/iconfont/barrage_fill.png"));  
                pwdImageView.setFitWidth(20);  
                pwdImageView.setFitHeight(20);  
                pwdBtn.setGraphic(pwdImageView);  
      
                paddedButton.getChildren().addAll(editBtn, delBtn, pwdBtn);  
            }  
        @Override  
        protected void updateItem(String item, boolean empty) {  
            super.updateItem(item, empty);  
            if (!empty) {  
                paddedButton.setPadding(new Insets(3));  
                delBtn.setOnMouseClicked((m) -> {  
                    log.debug("OperButtonCell...del");  
                    UserDto userDto = (UserDto) table.getSelectionModel().getSelectedItem();  
                    del(userDto.getId());  
                });  
                editBtn.setOnMouseClicked((m) -> {  
                    log.debug("OperButtonCell...edit");  
                    UserDto dto = (UserDto) table.getSelectionModel().getSelectedItem();  
                    idialog.openDialog("�û��༭", userEditView, 650.0, 450.0);  
                    showInfo(dto);  
                });  
                pwdBtn.setOnMouseClicked((m) -> {  
                    log.debug("OperButtonCell...pwdBtn");  
                    UserDto dto = (UserDto) table.getSelectionModel().getSelectedItem();  
                    idialog.openDialog("�����޸�", userPwdView, 650.0, 450.0);  
                    idT.setText("" + dto.getId());  
                });  
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);  
                setGraphic(paddedButton);  
            } else {  
                setGraphic(null);  
            }  
          
        };  
        return cell;  
    }); 

    userTableView.setItems(data);

    Platform.runLater(new Runnable() {
        @Override
        public void run() {
            initData();
        }
    });

    /**
     * ��ʼ���������
     */
    private void initData() {
//        data.add(new Student("����", 10, "��ѧ��"));
//        data.add(new Student("����", 13, "ѧϰ�ǳ�Ŭ��"));
//        data.add(new Student("����", 16, "��ѧ��"));
//        System.out.println(userLabel.getHeight() + "----" + userLabel.getWidth());
    }
}
