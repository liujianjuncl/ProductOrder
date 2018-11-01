package com.nii.desktop.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nii.desktop.model.Daily;
import com.nii.desktop.model.DailyProcessQty;
import com.nii.desktop.model.DateUtil;
import com.nii.desktop.util.conf.DBUtil;
import com.nii.desktop.util.conf.DailyUtil;
import com.nii.desktop.util.conf.SessionUtil;
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

    /* ��� */
    @FXML
    private TableView<Daily> dailyTableView;

    /* ��ҳ */
    @FXML
    private Pagination dailyTablePagination;

    /* ���� */
    private ObservableList<Daily> dailyDataList = FXCollections.observableArrayList();

    @FXML
    private Label dailyLabel;

    @FXML
    private HBox dailyHbox;

    /* selectCol */
    @FXML
    private TableColumn<Daily, CheckBox> selectCol;

    /* �ձ����� */
    @FXML
    private TableColumn<Daily, String> dailyNoCol;

    /* �������� */
    @FXML
    private TableColumn<Daily, String> billNoCol;

    /* ���ϴ��� */
    @FXML
    private TableColumn<Daily, String> materialCodeCol;

    /* �������� */
    @FXML
    private TableColumn<Daily, String> materialNameCol;

    /* ����ͺ� */
    @FXML
    private TableColumn<Daily, String> modelCol;

    /* �ƻ��������� */
    @FXML
    private TableColumn<Daily, String> planQuantityCol;
    
    /* �������� */
    @FXML
    private TableColumn<Daily, String> productDateCol;

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

    /* ϵͳstage */
    private static Stage dialogStage;

    public static Stage getdialogStage() {
        return dialogStage;
    }

    public static void setPrimaryStage(Stage stage) {
        dialogStage = stage;
    }

    /** ��ʼ�� */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addDatatoTableView();
        if (SessionUtil.CONTROLLERS.get("DailyTableViewController") == null) {
            SessionUtil.CONTROLLERS.put("DailyTableViewController", this);
        }

        // ˫��ĳһ��ʱ���༭����
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

        // �����ǰ��¼�û����ǹ���Ա������ʾ���Ӻ�ɾ����ť
        if (!"��".equals(SessionUtil.USERS.get("loginUser").getIsManager())) {
            addDailyBtn.setVisible(false);
            delDailyBtn.setVisible(false);
        }
        // ��ҳ
//        dailyTablePagination.setPageCount(1);
    }

    /* ��ʼ��������� */
    private void initData() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String dailyNo = null;
        String billNo = null;
        String materialCode = null;
        String materialName = null;
        String model = null;
        int planQuantity = 0;
        LocalDate productDate = null;

        // ��ӱ������ǰ�����
        dailyDataList.clear();

        try {
            String sql = "select dailyNo, billNo, materialCode, materialName, model, planQuantity, productDate "
                    + "from dbo.t_product_daily_bill_detail where isDelete = 0 ";
            if (!"��".equals(SessionUtil.USERS.get("loginUser").getIsManager())) {
                sql = sql + " and createUser = " + SessionUtil.USERS.get("loginUser").getUserNo();
            }

            sql = sql + " order by dailyNo desc";
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getDate("productDate"));
                dailyNo = rs.getString("dailyNo");
                billNo = rs.getString("billNo");
                materialCode = rs.getString("materialCode");
                materialName = rs.getString("materialName");
                model = rs.getString("model");
                planQuantity = rs.getInt("planQuantity");
                productDate = DateUtil.sqlDateToLocalDate(rs.getDate("productDate"));
                dailyDataList.add(
                        new Daily(new CheckBox(), dailyNo, billNo, materialCode, materialName, model, planQuantity, productDate));
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
        productDateCol.setCellValueFactory(new PropertyValueFactory<Daily, String>("productDate"));

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
            dialogStage.setTitle("�½��ձ�");

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

    /* �޸��ձ� */
    public void modifyDailyAction(Daily daily) {
        SessionUtil.DAILYS.put("editDaily", daily);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ResourceLoader.getFxmlResource("ModifyDaily.fxml"));

        AnchorPane modifyDailyPane;

        try {
            modifyDailyPane = fxmlLoader.load();

            dialogStage = new Stage();
            dialogStage.setTitle("�༭�ձ�");

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

    /* ɾ���ձ� */
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
                    DailyProcessQty dpq = DailyUtil.getProcessTotalQty(daily.getBillNo());

                    // ��ʵ�����ܱ��б���ɾ�����ձ���ʵ����������
                    DailyProcessQty dailyProcessQty = new DailyProcessQty(daily.getBillNo(), daily.getDailyNo(),
                            daily.getPlanQty(), dpq.getProQty1() - daily.getResProQty1(),
                            dpq.getResProQty2() - daily.getResProQty2(), dpq.getResProQty3() - daily.getResProQty3(),
                            dpq.getProQty1() - daily.getProQty1(), dpq.getProQty2() - daily.getProQty2(),
                            dpq.getProQty3() - daily.getProQty3(), dpq.getProQty3() - daily.getProQty4(),
                            dpq.getProQty5() - daily.getProQty5(), dpq.getProQty6() - daily.getProQty6(),
                            dpq.getProQty7() - daily.getProQty7(), dpq.getProQty8() - daily.getProQty8(),
                            dpq.getProQty9() - daily.getProQty9());
                    DailyUtil.updateDailyTotalQty(dailyProcessQty);

                    stmt.executeUpdate();
                }

            } catch (Exception e) {
                Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
                return;
            } finally {
                DBUtil.release(conn, stmt);
            }
            AlertUtil.alertInfoLater(PropsUtil.getMessage("user.delete.success"));
            // ɾ�����ˢ������
            ((DailyTableViewController) SessionUtil.CONTROLLERS.get("DailyTableViewController")).refresh();
        }
    }

    @FXML
    public void searchDailyAction() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String userNo = userNoTextField.getText();
        String billNo = billNoTextField.getText();
        String date = startDatePicker.getValue().toString();

        // ��ӱ������ǰ�����
        dailyDataList.clear();

        try {
            String sql = "select dailyNo, billNo, materialCode, materialName, model, planQuantity "
                    + "from dbo.t_product_daily_bill_detail where isDelete = 0 ";
            if (!"��".equals(SessionUtil.USERS.get("loginUser").getIsManager())) {
                sql = sql + " and createUser = " + SessionUtil.USERS.get("loginUser").getUserNo();
            }

            if (userNo != null) {
                sql = sql + " and createUser like %" + userNo + "%";
            }

            if (userNo != null) {
                sql = sql + " and billNo like %" + billNo + "%";
            }

            if (userNo != null) {
                sql = sql + " and createUser like %" + userNo + "%";
            }

            sql = sql + " order by dailyNo desc";
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Daily daily = new Daily(new CheckBox(), rs.getString("dailyNo"), rs.getString("billNo"),
                        rs.getString("materialCode"), rs.getString("materialName"), rs.getString("model"),
                        rs.getInt("planQuantity"), DateUtil.dateToLocalDate(rs.getDate("productDate")));
                dailyDataList.add(daily);
            }

        } catch (Exception e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }
    }

    /* ��ȡ����ѡ������ */
    public int getSelectedNum() {
        int count = 0;
        for (int i = 0; i < dailyDataList.size(); i++) {
            if (dailyDataList.get(i).getCheckbox().isSelected()) {
                count++;
            }
        }
        return count;
    }

    /* ��ȡ����ѡ�ļ�¼ */
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

    /* ��ȡ���б���ѡ���û���� */
    public List<String> getSelectedUserNoList() {
        List<String> dailyNoList = new ArrayList<String>();
        for (int i = 0; i < dailyDataList.size(); i++) {
            if (dailyDataList.get(i).getCheckbox().isSelected()) {
                dailyNoList.add(dailyDataList.get(i).getDailyNo());
            }
        }

        return dailyNoList;
    }

    /* ˢ������ */
    public void refresh() {
        initData();
        addDatatoTableView();
    }

}
