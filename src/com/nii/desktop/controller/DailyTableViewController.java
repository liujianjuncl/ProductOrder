package com.nii.desktop.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nii.desktop.model.Daily;
import com.nii.desktop.model.DailyProcessQty;
import com.nii.desktop.model.User;
import com.nii.desktop.util.conf.DBUtil;
import com.nii.desktop.util.conf.DailyUtil;
import com.nii.desktop.util.conf.SessionUtil;
import com.nii.desktop.util.conf.PropsUtil;
import com.nii.desktop.util.conf.UserUtil;
import com.nii.desktop.util.ui.AlertUtil;
import com.nii.desktop.util.ui.ResourceLoader;
import com.nii.desktop.util.ui.UIManager;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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

    @FXML
    private TextField searchField;

    @FXML
    private Button searchBtn;

    @FXML
    private Button addDailyBtn;

    @FXML
    private Button editDailyBtn;

    @FXML
    private Button delDailyBtn;

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

        // ��ӱ������ǰ�����
        dailyDataList.clear();

        try {
            String sql = "select dailyNo, billNo, materialCode, materialName, model, planQuantity "
                    + "from dbo.t_product_daily_bill_detail where isDelete = 0 ";
            if (!"��".equals(SessionUtil.USERS.get("loginUser").getIsManager())) {
                sql = sql + " and createUser = " + SessionUtil.USERS.get("loginUser").getUserNo();
            }

            sql = sql + " order by dailyNo desc";
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                dailyNo = rs.getString("dailyNo");
                billNo = rs.getString("billNo");
                materialCode = rs.getString("materialCode");
                materialName = rs.getString("materialName");
                model = rs.getString("model");
                planQuantity = rs.getInt("planQuantity");
                dailyDataList.add(
                        new Daily(new CheckBox(), dailyNo, billNo, materialCode, materialName, model, planQuantity));
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
        planQuantityCol.setCellValueFactory(new PropertyValueFactory<Daily, String>("planQuantity"));

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
        } else {
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
                    
                    //��ʵ�����ܱ��е�ʵ����������
                    DailyProcessQty dailyProcessQty = new DailyProcessQty(daily.getBillNo(), daily.getDailyNo(),
                            daily.getPlanQty(), -daily.getResProQty1(), -daily.getResProQty2(), -daily.getResProQty3(),
                            -daily.getProQty1(), -daily.getProQty2(), -daily.getProQty3(), -daily.getProQty4(),
                            -daily.getProQty5(), -daily.getProQty6(), -daily.getProQty7(), -daily.getProQty8(),
                            -daily.getProQty9());
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
    public void queryDailyAction() {

    }

    @FXML
    public void dailySearchAction() {

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
