package com.nii.desktop.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.nii.desktop.model.User;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * Created by wzj on 2018/1/7.
 */
public class TableViewTestController implements Initializable {

	/**
	 * 表格
	 */
	@FXML
	private TableView<User> userTableView;

	/**
	 * 数据
	 */
	ObservableList<User> data = FXCollections.observableArrayList();

	@FXML
	Label userLabel;

	/**
	 * name列
	 */
	@FXML
	TableColumn<User, String> userNoCol;

	/**
	 * name列
	 */
	@FXML
	TableColumn<User, String> userNameCol;

	/**
	 * 是否计件
	 */
	@FXML
	TableColumn<User, String> isPieceworkCol;

	/**
	 * 是否管理员
	 */
	@FXML
	TableColumn<User, String> isManagerCol;

	/**
	 * 是否禁用
	 */
	@FXML
	TableColumn<User, String> isDisableCol;

	/**
	 * 操作列
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
//        
		userNoCol.setCellValueFactory(new PropertyValueFactory<User, String>("userNo"));
		userNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
		isPieceworkCol.setCellValueFactory(new PropertyValueFactory<User, String>("isPiecework"));
		isManagerCol.setCellValueFactory(new PropertyValueFactory<User, String>("isManager"));
		isDisableCol.setCellValueFactory(new PropertyValueFactory<User, String>("isDisable"));

		userTableView.setItems(data);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				initData();
			}
		});

		operationCol.setCellFactory(new Callback() {
			@Override
			public Object call(Object param) {
				TableCell<User, String> cell = new TableCell<User, String>() {
					public HBox paddedButton = new HBox();
					Label delBtn = new Label("删除");
					Label editBtn = new Label("编辑");
					Label pwdBtn = new Label("密码修改");
					{
						paddedButton.getStylesheets().add("/resources/image/TableViewTestCss.css");

						delBtn.getStyleClass().addAll("ob");
						ImageView delImageView = new ImageView(new Image("/resources/image/delete.png"));
						delImageView.setFitWidth(20);
						delImageView.setFitHeight(20);
						delBtn.setGraphic(delImageView);

						editBtn.getStyleClass().addAll("ob");
						ImageView editImageView = new ImageView(new Image("/resources/image/edit.png"));
						editImageView.setFitWidth(20);
						editImageView.setFitHeight(20);
						editBtn.setGraphic(editImageView);

						pwdBtn.getStyleClass().addAll("ob");
						ImageView pwdImageView = new ImageView(new Image("/resources/image/reset_pwd.png"));
						pwdImageView.setFitWidth(20);
						pwdImageView.setFitHeight(20);
						pwdBtn.setGraphic(pwdImageView);

						paddedButton.getChildren().addAll(editBtn, delBtn, pwdBtn);
					}

//					@Override
//					protected void updateItem(String item, boolean empty) {
//						super.updateItem(item, empty);
//						if (!empty) {
//							paddedButton.setPadding(new Insets(3));
//							delBtn.setOnMouseClicked((m) -> {
//								System.out.println("OperButtonCell...del");
//								UserDto userDto = (UserDto) table.getSelectionModel().getSelectedItem();
//								del(userDto.getId());
//							});
//							editBtn.setOnMouseClicked((m) -> {
//								System.out.println("OperButtonCell...edit");
//								UserDto dto = (UserDto) table.getSelectionModel().getSelectedItem();
//								idialog.openDialog("用户编辑", userEditView, 650.0, 450.0);
//								showInfo(dto);
//							});
//							pwdBtn.setOnMouseClicked((m) -> {
//								System.out.println("OperButtonCell...pwdBtn");
//								UserDto dto = (UserDto) table.getSelectionModel().getSelectedItem();
//								idialog.openDialog("密码修改", userPwdView, 650.0, 450.0);
//								idT.setText("" + dto.getId());
//							});
//							setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//							setGraphic(paddedButton);
//						} else {
//							setGraphic(null);
//						}
//					}
				};
				return null;
			}

		});
	}

	/**
	 * 初始化表格数据
	 */
	private void initData() {
		data.add(new User("123456", "张三", "是", "是", "是"));
		data.add(new User("123456", "张三", "是", "是", "是"));
		data.add(new User("123456", "张三", "是", "是", "是"));

	}
}
