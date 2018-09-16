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
import com.nii.desktop.util.conf.DataManager;
import com.nii.desktop.util.conf.PropertiesUtil;
import com.nii.desktop.util.conf.UserUtil;
import com.nii.desktop.util.ui.AlertUtil;
import com.nii.desktop.util.ui.ResourceLoader;
import com.nii.desktop.util.ui.UIManager;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by ljj on 2018/1/7.
 */
public class UserTableViewController implements Initializable {

    @FXML
    private VBox userVBox;

    /* 表格 */
    @FXML
    private TableView<User> userTableView;

    /* 数据 */
    private ObservableList<User> userDataList = FXCollections.observableArrayList();

    @FXML
    private Label userLabel;

    @FXML
    private HBox userHbox;

    /* selectCol */
    @FXML
    private TableColumn<User, CheckBox> selectCol;

    /* userNo列 */
    @FXML
    private TableColumn<User, String> userNoCol;

    /* name列 */
    @FXML
    private TableColumn<User, String> userNameCol;

    /* 是否计件 */
    @FXML
    private TableColumn<User, String> isPieceworkCol;

    /* 是否管理员 */
    @FXML
    private TableColumn<User, String> isManagerCol;

    /* 是否禁用 */
    @FXML
    private TableColumn<User, String> isDisableCol;

    @FXML
    private TextField searchField;
    
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
        DataManager.CONTROLLERS.put("UserTableViewController", this);
    }

    /* 初始化表格数据 */
    private void initData() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String userNo = null;
        String userName = null;
        String isPiecework = null;
        String isManager = null;
        String isDisable = null;

        try {
            String sql = "select userNo, userName, isPiecework, isManager, isDisable from dbo.t_product_daily_user where isDelete = 0";
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                userNo = rs.getString("userNo");
                userName = rs.getString("userName");
                isPiecework = rs.getInt("isPiecework") == 1 ? "是" : "否";
                isManager = rs.getInt("isManager") == 1 ? "是" : "否";
                isDisable = rs.getInt("isDisable") == 1 ? "是" : "否";
                userDataList.add(new User(new CheckBox(), userNo, userName, isPiecework, isManager, isDisable));
            }

        } catch (Exception e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }
    }

    @FXML
    private void addUserAction() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ResourceLoader.getFxmlResource("AddUser.fxml"));

        AnchorPane addUserPane;

        try {
            addUserPane = fxmlLoader.load();

            dialogStage = new Stage();
            dialogStage.setTitle("添加用户");

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(UIManager.getPrimaryStage());
            Scene scene = new Scene(addUserPane);
            dialogStage.setScene(scene);

            dialogStage.setResizable(false);
            dialogStage.showAndWait();
            AddUserController.setTableViewController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDatatoTableView() {
        userDataList.clear();
        
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

    /* 修改用户窗口 */
    @FXML
    public void modifyUserAction() {
        if (getSelectedNum() == 0) {
            AlertUtil.alertInfoLater(PropertiesUtil.getStringValue("comboBox.modify.noSelected"));
        } else if (getSelectedNum() > 1) {
            AlertUtil.alertInfoLater(PropertiesUtil.getStringValue("comboBox.selected.count"));
        } else {
            User user = getSingleSelectedUser();
            DataManager.USERS.put("editUser", user);
            
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(ResourceLoader.getFxmlResource("ModifyUser.fxml"));

            AnchorPane modifyUserPane;

            try {
                modifyUserPane = fxmlLoader.load();

                dialogStage = new Stage();
                dialogStage.setTitle("编辑用户");

                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(UIManager.getPrimaryStage());
                Scene scene = new Scene(modifyUserPane);
                dialogStage.setScene(scene);

                dialogStage.setResizable(false);
                dialogStage.showAndWait();
                AddUserController.setTableViewController(this);

            } catch (IOException e) {
                Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    @FXML
    public void deleteUserAction() {
        if (getSelectedNum() == 0) {
            AlertUtil.alertInfoLater(PropertiesUtil.getStringValue("comboBox.delete.noSelected"));
        } else {
            List<String> userNoList = getSelectedUserNoList();
            
            Connection conn = null;
            PreparedStatement stmt = null;
            
            try {
                String sql = "update dbo.t_product_daily_user set isDelete = 1 where userNo = ?";
                conn = DBUtil.getConnection();
                stmt = conn.prepareStatement(sql);
                
                for(int i = 0; i < userNoList.size(); i++) {
                    String userNo = userNoList.get(i);
                    stmt.setString(1, userNo);
                    stmt.execute();
                }
                
            } catch(Exception e) {
                Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
                return;
            } finally {
                DBUtil.release(conn, stmt);
            }
            AlertUtil.alertInfoLater(PropertiesUtil.getStringValue("user.delete.success"));
            // 删除完成刷新数据
            ((UserTableViewController) DataManager.CONTROLLERS.get("UserTableViewController")).refresh();
        }
    }
    
    @FXML
    public void userSearchAction() {
        
    }

    /* 获取被勾选的数量 */
    public int getSelectedNum() {
        int count = 0;
        for (int i = 0; i < userDataList.size(); i++) {
            if (userDataList.get(i).getCheckbox().isSelected()) {
                count++;
            }
        }
        return count;
    }

    /* 获取被勾选的记录状态 */
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
    
    /* 获取所有被勾选的用户编号 */
    public List<String> getSelectedUserNoList() {
        List<String> userNoList = new ArrayList<String>();
        for (int i = 0; i < userDataList.size(); i++) {
            if (userDataList.get(i).getCheckbox().isSelected()) {
                userNoList.add(userDataList.get(i).getUserNo());
            }
        }
        
        return userNoList;
    }
    
    /*刷新数据*/
    public void refresh() {
        initData();
        addDatatoTableView();
    }

}
