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

import com.nii.desktop.model.User;
import com.nii.desktop.util.conf.DBUtil;
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
public class UserTableViewController implements Initializable {

    @FXML
    private VBox userVBox;

    /* ��� */
    @FXML
    private TableView<User> userTableView;

    /* ��ҳ */
    @FXML
    private Pagination userTablePagination;

    /* ���� */
    private ObservableList<User> userDataList = FXCollections.observableArrayList();

    @FXML
    private Label userLabel;

    @FXML
    private HBox userHbox;

    /* selectCol */
    @FXML
    private TableColumn<User, CheckBox> selectCol;

    /* userNo�� */
    @FXML
    private TableColumn<User, String> userNoCol;

    /* name�� */
    @FXML
    private TableColumn<User, String> userNameCol;

    /* �Ƿ�Ƽ� */
    @FXML
    private TableColumn<User, String> isPieceworkCol;

    /* �Ƿ����Ա */
    @FXML
    private TableColumn<User, String> isManagerCol;

    /* �Ƿ���� */
    @FXML
    private TableColumn<User, String> isDisableCol;

    @FXML
    private TextField searchField;

    @FXML
    private Button addUserBtn;

    @FXML
    private Button editUserBtn;

    @FXML
    private Button delUserBtn;

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
        if (SessionUtil.CONTROLLERS.get("UserTableViewController") == null) {
            SessionUtil.CONTROLLERS.put("UserTableViewController", this);
        }

        userTableView.setRowFactory(new Callback<TableView<User>, TableRow<User>>() {
            @Override
            public TableRow<User> call(TableView<User> param) {
                TableRow<User> row = new TableRow<User>();
                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        // TODO Auto-generated method stub
                        if (event.getClickCount() == 2) {
                            User user = row.getItem();
                            modifyUserAction(user);
                        }
                    }
                });
                return row;
            }
        });
        
        // �����ǰ��¼�û����ǹ���Ա������ʾ���Ӻ�ɾ����ť
        if (!"��".equals(SessionUtil.USERS.get("loginUser").getIsManager())) {
            addUserBtn.setVisible(false);
            delUserBtn.setVisible(false);
        }

        userTablePagination.setPageCount(5);
    }

    /* ��ʼ��������� */
    private void initData() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String userNo = null;
        String userName = null;
        String isPiecework = null;
        String isManager = null;
        String isDisable = null;

        // ��ӱ������ǰ�����
        userDataList.clear();

        try {
            String sql = "select userNo, userName, isPiecework, isManager, isDisable from dbo.t_product_daily_user where isDelete = 0";
            if (!"��".equals(SessionUtil.USERS.get("loginUser").getIsManager())) {
                sql = sql + " and userNo = " + SessionUtil.USERS.get("loginUser").getUserNo();
            }
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                userNo = rs.getString("userNo");
                userName = rs.getString("userName");
                isPiecework = rs.getInt("isPiecework") == 1 ? "��" : "��";
                isManager = rs.getInt("isManager") == 1 ? "��" : "��";
                isDisable = rs.getInt("isDisable") == 1 ? "��" : "��";
                userDataList.add(new User(new CheckBox(), userNo, userName, isPiecework, isManager, isDisable));
            }

        } catch (Exception e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }
    }

    /* �½��û� */
    @FXML
    private void addUserAction() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ResourceLoader.getFxmlResource("AddUser.fxml"));

        AnchorPane addUserPane;

        try {
            addUserPane = fxmlLoader.load();

            dialogStage = new Stage();
            dialogStage.setTitle("����û�");

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(UIManager.getPrimaryStage());
            Scene scene = new Scene(addUserPane);
            dialogStage.setScene(scene);

            dialogStage.setResizable(false);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDatatoTableView() {
        selectCol.setCellValueFactory(new PropertyValueFactory<User, CheckBox>("checkbox"));
        userNoCol.setCellValueFactory(new PropertyValueFactory<User, String>("userNo"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
        isPieceworkCol.setCellValueFactory(new PropertyValueFactory<User, String>("isPiecework"));
        isManagerCol.setCellValueFactory(new PropertyValueFactory<User, String>("isManager"));
        isDisableCol.setCellValueFactory(new PropertyValueFactory<User, String>("isDisable"));

        userTableView.setItems(userDataList);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        });
    }

    /* �޸��û� */
    public void modifyUserAction(User user) {
        SessionUtil.USERS.put("editUser", user);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ResourceLoader.getFxmlResource("ModifyUser.fxml"));

        AnchorPane modifyUserPane;

        try {
            modifyUserPane = fxmlLoader.load();

            dialogStage = new Stage();
            dialogStage.setTitle("�༭�û�");

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(UIManager.getPrimaryStage());
            Scene scene = new Scene(modifyUserPane);
            dialogStage.setScene(scene);

            dialogStage.setResizable(false);
            dialogStage.showAndWait();

        } catch (IOException e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /* ɾ���û� */
    @FXML
    public void deleteUserAction() {
        if (getSelectedNum() == 0) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("comboBox.delete.noSelected"));
        } else {
            List<String> userNoList = getSelectedUserNoList();

            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                String sql = "update dbo.t_product_daily_user set isDelete = 1 where userNo = ?";
                conn = DBUtil.getConnection();
                stmt = conn.prepareStatement(sql);

                for (int i = 0; i < userNoList.size(); i++) {
                    String userNo = userNoList.get(i);
                    stmt.setString(1, userNo);
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
            ((UserTableViewController) SessionUtil.CONTROLLERS.get("UserTableViewController")).refresh();
        }
    }

    @FXML
    public void userSearchAction() {

    }

    /* ��ȡ����ѡ������ */
    public int getSelectedNum() {
        int count = 0;
        for (int i = 0; i < userDataList.size(); i++) {
            if (userDataList.get(i).getCheckbox().isSelected()) {
                count++;
            }
        }
        return count;
    }

    /* ��ȡ����ѡ�ļ�¼ */
    public User getSingleSelectedUser() {
        String userNo = null;
        for (int i = 0; i < userDataList.size(); i++) {
            if (userDataList.get(i).getCheckbox().isSelected()) {
                userNo = userDataList.get(i).getUserNo();
                break;
            }
        }

        User user = UserUtil.getUser(userNo);
        return user;
    }

    /* ��ȡ���б���ѡ���û���� */
    public List<String> getSelectedUserNoList() {
        List<String> userNoList = new ArrayList<String>();
        for (int i = 0; i < userDataList.size(); i++) {
            if (userDataList.get(i).getCheckbox().isSelected()) {
                userNoList.add(userDataList.get(i).getUserNo());
            }
        }

        return userNoList;
    }

    /* ˢ������ */
    public void refresh() {
        initData();
        addDatatoTableView();
    }

}
