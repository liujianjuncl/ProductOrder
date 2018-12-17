package com.nii.desktop.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nii.desktop.model.Work;
import com.nii.desktop.util.conf.DBUtil;
import com.nii.desktop.util.conf.PropsUtil;
import com.nii.desktop.util.conf.SessionUtil;
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

public class WorkTableViewController implements Initializable {

    @FXML
    private VBox workVBox;

    @FXML
    private Button addWorkBtn;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchBtn;

    @FXML
    private TableView<Work> workTableView;
    
    /* 数据 */
    private ObservableList<Work> workDataList = FXCollections.observableArrayList();

    /* 间接作业编号 */
    @FXML
    private TableColumn<Work, String> workNoCol;

    /* 间接作业名称 */
    @FXML
    private TableColumn<Work, String> workNameCol;

    /* 计量单位 */
    @FXML
    private TableColumn<Work, String> unitCol;

    /* 单位单价 */
    @FXML
    private TableColumn<Work, Double> unitPriceCol;

    /* 使用状态 */
    @FXML
    private TableColumn<Work, String> statusCol;
    
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
        if (SessionUtil.CONTROLLERS.get("WorkTableViewController") == null) {
            SessionUtil.CONTROLLERS.put("WorkTableViewController", this);
        }
        
        if(!"是".equals(SessionUtil.USERS.get("loginUser").getIsManager())) {
            addWorkBtn.setVisible(false);
        }

        // 双击某一行时，编辑该行
        workTableView.setRowFactory(new Callback<TableView<Work>, TableRow<Work>>() {
            @Override
            public TableRow<Work> call(TableView<Work> param) {
                TableRow<Work> row = new TableRow<Work>();
                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        // TODO Auto-generated method stub
                        if (event.getClickCount() == 2) {
                            Work work = row.getItem();
                            modifyWorkAction(work);
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

        String workNo = null;
        String workName = null;
        String unit = null;
        double unitPrice = 0.0;
        String status = null;

        // 添加表格数据前先清空
        workDataList.clear();

        try {
            String sql = "select workNo, workName, unit, unitPrice, status from dbo.t_product_daily_work";
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                workNo = rs.getString("workNo");
                workName = rs.getString("workName");
                unit = rs.getString("unit");
                unitPrice = rs.getDouble("unitPrice");
                status = rs.getInt("status") == 1 ? "禁用" : "正常";
                workDataList.add(new Work(workNo, workName, unit, unitPrice, status));
            }

        } catch (Exception e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }
    }

    /* 新建作业 */
    @FXML
    private void addWorkAction() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ResourceLoader.getFxmlResource("AddWork.fxml"));

        AnchorPane addWorkPane;

        try {
            addWorkPane = fxmlLoader.load();

            dialogStage = new Stage();
            dialogStage.setTitle("添加作业项目");

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(UIManager.getPrimaryStage());
            Scene scene = new Scene(addWorkPane);
            dialogStage.setScene(scene);

            dialogStage.setResizable(false);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDatatoTableView() {
        workNoCol.setCellValueFactory(new PropertyValueFactory<Work, String>("workNo"));
        workNameCol.setCellValueFactory(new PropertyValueFactory<Work, String>("workName"));
        unitCol.setCellValueFactory(new PropertyValueFactory<Work, String>("unit"));
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<Work, Double>("unitPrice"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Work, String>("status"));

        workTableView.setItems(workDataList);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        });
    }

    /* 修改用户 */
    public void modifyWorkAction(Work work) {
        SessionUtil.WORKS.put("editWork", work);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ResourceLoader.getFxmlResource("ModifyWork.fxml"));

        AnchorPane modifyWorkPane;

        try {
            modifyWorkPane = fxmlLoader.load();

            dialogStage = new Stage();
            dialogStage.setTitle("编辑作业项目");

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(UIManager.getPrimaryStage());
            Scene scene = new Scene(modifyWorkPane);
            dialogStage.setScene(scene);

            dialogStage.setResizable(false);
            dialogStage.showAndWait();

        } catch (IOException e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    public void workSearchAction() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String workNo = null;
        String workName = null;
        String unit = null;
        double unitPrice = 0.0;
        String status = null;
        
        String searchWorkNo = searchField.getText().trim();
        
        // 添加表格数据前先清空
        workDataList.clear();

        try {
            String sql = "select workNo, workName, unit, unitPrice, status from dbo.t_product_daily_work where 1 = 1 ";
            if(!"".equals(searchWorkNo)) {
                sql = sql + " and workNo = '" + searchWorkNo + "'";
            }
            
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                workNo = rs.getString("workNo");
                workName = rs.getString("workName");
                unit = rs.getString("unit");
                unitPrice = rs.getDouble("unitPrice");
                status = rs.getInt("status") == 1 ? "禁用" : "正常";
                workDataList.add(new Work(workNo, workName, unit, unitPrice, status));
            }
            
            if (workDataList.size() == 0) {
                AlertUtil.alertInfoLater(PropsUtil.getMessage("search.result.null"));
            } 
        } catch (Exception e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }
        workTableView.refresh();
    }

    /* 刷新数据 */
    public void refresh() {
        initData();
        addDatatoTableView();
    }
}
