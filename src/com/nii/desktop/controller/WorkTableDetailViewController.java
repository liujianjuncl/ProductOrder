package com.nii.desktop.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nii.desktop.model.Work;
import com.nii.desktop.model.WorkDetail;
import com.nii.desktop.util.conf.DBUtil;
import com.nii.desktop.util.conf.SessionUtil;
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

        // 分页
//        userTablePagination.setPageCount(1);
    }

    /* 初始化表格数据 */
    private void initData() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        // 添加表格数据前先清空
        workDataList.clear();

        try {
            String sql = "select * from dbo.t_product_daily_work_detail where isDelete = 0 ";
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                WorkDetail workDetail = new WorkDetail(new CheckBox(), rs.getString("workDetailNo"),
                        rs.getTimestamp("workDate"), rs.getString("status"), rs.getString("workNo"),
                        rs.getString("workName"), rs.getString("unit"), rs.getDouble("unitPrice"), rs.getInt("workNum"),
                        rs.getDouble("money"), rs.getString("createUser"), rs.getTimestamp("createTime"),
                        rs.getString("modifyUser"), rs.getTimestamp("modifyTime"), rs.getString("auditor"),
                        rs.getTimestamp("auditorTime"));
                workDataList.add(workDetail);
            }
        } catch (Exception e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }
    }

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

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        });
    }

    /* 修改用户 */
    public void modifyWorkDetailAction(WorkDetail workDetail) {
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

        // 添加表格数据前先清空
        workDataList.clear();

        try {
            String sql = "select * from dbo.t_product_daily_work_detail where isDelete = 0 ";

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                WorkDetail workDetail = new WorkDetail(new CheckBox(), rs.getString("workDetailNo"),
                        rs.getTimestamp("workDate"), rs.getString("status"), rs.getString("workNo"),
                        rs.getString("workName"), rs.getString("unit"), rs.getDouble("unitPrice"), rs.getInt("workNum"),
                        rs.getDouble("money"), rs.getString("createUser"), rs.getTimestamp("createTime"),
                        rs.getString("modifyUser"), rs.getTimestamp("modifyTime"), rs.getString("auditor"),
                        rs.getTimestamp("auditorTime"));
                workDataList.add(workDetail);
            }
        } catch (Exception e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }
        workDetailTableView.refresh();
    }

    /* 刷新数据 */
    public void refresh() {
        initData();
        addDatatoTableView();
    }

    @FXML
    public void workSearchAction() {

    }

}
