package com.nii.desktop.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nii.desktop.model.WorkDetail;
import com.nii.desktop.util.conf.DBUtil;
import com.nii.desktop.util.conf.DailyUtil;
import com.nii.desktop.util.conf.DateUtil;
import com.nii.desktop.util.conf.PropsUtil;
import com.nii.desktop.util.conf.SessionUtil;
import com.nii.desktop.util.conf.UserUtil;
import com.nii.desktop.util.conf.WorkUtil;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class WorkTableDetailViewController implements Initializable {

    @FXML
    private VBox workDetailVBox;

    @FXML
    private Button addWorkDetailBtn;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchBtn;

    @FXML
    private Button auditWorkDetailBtn;

    @FXML
    private Button antiAuditWorkDetailBtn;

    @FXML
    private TableView<WorkDetail> workDetailTableView;

    /* 数据 */
    private ObservableList<WorkDetail> workDataList = FXCollections.observableArrayList();

    /* 多选框 */
    @FXML
    private TableColumn<WorkDetail, CheckBox> selectCol;

    /* 间接日报单编号 */
    @FXML
    private TableColumn<WorkDetail, String> workDetailNoCol;

    @FXML
    private TableColumn<WorkDetail, Timestamp> workDateCol;

    /* 状态 */
    @FXML
    private TableColumn<WorkDetail, String> statusCol;

    /* 作业编号 */
    @FXML
    private TableColumn<WorkDetail, String> workNoCol;

    /* 作业名称 */
    @FXML
    private TableColumn<WorkDetail, String> workNameCol;

    /* 计量单位 */
    @FXML
    private TableColumn<WorkDetail, String> unitCol;

    /* 单位单价 */
    @FXML
    private TableColumn<WorkDetail, Double> unitPriceCol;

    /* 实际作业数量 */
    @FXML
    private TableColumn<WorkDetail, Integer> workNumCol;

    /* 作业金额 */
    @FXML
    private TableColumn<WorkDetail, Double> moneyCol;

    /* 创建人 */
    @FXML
    private TableColumn<WorkDetail, String> createUserCol;

    /* 创建时间 */
    @FXML
    private TableColumn<WorkDetail, Timestamp> createTimeCol;

    /* 修改人 */
    @FXML
    private TableColumn<WorkDetail, String> modifyUserCol;

    /* 修改时间 */
    @FXML
    private TableColumn<WorkDetail, Timestamp> modifyTimeCol;

    /* 审核人 */
    @FXML
    private TableColumn<WorkDetail, String> auditorCol;

    /* 审核时间 */
    @FXML
    private TableColumn<WorkDetail, Timestamp> auditorTimeCol;

    @FXML
    private Label workMoney;

    @FXML
    private TextField userNoTextField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

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
//        addDatatoTableView();
        if (SessionUtil.CONTROLLERS.get("WorkTableDetailViewController") == null) {
            SessionUtil.CONTROLLERS.put("WorkTableDetailViewController", this);
        }

        // 双击某一行时，编辑该行
        workDetailTableView.setRowFactory(new Callback<TableView<WorkDetail>, TableRow<WorkDetail>>() {
            @Override
            public TableRow<WorkDetail> call(TableView<WorkDetail> param) {
                TableRow<WorkDetail> row = new TableRow<WorkDetail>();
                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        // TODO Auto-generated method stub
                        if (event.getClickCount() == 2) {
                            WorkDetail workDetail = row.getItem();
                            modifyWorkDetailAction(workDetail);
                        }
                    }
                });
                return row;
            }
        });

        // 只有管理员和审核员显示“审核”和“反审核”按钮
        if (!"是".equals(SessionUtil.USERS.get("loginUser").getIsAuditor())
                && !"是".equals(SessionUtil.USERS.get("loginUser").getIsManager())) {
            auditWorkDetailBtn.setVisible(false);
            antiAuditWorkDetailBtn.setVisible(false);
        }

        // 分页
//        userTablePagination.setPageCount(1);
    }

//    /* 初始化表格数据 */
//    private void initData() {
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        ResultSet rs = null;
//
//        // 添加表格数据前先清空
//        workDataList.clear();
//
//        double money = 0.0;
//        try {
//            String sql = "select work.* from dbo.t_product_daily_work_detail work " + 
//                    " left join dbo.t_product_daily_user u on work.createUser = u.userNo " + 
//                    " where work.isDelete = 0 ";
//            // 如果当前用户既不是管理员也不是审核员，则只查询当前用户的间接日报单
//            if (!"是".equals(SessionUtil.USERS.get("loginUser").getIsManager())
//                    && !"是".equals(SessionUtil.USERS.get("loginUser").getIsAuditor())) {
//                sql = sql + " and work.createUser = '" + SessionUtil.USERS.get("loginUser").getUserNo() + "'";
//            }
//
//            // 如果当前用户不是管理员，但是是审核员，则显示该用户名下的间接日报单
//            if (!"是".equals(SessionUtil.USERS.get("loginUser").getIsManager())
//                    && "是".equals(SessionUtil.USERS.get("loginUser").getIsAuditor())) {
//                sql = sql + " and u.auditor = '" + SessionUtil.USERS.get("loginUser").getUserNo() + "'";
//            }
//
//            conn = DBUtil.getConnection();
//            stmt = conn.prepareStatement(sql);
//            rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                WorkDetail workDetail = new WorkDetail(new CheckBox(), rs.getString("workDetailNo"),
//                        rs.getTimestamp("workDate"), rs.getInt("status") == 1 ? "已审核" : "未审核", rs.getString("workNo"),
//                        rs.getString("workName"), rs.getString("unit"), rs.getDouble("unitPrice"), rs.getInt("workNum"),
//                        rs.getDouble("money"), UserUtil.getUser(rs.getString("createUser")).getUserName(),
//                        rs.getTimestamp("createTime"),
//                        UserUtil.getUser(rs.getString("modifyUser")) == null ? null
//                                : UserUtil.getUser(rs.getString("modifyUser")).getUserName(),
//                        rs.getTimestamp("modifyTime"),
//                        UserUtil.getUser(rs.getString("auditor")) == null ? null
//                                : UserUtil.getUser(rs.getString("auditor")).getUserName(),
//                        rs.getTimestamp("auditorTime"));
//                workDataList.add(workDetail);
//                
//                money = money + rs.getDouble("unitPrice") * rs.getInt("workNum");
//            }
//            DecimalFormat df = new DecimalFormat("#.0000");
//            if (workDataList.size() == 0) {
//                AlertUtil.alertInfoLater(PropsUtil.getMessage("search.result.null"));
//                workMoney.setText("金额：0.0");
//            } else {
//                workMoney.setText("金额：" + df.format(money));
//            }
//        } catch (Exception e) {
//            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
//        } finally {
//            DBUtil.release(conn, stmt, rs);
//        }
//    }

    /* 新建作业 */
    @FXML
    private void addWorkDetailAction() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ResourceLoader.getFxmlResource("AddWorkDetail.fxml"));

        AnchorPane addWorkDetailPane;

        try {
            addWorkDetailPane = fxmlLoader.load();

            dialogStage = new Stage();
            dialogStage.setTitle("添加间接日报单");

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(UIManager.getPrimaryStage());
            Scene scene = new Scene(addWorkDetailPane);
            dialogStage.setScene(scene);

            dialogStage.setResizable(false);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDatatoTableView() {
        selectCol.setCellValueFactory(new PropertyValueFactory<WorkDetail, CheckBox>("checkbox"));
        workDetailNoCol.setCellValueFactory(new PropertyValueFactory<WorkDetail, String>("workDetailNo"));
        workDateCol.setCellValueFactory(new PropertyValueFactory<WorkDetail, Timestamp>("workDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<WorkDetail, String>("status"));
        workNoCol.setCellValueFactory(new PropertyValueFactory<WorkDetail, String>("workNo"));
        workNameCol.setCellValueFactory(new PropertyValueFactory<WorkDetail, String>("workName"));
        unitCol.setCellValueFactory(new PropertyValueFactory<WorkDetail, String>("unit"));
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<WorkDetail, Double>("unitPrice"));
        workNumCol.setCellValueFactory(new PropertyValueFactory<WorkDetail, Integer>("workNum"));
        moneyCol.setCellValueFactory(new PropertyValueFactory<WorkDetail, Double>("money"));
        createUserCol.setCellValueFactory(new PropertyValueFactory<WorkDetail, String>("createUser"));
        createTimeCol.setCellValueFactory(new PropertyValueFactory<WorkDetail, Timestamp>("createTime"));
        modifyUserCol.setCellValueFactory(new PropertyValueFactory<WorkDetail, String>("modifyUser"));
        modifyTimeCol.setCellValueFactory(new PropertyValueFactory<WorkDetail, Timestamp>("modifyTime"));
        auditorCol.setCellValueFactory(new PropertyValueFactory<WorkDetail, String>("auditor"));
        auditorTimeCol.setCellValueFactory(new PropertyValueFactory<WorkDetail, Timestamp>("auditorTime"));

        workDetailTableView.setItems(workDataList);

//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                initData();
//            }
//        });
    }

    /* 修改间接日报单 */
    public void modifyWorkDetailAction(WorkDetail workDetail) {
        if ("已审核".equals(workDetail.getStatus())) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("audit.workDetail.donot.modify"));
            return;
        }

        if (!"是".equals(SessionUtil.USERS.get("loginUser").getIsManager())) {
            if (workDetail.getWorkDate().compareTo(DateUtil.lastMonth26Day()) < 0
                    || workDetail.getWorkDate().compareTo(DateUtil.curMonth25Day()) > 0) {
                AlertUtil.alertInfoLater(PropsUtil.getMessage("workDetail.donot.modify"));
                return;
            }
        }

        SessionUtil.WORKDETAILS.put("editWorkDetail", workDetail);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ResourceLoader.getFxmlResource("ModifyWorkDetail.fxml"));

        AnchorPane modifyWorkDetailPane;

        try {
            modifyWorkDetailPane = fxmlLoader.load();

            dialogStage = new Stage();
            dialogStage.setTitle("编辑间接日报单");

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(UIManager.getPrimaryStage());
            Scene scene = new Scene(modifyWorkDetailPane);
            dialogStage.setScene(scene);

            dialogStage.setResizable(false);
            dialogStage.showAndWait();

        } catch (IOException e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    public void workDetailSearchAction() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String userNo = userNoTextField.getText().trim();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        // 添加表格数据前先清空
        workDataList.clear();

        double money = 0.0;

        try {
            String sql = "select work.* from dbo.t_product_daily_work_detail work "
                    + " left join dbo.t_product_daily_user u on work.createUser = u.userNo "
                    + " where work.isDelete = 0 ";
            // 如果当前用户既不是管理员也不是审核员，则只查询当前用户的间接日报单
            if (!"是".equals(SessionUtil.USERS.get("loginUser").getIsManager())
                    && !"是".equals(SessionUtil.USERS.get("loginUser").getIsAuditor())) {
                sql = sql + " and work.createUser = '" + SessionUtil.USERS.get("loginUser").getUserNo() + "'";
            }

            // 如果当前用户不是管理员，但是是审核员，则显示该用户名下的间接日报单
            if (!"是".equals(SessionUtil.USERS.get("loginUser").getIsManager())
                    && "是".equals(SessionUtil.USERS.get("loginUser").getIsAuditor())) {
                sql = sql + " and u.auditor = '" + SessionUtil.USERS.get("loginUser").getUserNo() + "'";
            }

            if (userNo != null && !"".equals(userNo)) {
                sql = sql + " and work.createUser like '%" + userNo + "%'";
            }

            if (startDate != null) {
                sql = sql + " and work.workDate >= '" + DateUtil.localDateToDateTimeStr(startDate) + "'";
            }

            if (endDate != null) {
                sql = sql + " and work.workDate <= '" + DateUtil.localDateToDateTimeStr(endDate) + "'";
            }

            if (userNo != null && !"".equals(userNo)) {
                sql = sql + " and work.createUser like '%" + userNo + "%'";
            }

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                WorkDetail workDetail = new WorkDetail(new CheckBox(), rs.getString("workDetailNo"),
                        rs.getTimestamp("workDate"), rs.getInt("status") == 1 ? "已审核" : "未审核", rs.getString("workNo"),
                        rs.getString("workName"), rs.getString("unit"), rs.getDouble("unitPrice"), rs.getInt("workNum"),
                        rs.getDouble("money"), UserUtil.getUser(rs.getString("createUser")).getUserName(),
                        rs.getTimestamp("createTime"),
                        UserUtil.getUser(rs.getString("modifyUser")) == null ? null
                                : UserUtil.getUser(rs.getString("modifyUser")).getUserName(),
                        rs.getTimestamp("modifyTime"),
                        UserUtil.getUser(rs.getString("auditor")) == null ? null
                                : UserUtil.getUser(rs.getString("auditor")).getUserName(),
                        rs.getTimestamp("auditorTime"));
                workDataList.add(workDetail);

                money = money + rs.getDouble("unitPrice") * rs.getInt("workNum");

            }
            addDatatoTableView();
            setWorkDetailAuditMoney();
            ((MainUIController) SessionUtil.CONTROLLERS.get("MainUIController")).setSumMoney(
                    DailyUtil.setBillmoney(userNo) + WorkUtil.setWorkMoney(userNo, startDate, endDate) + "");
        } catch (Exception e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }
        workDetailTableView.refresh();
    }

    @FXML
    public void setWorkDetailAuditMoney() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String userNo = userNoTextField.getText().trim();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        // 将时间条件存到缓存中，在其他地方调用
        SessionUtil.PARAMS.put("workStartDate", startDate);
        SessionUtil.PARAMS.put("workEndDate", endDate);

        double money = 0.0;

        try {
            String sql = "select work.* from dbo.t_product_daily_work_detail work "
                    + " left join dbo.t_product_daily_user u on work.createUser = u.userNo "
                    + " where work.isDelete = 0 and work.status = 1 ";
            // 如果当前用户既不是管理员也不是审核员，则只查询当前用户的间接日报单
            if (!"是".equals(SessionUtil.USERS.get("loginUser").getIsManager())
                    && !"是".equals(SessionUtil.USERS.get("loginUser").getIsAuditor())) {
                sql = sql + " and work.createUser = '" + SessionUtil.USERS.get("loginUser").getUserNo() + "'";
            }

            // 如果当前用户不是管理员，但是是审核员，则显示该用户名下的间接日报单
            if (!"是".equals(SessionUtil.USERS.get("loginUser").getIsManager())
                    && "是".equals(SessionUtil.USERS.get("loginUser").getIsAuditor())) {
                sql = sql + " and u.auditor = '" + SessionUtil.USERS.get("loginUser").getUserNo() + "'";
            }

            if (userNo != null && !"".equals(userNo)) {
                sql = sql + " and work.createUser like '%" + userNo + "%'";
            }

            if (startDate != null) {
                sql = sql + " and work.workDate >= '" + DateUtil.localDateToDateTimeStr(startDate) + "'";
            }

            if (endDate != null) {
                sql = sql + " and work.workDate <= '" + DateUtil.localDateToDateTimeStr(endDate) + "'";
            }

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                money = money + rs.getDouble("unitPrice") * rs.getInt("workNum");
            }
            DecimalFormat df = new DecimalFormat("#.0000");
            if (workDataList.size() == 0) {
                AlertUtil.alertInfoLater(PropsUtil.getMessage("search.result.null"));
                workMoney.setText("金额：0.0");
            } else {
                workMoney.setText("金额：" + df.format(money));
            }
            addDatatoTableView();
            WorkUtil.setWorkMoney(userNo, startDate, endDate);
        } catch (Exception e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }
        workDetailTableView.refresh();
    }

    /* 刷新数据 */
    public void refresh() {
        workDetailSearchAction();
    }

    @FXML
    public void deleteWorkDetailAction() {
        if (getSelectedNum() == 0) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("comboBox.delete.noSelected"));
        } else if (AlertUtil.alertConfirmLater(PropsUtil.getMessage("confirm.delete"))) {
            List<String> workDetailNoList = getSelectedWorkDetailNoList();

            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                String sql = "update dbo.t_product_daily_work_detail set isDelete = 1 where workDetailNo = ?";
                conn = DBUtil.getConnection();
                conn.setAutoCommit(false);
                stmt = conn.prepareStatement(sql);
                for (int i = 0; i < workDetailNoList.size(); i++) {
                    String workDetailNo = workDetailNoList.get(i);
                    WorkDetail workD = WorkUtil.getWorkDetailByNo(workDetailNo);
                    if ("已审核".equals(workD.getStatus())) {
                        AlertUtil.alertInfoLater(PropsUtil.getMessage("audit.workDetail.donot.delete"));
                        try {
                            conn.rollback();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        return;
                    }
                    stmt.setString(1, workDetailNo);
                    stmt.executeUpdate();
                    conn.commit();
                }

            } catch (Exception e) {
                Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                DBUtil.release(conn, stmt);
            }
            AlertUtil.alertInfoLater(PropsUtil.getMessage("workDetail.delete.success"));
            // 删除完成刷新数据
            refresh();
        }
    }

    /* 获取被勾选的数量 */
    public int getSelectedNum() {
        int count = 0;
        for (int i = 0; i < workDataList.size(); i++) {
            if (workDataList.get(i).getCheckbox().isSelected()) {
                count++;
            }
        }
        return count;
    }

    /* 获取被勾选的记录 */
    public WorkDetail getSingleSelectedWorkDetail() {
        String workDetailNo = null;
        for (int i = 0; i < workDataList.size(); i++) {
            if (workDataList.get(i).getCheckbox().isSelected()) {
                workDetailNo = workDataList.get(i).getWorkDetailNo();
                break;
            }
        }

        WorkDetail workDetail = WorkUtil.getWorkDetailByNo(workDetailNo);
        return workDetail;
    }

    /* 获取所有被勾选的间接日报单编号 */
    public List<String> getSelectedWorkDetailNoList() {
        List<String> workDetailNoList = new ArrayList<String>();
        for (int i = 0; i < workDataList.size(); i++) {
            if (workDataList.get(i).getCheckbox().isSelected()) {
                workDetailNoList.add(workDataList.get(i).getWorkDetailNo());
            }
        }

        return workDetailNoList;
    }

    // 审核
    @FXML
    public void auditWorkDetailAction() {
        if (getSelectedNum() == 0) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("workDetail.audit.noSelect"));
        } else if (AlertUtil.alertConfirmLater(PropsUtil.getMessage("confirm.audit"))) {
            List<String> workDetailNoList = getSelectedWorkDetailNoList();
            List<WorkDetail> workDetailList = new ArrayList<WorkDetail>();

            for (int i = 0; i < workDetailNoList.size(); i++) {
                workDetailList.add(WorkUtil.getWorkDetailByNo(workDetailNoList.get(i)));
            }

            if (!"是".equals(SessionUtil.USERS.get("loginUser").getIsManager())) {
                for (int i = 0; i < workDetailList.size(); i++) {
                    if (workDetailList.get(i).getWorkDate().compareTo(DateUtil.lastMonth26Day()) < 0
                            || workDetailList.get(i).getWorkDate().compareTo(DateUtil.curMonth25Day()) > 0) {
                        AlertUtil.alertInfoLater(PropsUtil.getMessage("workDetail.donot.audit"));
                        return;
                    }
                }
            }

            int count = 0;
            for (int i = 0; i < workDetailList.size(); i++) {
                // 判断是否有未审核的间接日报单
                if ("未审核".equals(workDetailList.get(i).getStatus())) {
                    count++;
                }
            }

            if (count == 0) {
                AlertUtil.alertInfoLater(PropsUtil.getMessage("workDetail.no.audit"));
                return;
            }

            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                String sql = "update dbo.t_product_daily_work_detail set status = 1, auditor = ?, auditorTime = ? where workDetailNo = ?";
                conn = DBUtil.getConnection();
                stmt = conn.prepareStatement(sql);

                for (int i = 0; i < workDetailNoList.size(); i++) {
                    String workDetailNo = workDetailNoList.get(i);
                    stmt.setString(1, SessionUtil.USERS.get("loginUser").getUserNo());
                    stmt.setTimestamp(2, new Timestamp(new Date().getTime()));
                    stmt.setString(3, workDetailNo);
                    stmt.executeUpdate();
                }

            } catch (Exception e) {
                Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
                return;
            } finally {
                DBUtil.release(conn, stmt);
            }
            AlertUtil.alertInfoLater(PropsUtil.getMessage("workDetail.audit.success"));
            // 删除完成刷新数据
            refresh();
        }
    }

    // 反审核
    @FXML
    public void antiAuditWorkDetailAction() {
        if (getSelectedNum() == 0) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("workDetail.antiAudit.noSelect"));
        } else if (AlertUtil.alertConfirmLater(PropsUtil.getMessage("confirm.antiAudit"))) {
            List<String> workDetailNoList = getSelectedWorkDetailNoList();
            List<WorkDetail> workDetailList = new ArrayList<WorkDetail>();

            for (int i = 0; i < workDetailNoList.size(); i++) {
                workDetailList.add(WorkUtil.getWorkDetailByNo(workDetailNoList.get(i)));
            }

            if (!"是".equals(SessionUtil.USERS.get("loginUser").getIsManager())) {
                for (int i = 0; i < workDetailList.size(); i++) {
                    if (workDetailList.get(i).getWorkDate().compareTo(DateUtil.lastMonth26Day()) < 0
                            || workDetailList.get(i).getWorkDate().compareTo(DateUtil.curMonth25Day()) > 0) {
                        AlertUtil.alertInfoLater(PropsUtil.getMessage("workDetail.donot.antiAudit"));
                        return;
                    }
                }
            }

            int count = 0;
            for (int i = 0; i < workDetailList.size(); i++) {
                // 判断是否有已审核的间接日报单
                if ("已审核".equals(workDetailList.get(i).getStatus())) {
                    count++;
                }
            }

            if (count == 0) {
                AlertUtil.alertInfoLater(PropsUtil.getMessage("workDetail.no.antiAudit"));
                return;
            }

            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                String sql = "update dbo.t_product_daily_work_detail set status = 0 where workDetailNo = ?";
                conn = DBUtil.getConnection();
                stmt = conn.prepareStatement(sql);

                for (int i = 0; i < workDetailNoList.size(); i++) {
                    String workDetailNo = workDetailNoList.get(i);
                    stmt.setString(1, workDetailNo);
                    stmt.executeUpdate();
                }

            } catch (Exception e) {
                Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
                return;
            } finally {
                DBUtil.release(conn, stmt);
            }
            AlertUtil.alertInfoLater(PropsUtil.getMessage("workDetail.antiAudit.success"));
            // 删除完成刷新数据
            refresh();
        }
    }

}
