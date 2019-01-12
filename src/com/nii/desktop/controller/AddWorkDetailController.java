package com.nii.desktop.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.nii.desktop.model.User;
import com.nii.desktop.model.Work;
import com.nii.desktop.util.conf.DBUtil;
import com.nii.desktop.util.conf.SessionUtil;
import com.nii.desktop.util.conf.Encoder;
import com.nii.desktop.util.conf.PropsUtil;
import com.nii.desktop.util.conf.UserUtil;
import com.nii.desktop.util.conf.WorkUtil;
import com.nii.desktop.util.ui.AlertUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

public class AddWorkDetailController implements Initializable {

	@FXML
	private AnchorPane addWorkDetailPane;

	@FXML
	private TextField workNoField;

	@FXML
	private TextField workNameField;

	@FXML
	private ComboBox unitCbox;

	@FXML
	private TextField unitPriceField;

	@FXML
	private TextField workNumField;
	
	@FXML
	private TextArea remarkField;

	private Work work;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 根据作业编号获取作业信息
		workNoField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					work = WorkUtil.getWorkByNo(workNoField.getText().trim());
					if (work == null) {
						AlertUtil.alertInfoLater(PropsUtil.getMessage("workNo.isNotExist"));
						return;
					} else {
						workNameField.setText(work.getWorkName());
						unitCbox.setValue(work.getUnit());
						unitPriceField.setText(Double.toString(work.getUnitPrice()));
					}
				}
			}
		});

		/* 当作业编号失去焦点时，重新查询作业信息 */
		workNoField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
				if (oldValue) {
					work = WorkUtil.getWorkByNo(workNoField.getText().trim());
					if (work == null) {
						AlertUtil.alertInfoLater(PropsUtil.getMessage("workNo.isNotExist"));
						workNameField.setText("");
						unitCbox.setValue(null);
						unitPriceField.setText("");
						workNumField.setText("");
						return;
					} else {
						workNameField.setText(work.getWorkName());
						unitCbox.setValue(work.getUnit());
						unitPriceField.setText(Double.toString(work.getUnitPrice()));
					}
				}
			}
		});

		// 实际作业数量只允许输入数字
		workNumField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Pattern pattern = Pattern.compile("[0-9]*");
				if (!pattern.matcher(newValue).matches()) {
					workNumField.setEditable(false);
					workNumField.setText(oldValue);
					workNumField.setEditable(true);
				}
			}
		});
	}

	@FXML
	public void confirmBtnAction() {
		
		User loginUser = SessionUtil.USERS.get("loginUser");

		String workNum = workNumField.getText().trim();
		String remark = remarkField.getText().trim();

		Connection conn = null;
		PreparedStatement stmt = null;
		String workDetailNo = null;

		try {
			String sql = "insert into dbo.t_product_daily_work_detail  " + "  values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			workDetailNo = WorkUtil.getWorkDetailNo();
			
			if(work == null) {
				AlertUtil.alertInfoLater(PropsUtil.getMessage("workNo.isNotExist"));
				return;
			}
			
			if("".equals(workNum)) {
				AlertUtil.alertInfoLater(PropsUtil.getMessage("workNum.isNotExist"));
				return;
			}

			stmt.setString(1, workDetailNo);
			stmt.setTimestamp(2, new Timestamp(new Date().getTime()));
			stmt.setInt(3, 0);
			stmt.setString(4, work.getWorkNo());
			stmt.setString(5, work.getWorkName());
			stmt.setString(6, work.getUnit());
			stmt.setDouble(7, work.getUnitPrice());
			stmt.setInt(8, Integer.parseInt(workNum));
			stmt.setDouble(9, work.getUnitPrice() * Integer.parseInt(workNum));
			stmt.setString(10, loginUser.getUserNo());
			stmt.setTimestamp(11, new Timestamp(new Date().getTime()));
			stmt.setString(12, null);
			stmt.setTimestamp(13, null);
			stmt.setString(14, null);
			stmt.setTimestamp(15, null);
			stmt.setInt(16, 0);
			stmt.setString(17, loginUser.getUserName());
			stmt.setString(18, null);
			stmt.setString(19, null);
			stmt.setString(20, remark);

			stmt.executeUpdate();

		} catch (Exception e) {
			Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
			return;
		} finally {
			DBUtil.release(conn, stmt);
		}

		AlertUtil.alertInfoLater(PropsUtil.getMessage("workDetail.add.success") + workDetailNo);
		WorkTableDetailViewController.getdialogStage().close();
		// 新建完成刷新数据
		((WorkTableDetailViewController) SessionUtil.CONTROLLERS.get("WorkTableDetailViewController")).refresh();
	}

	@FXML
	public void cancelBtnAction() {
		WorkTableDetailViewController.getdialogStage().close();
	}

}
