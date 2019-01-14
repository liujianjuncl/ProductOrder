package com.nii.desktop.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nii.desktop.model.Daily;
import com.nii.desktop.model.DailyProcessQty;
import com.nii.desktop.model.User;
import com.nii.desktop.util.conf.DBUtil;
import com.nii.desktop.util.conf.DailyUtil;
import com.nii.desktop.util.conf.DateUtil;
import com.nii.desktop.util.conf.SessionUtil;
import com.nii.desktop.util.conf.WorkUtil;
import com.nii.desktop.util.conf.PropsUtil;
import com.nii.desktop.util.ui.AlertUtil;
import com.nii.desktop.util.ui.ResourceLoader;
import com.nii.desktop.util.ui.UIManager;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Created by ljj on 2018/9/7.
 */
public class DailyTableViewController implements Initializable {

	@FXML
	private VBox dailyVBox;

	/* 表格 */
	@FXML
	private TableView<Daily> dailyTableView;

	/* 分页 */
	@FXML
	private Pagination dailyTablePagination;

	/* 数据 */
	private ObservableList<Daily> dailyDataList = FXCollections.observableArrayList();

	@FXML
	private Label dailyLabel;

	@FXML
	private HBox dailyHbox;

	/* selectCol */
	@FXML
	private TableColumn<Daily, CheckBox> selectCol;

	/* 日报单号 */
	@FXML
	private TableColumn<Daily, String> dailyNoCol;

	/* 生产任务单 */
	@FXML
	private TableColumn<Daily, String> billNoCol;

	/* 物料代码 */
	@FXML
	private TableColumn<Daily, String> materialCodeCol;

	/* 物料名称 */
	@FXML
	private TableColumn<Daily, String> materialNameCol;

	/* 规格型号 */
	@FXML
	private TableColumn<Daily, String> modelCol;

	/* 计划生产数量 */
	@FXML
	private TableColumn<Daily, String> planQuantityCol;

	/* 生产日期 */
	@FXML
	private TableColumn<Daily, String> productDateCol;

	/* 改制工序1 */
	@FXML
	private TableColumn<Daily, String> resPro1Col;

	/* 改制工序1实作数量 */
	@FXML
	private TableColumn<Daily, String> resProQty1Col;

	/* 改制工序2 */
	@FXML
	private TableColumn<Daily, String> resPro2Col;

	/* 改制工序2实作数量 */
	@FXML
	private TableColumn<Daily, String> resProQty2Col;

	/* 改制工序3 */
	@FXML
	private TableColumn<Daily, String> resPro3Col;

	/* 改制工序3实作数量 */
	@FXML
	private TableColumn<Daily, String> resProQty3Col;

	/* 工序1 */
	@FXML
	private TableColumn<Daily, String> pro1Col;

	/* 工序1实作数量 */
	@FXML
	private TableColumn<Daily, String> proQty1Col;

	/* 工序2 */
	@FXML
	private TableColumn<Daily, String> pro2Col;

	/* 工序2实作数量 */
	@FXML
	private TableColumn<Daily, String> proQty2Col;

	/* 工序3 */
	@FXML
	private TableColumn<Daily, String> pro3Col;

	/* 工序3实作数量 */
	@FXML
	private TableColumn<Daily, String> proQty3Col;

	/* 工序4 */
	@FXML
	private TableColumn<Daily, String> pro4Col;

	/* 工序4实作数量 */
	@FXML
	private TableColumn<Daily, String> proQty4Col;

	/* 工序5 */
	@FXML
	private TableColumn<Daily, String> pro5Col;

	/* 工序5实作数量 */
	@FXML
	private TableColumn<Daily, String> proQty5Col;

	/* 工序6 */
	@FXML
	private TableColumn<Daily, String> pro6Col;

	/* 工序6实作数量 */
	@FXML
	private TableColumn<Daily, String> proQty6Col;

	/* 创建人员 */
	@FXML
	private TableColumn<Daily, String> createUserCol;

	/* 创建时间 */
	@FXML
	private TableColumn<Daily, String> createTimeCol;

	/* 修改人员 */
	@FXML
	private TableColumn<Daily, String> modifyUserCol;

	/* 修改时间 */
	@FXML
	private TableColumn<Daily, String> modifyTimeCol;

	@FXML
	private Button addDailyBtn;

	@FXML
	private Button delDailyBtn;

	@FXML
	private TextField userNoTextField;

	@FXML
	private TextField billNoTextField;

	@FXML
	private DatePicker startDatePicker;

	@FXML
	private DatePicker endDatePicker;

	@FXML
	private Label dailyMoney;
	
	private User loginUser = SessionUtil.USERS.get("loginUser");

	/* 系统stage */
	private static Stage dialogStage;

	public static Stage getdialogStage() {
		return dialogStage;
	}

	public static void setPrimaryStage(Stage stage) {
		dialogStage = stage;
	}

	/** 初始化 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addDatatoTableView();
		if (SessionUtil.CONTROLLERS.get("DailyTableViewController") == null) {
			SessionUtil.CONTROLLERS.put("DailyTableViewController", this);
		}

		dailyMoney.setText("金额：0.0");

		// 双击某一行时，编辑该行
		dailyTableView.setRowFactory(new Callback<TableView<Daily>, TableRow<Daily>>() {
			@Override
			public TableRow<Daily> call(TableView<Daily> param) {
				TableRow<Daily> row = new TableRow<Daily>();
				row.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						// TODO Auto-generated method stub
						if (event.getClickCount() == 2) {
							Daily d = row.getItem();
							Daily daily = DailyUtil.getDailyByNoAll(d.getDailyNo());
							modifyDailyAction(daily);
						}
					}
				});
				return row;
			}
		});

		// 分页
//        dailyTablePagination.setPageCount(1);
	}

	/* 初始化表格数据 */
	private void initData() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		// 添加表格数据前先清空
		dailyDataList.clear();

		try {
			if (!"是".equals(loginUser.getIsManager())) {
				String sql = "select * from dbo.t_product_daily_bill_detail where isDelete = 0 and createUser = '"
						+ loginUser.getUserNo() + "'";
				// 非管理员用户默认显示当天日报
				sql = sql + " and CONVERT(datetime, productDate, 23) = '" + DateUtil.SDF.format(new Date()) + "'";

//            // 获取最近3个月的记录
//            sql = sql + " and productDate <= '" + DateUtil.localDateToDateTimeStr(LocalDate.now()) + "'";
//            sql = sql + " and productDate >= '" + DateUtil.last3MonthDateTimeStr() + "'";
//            sql = sql + " order by dailyNo desc";

				conn = DBUtil.getConnection();
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();

				double money = 0.0;
				while (rs.next()) {
					dailyDataList.add(new Daily(new CheckBox(), rs.getString("dailyNo"), rs.getString("billNo"),
							rs.getString("materialCode"), rs.getString("materialName"), rs.getString("model"),
							rs.getInt("planQuantity"), DateUtil.sqlDateToLocalDate(rs.getDate("productDate")),
							rs.getString("resProcess1"), rs.getInt("resProcessQty1"), rs.getString("resProcess2"),
							rs.getInt("resProcessQty2"), rs.getString("resProcess3"), rs.getInt("resProcessQty3"),
							rs.getString("process1"), rs.getInt("processQty1"), rs.getString("process2"),
							rs.getInt("processQty2"), rs.getString("process3"), rs.getInt("processQty3"),
							rs.getString("process4"), rs.getInt("processQty4"), rs.getString("process5"),
							rs.getInt("processQty5"), rs.getString("process6"), rs.getInt("processQty6"),
							rs.getString("createUser"), rs.getTimestamp("createTime"),
							rs.getString("modifyUser"), rs.getTimestamp("modifyTime"),
							rs.getInt("isPiecework"), rs.getInt("isDelete"), rs.getInt("sequence"),
							rs.getString("createUserName"), rs.getString("modifyUserName")));
					// 计算查询出所有工序的金额
					money = money + rs.getInt("resProcessQty1") * rs.getDouble("resProcessPrice1")
							+ rs.getInt("resProcessQty2") * rs.getDouble("resProcessPrice2")
							+ rs.getInt("resProcessQty3") * rs.getDouble("resProcessPrice3")
							+ rs.getInt("processQty1") * rs.getDouble("processPrice1")
							+ rs.getInt("processQty2") * rs.getDouble("processPrice2")
							+ rs.getInt("processQty3") * rs.getDouble("processPrice3")
							+ rs.getInt("processQty4") * rs.getDouble("processPrice4")
							+ rs.getInt("processQty5") * rs.getDouble("processPrice5")
							+ rs.getInt("processQty6") * rs.getDouble("processPrice6");
				}
				DecimalFormat df = new DecimalFormat("#.0000");
				dailyMoney.setText("金额：" + df.format(money));
			}
		} catch (Exception e) {
			Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
		} finally {
			DBUtil.release(conn, stmt, rs);
		}

	}

	public void addDatatoTableView() {
		selectCol.setCellValueFactory(new PropertyValueFactory<Daily, CheckBox>("checkbox"));
		dailyNoCol.setCellValueFactory(new PropertyValueFactory<Daily, String>("dailyNo"));
		billNoCol.setCellValueFactory(new PropertyValueFactory<Daily, String>("billNo"));
		materialCodeCol.setCellValueFactory(new PropertyValueFactory<Daily, String>("materialCode"));
		materialNameCol.setCellValueFactory(new PropertyValueFactory<Daily, String>("materialName"));
		modelCol.setCellValueFactory(new PropertyValueFactory<Daily, String>("model"));
		planQuantityCol.setCellValueFactory(new PropertyValueFactory<Daily, String>("planQty"));
		productDateCol.setCellValueFactory(new PropertyValueFactory<Daily, String>("proDate"));
		resPro1Col.setCellValueFactory(new PropertyValueFactory<Daily, String>("resPro1"));
		resProQty1Col.setCellValueFactory(new PropertyValueFactory<Daily, String>("resProQty1"));
		resPro2Col.setCellValueFactory(new PropertyValueFactory<Daily, String>("resPro2"));
		resProQty2Col.setCellValueFactory(new PropertyValueFactory<Daily, String>("resProQty2"));
		resPro3Col.setCellValueFactory(new PropertyValueFactory<Daily, String>("resPro3"));
		resProQty3Col.setCellValueFactory(new PropertyValueFactory<Daily, String>("resProQty3"));
		pro1Col.setCellValueFactory(new PropertyValueFactory<Daily, String>("pro1"));
		proQty1Col.setCellValueFactory(new PropertyValueFactory<Daily, String>("proQty1"));
		pro2Col.setCellValueFactory(new PropertyValueFactory<Daily, String>("pro2"));
		proQty2Col.setCellValueFactory(new PropertyValueFactory<Daily, String>("proQty2"));
		pro3Col.setCellValueFactory(new PropertyValueFactory<Daily, String>("pro3"));
		proQty3Col.setCellValueFactory(new PropertyValueFactory<Daily, String>("proQty3"));
		pro4Col.setCellValueFactory(new PropertyValueFactory<Daily, String>("pro4"));
		proQty4Col.setCellValueFactory(new PropertyValueFactory<Daily, String>("proQty4"));
		pro5Col.setCellValueFactory(new PropertyValueFactory<Daily, String>("pro5"));
		proQty5Col.setCellValueFactory(new PropertyValueFactory<Daily, String>("proQty5"));
		pro6Col.setCellValueFactory(new PropertyValueFactory<Daily, String>("pro6"));
		proQty6Col.setCellValueFactory(new PropertyValueFactory<Daily, String>("proQty6"));
		createUserCol.setCellValueFactory(new PropertyValueFactory<Daily, String>("createUserName"));
		createTimeCol.setCellValueFactory(new PropertyValueFactory<Daily, String>("createTime"));
		modifyUserCol.setCellValueFactory(new PropertyValueFactory<Daily, String>("modifyUserName"));
		modifyTimeCol.setCellValueFactory(new PropertyValueFactory<Daily, String>("modifyTime"));

		dailyTableView.setItems(dailyDataList);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				initData();
			}
		});
	}

	@FXML
	public void addDailyAction() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(ResourceLoader.getFxmlResource("AddDaily.fxml"));

		AnchorPane addDailyPane;

		try {
			addDailyPane = fxmlLoader.load();

			dialogStage = new Stage();
			dialogStage.setTitle("新建日报");

			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(UIManager.getPrimaryStage());
			Scene scene = new Scene(addDailyPane);
			dialogStage.setScene(scene);

			dialogStage.setResizable(false);
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* 修改日报 */
	public void modifyDailyAction(Daily daily) {
		SessionUtil.DAILYS.put("editDaily", daily);

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(ResourceLoader.getFxmlResource("ModifyDaily.fxml"));

		AnchorPane modifyDailyPane;

		try {
			modifyDailyPane = fxmlLoader.load();

			dialogStage = new Stage();
			dialogStage.setTitle("编辑日报");

			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(UIManager.getPrimaryStage());
			Scene scene = new Scene(modifyDailyPane);
			dialogStage.setScene(scene);

			dialogStage.setResizable(false);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/* 删除日报 */
	@FXML
	public void deleteDailyAction() {
		if (getSelectedNum() == 0) {
			AlertUtil.alertInfoLater(PropsUtil.getMessage("comboBox.delete.noSelected"));
		} else if (AlertUtil.alertConfirmLater(PropsUtil.getMessage("confirm.delete"))) {
			List<String> dailyNoList = getSelectedUserNoList();

			Connection conn = null;
			PreparedStatement stmt = null;

			try {
				String sql = "update dbo.t_product_daily_bill_detail set isDelete = 1 where dailyNo = ?";
				conn = DBUtil.getConnection();
				stmt = conn.prepareStatement(sql);

				for (int i = 0; i < dailyNoList.size(); i++) {
					String dailyNo = dailyNoList.get(i);
					stmt.setString(1, dailyNo);

					Daily daily = DailyUtil.getDailyByNoAll(dailyNo);

					// 本月25号
					Date curDate25Day = DateUtil.curMonth25Day();
					// 上月26号
					Date lastDate26Day = DateUtil.lastMonth26Day();

					// 普通员工只允许修改上个月26号到本月25号的生产日报！
					if (!"是".equals(loginUser.getIsManager())
							&& (lastDate26Day.compareTo(DateUtil.localDateToDate(daily.getProDate())) > 0
									|| curDate25Day.compareTo(DateUtil.localDateToDate(daily.getProDate())) < 0)) {
						AlertUtil.alertInfoLater(PropsUtil.getMessage("daily.cannot.delete"));
						return;
					}

					DailyProcessQty dpq = DailyUtil.getProcessTotalQty(daily.getBillNo());

					// 将实作汇总表中本次删除的日报的实作数量减掉
					DailyProcessQty dailyProcessQty = new DailyProcessQty(daily.getBillNo(), daily.getDailyNo(),
							daily.getProDate(), daily.getPlanQty(), dpq.getProQty1() - daily.getResProQty1(),
							dpq.getResProQty2() - daily.getResProQty2(), dpq.getResProQty3() - daily.getResProQty3(),
							dpq.getProQty1() - daily.getProQty1(), dpq.getProQty2() - daily.getProQty2(),
							dpq.getProQty3() - daily.getProQty3(), dpq.getProQty3() - daily.getProQty4(),
							dpq.getProQty5() - daily.getProQty5(), dpq.getProQty6() - daily.getProQty6());
					DailyUtil.updateDailyTotalQty(dailyProcessQty);

					stmt.executeUpdate();
				}
			} catch (Exception e) {
				Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
				return;
			} finally {
				DBUtil.release(conn, stmt);
			}
			AlertUtil.alertInfoLater(PropsUtil.getMessage("daily.delete.success"));
			// 删除完成刷新数据
			refresh();
		}
	}

	@FXML
	public void searchDailyAction() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String userNo = userNoTextField.getText().trim();
		String billNo = billNoTextField.getText().trim();
		LocalDate startDate = startDatePicker.getValue();
		LocalDate endDate = endDatePicker.getValue();

		String last3Month = DateUtil.last3MonthDateTimeStr();

		// 添加表格数据前先清空
		dailyDataList.clear();
		// 金额
		double money = 0.0;

		try {
			String sql = "select * from dbo.t_product_daily_bill_detail where isDelete = 0 ";
			if (!"是".equals(loginUser.getIsManager())) {
				sql = sql + " and createUser = '" + loginUser.getUserNo() + "'";
			}

			if (userNo != null && !"".equals(userNo)) {
				sql = sql + " and createUser = '" + userNo + "'";
			}

			if (billNo != null && !"".equals(billNo)) {
				sql = sql + " and billNo = '" + billNo + "'";
			}

			if (startDate != null) {
				sql = sql + " and productDate >= '" + DateUtil.localDateToDateTimeStr(startDate) + "'";
			} else {
				sql = sql + " and productDate >= '" + last3Month + "'";
			}

			if (endDate != null) {
				sql = sql + " and productDate <= '" + DateUtil.localDateToDateTimeStr(endDate) + "'";
			}
			sql = sql + " order by dailyNo desc";
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			Daily initDaily = new Daily();
			while (rs.next()) {
				Daily daily = initDaily.clone();
				daily.setCheckbox(new CheckBox());
				daily.setDailyNo(rs.getString("dailyNo"));
				daily.setBillNo(rs.getString("billNo"));
				daily.setMaterialCode(rs.getString("materialCode"));
				daily.setMaterialName(rs.getString("materialName"));
				daily.setModel(rs.getString("model"));
				daily.setPlanQty(rs.getInt("planQuantity"));
				daily.setProDate(DateUtil.sqlDateToLocalDate(rs.getDate("productDate")));
				daily.setResPro1(rs.getString("resProcess1"));
				daily.setResProQty1(rs.getInt("resProcessQty1"));
				daily.setResPro2(rs.getString("resProcess2"));
				daily.setResProQty2(rs.getInt("resProcessQty2"));
				daily.setResPro3(rs.getString("resProcess3"));
				daily.setResProQty3(rs.getInt("resProcessQty3"));
				daily.setPro1(rs.getString("process1"));
				daily.setProQty1(rs.getInt("processQty1"));
				daily.setPro2(rs.getString("process2"));
				daily.setProQty2(rs.getInt("processQty2"));
				daily.setPro3(rs.getString("process3"));
				daily.setProQty3(rs.getInt("processQty3"));
				daily.setPro4(rs.getString("process4"));
				daily.setProQty4(rs.getInt("processQty4"));
				daily.setPro5(rs.getString("process5"));
				daily.setProQty5(rs.getInt("processQty5"));
				daily.setPro6(rs.getString("process6"));
				daily.setProQty6(rs.getInt("processQty6"));
				daily.setCreateUserNo(rs.getString("createUser"));
				daily.setCreateTime(rs.getTimestamp("createTime"));
				daily.setModifyUserNo(rs.getString("modifyUser"));
				daily.setModifyTime(rs.getTimestamp("modifyTime"));
				daily.setIsPiecework(rs.getInt("isPiecework"));
				daily.setIsDelete(rs.getInt("isDelete"));
				daily.setSequence(rs.getInt("sequence"));
				daily.setCreateUserName(rs.getString("createUserName"));
				daily.setModifyUserName(rs.getString("modifyUserName"));
//				Daily daily = new Daily(new CheckBox(), rs.getString("dailyNo"), rs.getString("billNo"),
//						rs.getString("materialCode"), rs.getString("materialName"), rs.getString("model"),
//						rs.getInt("planQuantity"), DateUtil.sqlDateToLocalDate(rs.getDate("productDate")),
//						rs.getString("resProcess1"), rs.getInt("resProcessQty1"), rs.getString("resProcess2"),
//						rs.getInt("resProcessQty2"), rs.getString("resProcess3"), rs.getInt("resProcessQty3"),
//						rs.getString("process1"), rs.getInt("processQty1"), rs.getString("process2"),
//						rs.getInt("processQty2"), rs.getString("process3"), rs.getInt("processQty3"),
//						rs.getString("process4"), rs.getInt("processQty4"), rs.getString("process5"),
//						rs.getInt("processQty5"), rs.getString("process6"), rs.getInt("processQty6"),
//						UserUtil.getUser(rs.getString("createUser")).getUserName(), rs.getTimestamp("createTime"),
//						UserUtil.getUser(rs.getString("modifyUser")).getUserName(), rs.getTimestamp("modifyTime"),
//						rs.getInt("isPiecework"), rs.getInt("isDelete"), rs.getInt("sequence"));

				dailyDataList.add(daily);

				// 计算查询出所有工序的金额
				money = money + rs.getInt("resProcessQty1") * rs.getDouble("resProcessPrice1")
						+ rs.getInt("resProcessQty2") * rs.getDouble("resProcessPrice2")
						+ rs.getInt("resProcessQty3") * rs.getDouble("resProcessPrice3")
						+ rs.getInt("processQty1") * rs.getDouble("processPrice1")
						+ rs.getInt("processQty2") * rs.getDouble("processPrice2")
						+ rs.getInt("processQty3") * rs.getDouble("processPrice3")
						+ rs.getInt("processQty4") * rs.getDouble("processPrice4")
						+ rs.getInt("processQty5") * rs.getDouble("processPrice5")
						+ rs.getInt("processQty6") * rs.getDouble("processPrice6");
			}
			DecimalFormat df = new DecimalFormat("#.0000");
			if (dailyDataList.size() == 0) {
				AlertUtil.alertInfoLater(PropsUtil.getMessage("search.result.null"));
				dailyMoney.setText("金额：0.0");
			} else {
				dailyMoney.setText("金额：" + df.format(money));
			}
			// 设置日报单数量
			DailyUtil.setBillnoCount(userNo);

			((MainUIController) SessionUtil.CONTROLLERS.get("MainUIController"))
					.setSumMoney(DailyUtil.setBillmoney(userNo)
							+ WorkUtil.setWorkMoney(userNo, (LocalDate) SessionUtil.PARAMS.get("workStartDate"),
									(LocalDate) SessionUtil.PARAMS.get("workEndDate"))
							+ "");
			dailyTableView.refresh();
		} catch (Exception e) {
			Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
		} finally {
			DBUtil.release(conn, stmt, rs);
		}
		dailyTableView.setItems(dailyDataList);
	}

	/* 获取被勾选的数量 */
	public int getSelectedNum() {
		int count = 0;
		for (int i = 0; i < dailyDataList.size(); i++) {
			if (dailyDataList.get(i).getCheckbox().isSelected()) {
				count++;
			}
		}
		return count;
	}

	/* 获取被勾选的记录 */
	public Daily getSingleSelectedDaily() {
		String dailyNo = null;
		for (int i = 0; i < dailyDataList.size(); i++) {
			if (dailyDataList.get(i).getCheckbox().isSelected()) {
				dailyNo = dailyDataList.get(i).getDailyNo();
				break;
			}
		}

		Daily daily = DailyUtil.getDailyByNo(dailyNo);
		return daily;
	}

	/* 获取所有被勾选的用户编号 */
	public List<String> getSelectedUserNoList() {
		List<String> dailyNoList = new ArrayList<String>();
		for (int i = 0; i < dailyDataList.size(); i++) {
			if (dailyDataList.get(i).getCheckbox().isSelected()) {
				dailyNoList.add(dailyDataList.get(i).getDailyNo());
			}
		}

		return dailyNoList;
	}

	/* 刷新数据 */
	public void refresh() {
		searchDailyAction();
		dailyTableView.setItems(dailyDataList);
		dailyTableView.refresh();
	}
	
	/* 刷新当天数据 */
	public void refreshCurDay() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String userNo = loginUser.getUserNo();
		String curDayDate = DateUtil.currentDayDateStr();

		// 添加表格数据前先清空
		dailyDataList.clear();
		// 金额
		double money = 0.0;

		try {
			String sql = "select * from dbo.t_product_daily_bill_detail where isDelete = 0 ";
			if (!"是".equals(loginUser.getIsManager())) {
				sql = sql + " and createUser = '" + loginUser.getUserNo() + "'";
			}
			
			if (curDayDate != null) {
				sql = sql + " and CONVERT(varchar(100), productDate, 23) = '" + curDayDate + "'";
			}

			sql = sql + " order by dailyNo desc";
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			System.out.println("while begin  : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
			Daily initDaily = new Daily();
			while (rs.next()) {
				Daily daily = initDaily.clone();
				daily.setCheckbox(new CheckBox());
				daily.setDailyNo(rs.getString("dailyNo"));
				daily.setBillNo(rs.getString("billNo"));
				daily.setMaterialCode(rs.getString("materialCode"));
				daily.setMaterialName(rs.getString("materialName"));
				daily.setModel(rs.getString("model"));
				daily.setPlanQty(rs.getInt("planQuantity"));
				daily.setProDate(DateUtil.sqlDateToLocalDate(rs.getDate("productDate")));
				daily.setResPro1(rs.getString("resProcess1"));
				daily.setResProQty1(rs.getInt("resProcessQty1"));
				daily.setResPro2(rs.getString("resProcess2"));
				daily.setResProQty2(rs.getInt("resProcessQty2"));
				daily.setResPro3(rs.getString("resProcess3"));
				daily.setResProQty3(rs.getInt("resProcessQty3"));
				daily.setPro1(rs.getString("process1"));
				daily.setProQty1(rs.getInt("processQty1"));
				daily.setPro2(rs.getString("process2"));
				daily.setProQty2(rs.getInt("processQty2"));
				daily.setPro3(rs.getString("process3"));
				daily.setProQty3(rs.getInt("processQty3"));
				daily.setPro4(rs.getString("process4"));
				daily.setProQty4(rs.getInt("processQty4"));
				daily.setPro5(rs.getString("process5"));
				daily.setProQty5(rs.getInt("processQty5"));
				daily.setPro6(rs.getString("process6"));
				daily.setProQty6(rs.getInt("processQty6"));
				daily.setCreateUserNo(rs.getString("createUser"));
				daily.setCreateTime(rs.getTimestamp("createTime"));
				daily.setModifyUserNo(rs.getString("modifyUser"));
				daily.setModifyTime(rs.getTimestamp("modifyTime"));
				daily.setIsPiecework(rs.getInt("isPiecework"));
				daily.setIsDelete(rs.getInt("isDelete"));
				daily.setSequence(rs.getInt("sequence"));
				daily.setCreateUserName(rs.getString("createUserName"));
				daily.setModifyUserName(rs.getString("modifyUserName"));

				dailyDataList.add(daily);

				// 计算查询出所有工序的金额
				money = money + rs.getInt("resProcessQty1") * rs.getDouble("resProcessPrice1")
						+ rs.getInt("resProcessQty2") * rs.getDouble("resProcessPrice2")
						+ rs.getInt("resProcessQty3") * rs.getDouble("resProcessPrice3")
						+ rs.getInt("processQty1") * rs.getDouble("processPrice1")
						+ rs.getInt("processQty2") * rs.getDouble("processPrice2")
						+ rs.getInt("processQty3") * rs.getDouble("processPrice3")
						+ rs.getInt("processQty4") * rs.getDouble("processPrice4")
						+ rs.getInt("processQty5") * rs.getDouble("processPrice5")
						+ rs.getInt("processQty6") * rs.getDouble("processPrice6");
			}
			System.out.println("while end  : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
			DecimalFormat df = new DecimalFormat("#.0000");
			if (dailyDataList.size() == 0) {
				AlertUtil.alertInfoLater(PropsUtil.getMessage("search.result.null"));
				dailyMoney.setText("金额：0.0");
			} else {
				dailyMoney.setText("金额：" + df.format(money));
			}
			// 设置日报单数量
			DailyUtil.setBillnoCount(userNo);

			((MainUIController) SessionUtil.CONTROLLERS.get("MainUIController"))
					.setSumMoney(DailyUtil.setBillmoney(userNo)
							+ WorkUtil.setWorkMoney(userNo, (LocalDate) SessionUtil.PARAMS.get("workStartDate"),
									(LocalDate) SessionUtil.PARAMS.get("workEndDate"))
							+ "");
			dailyTableView.refresh();
		} catch (Exception e) {
			Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
		} finally {
			DBUtil.release(conn, stmt, rs);
		}
	}

	public static void main(String[] args) {

		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
	}

}
