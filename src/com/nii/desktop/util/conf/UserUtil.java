package com.nii.desktop.util.conf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nii.desktop.model.User;
import com.nii.desktop.util.ui.AlertUtil;

public class UserUtil {

    //或最大用户编号
    public static String getMaxUserNo() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String userNoRes = null;

        String sql = "select MAX(userNo) as userNo from dbo.t_product_daily_user";

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            int userNum = 0;
            while (rs.next()) {
                String userNo = rs.getString("userNo");
                if (userNo != null) {
                    userNum = Integer.parseInt(userNo);
                }
            }
            userNum = userNum + 1;

            if (userNum < 10) {
                userNoRes = "00000" + userNum;
            } else if (userNum >= 10 && userNum < 100) {
                userNoRes = "0000" + userNum;
            } else if (userNum >= 100 && userNum < 1000) {
                userNoRes = "000" + userNum;
            } else if (userNum >= 1000 && userNum < 10000) {
                userNoRes = "00" + userNum;
            } else if (userNum >= 10000 && userNum < 100000) {
                userNoRes = "0" + userNum;
            } else {
                userNoRes = Integer.toString(userNum);
            }
        } catch (Exception e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }

        return userNoRes;
    }

    /* 根据userNo获取用户 */
    public static User getUser(String userNumber) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String userNo = null;
        String userName = null;
        String password = null;
        String isPiecework = null;
        String isManager = null;
        String isDisable = null;

        User user = null;

        String sql = "select * from dbo.t_product_daily_user where userNo = ? and isDelete = 0";
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, userNumber);
            rs = stmt.executeQuery();

            if (rs.next()) {
                userNo = rs.getString("userNo");
                userName = rs.getString("userName");
                password = rs.getString("password");
                isPiecework = rs.getInt("isPiecework") == 1 ? "是" : "否";
                isManager = rs.getInt("isManager") == 1 ? "是" : "否";
                isDisable = rs.getInt("isDisable") == 1 ? "是" : "否";
                user = new User(userNo, userName, password, isPiecework, isManager, isDisable);
            }
        } catch (Exception e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }

        return user;
    }

    /* 校验新建用户信息 */
    public static boolean verifyUserInfo(String userName, String password, String isPiecework, String isManager) {
        if ("".equals(userName.trim())) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("userName.notnull"));
            return false;
        }

        if ("".equals(password.trim())) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("password.notnull"));
            return false;
        }

        if (userName.trim().length() > 10) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("userName.length"));
            return false;
        }

        if (password.trim().length() < 6) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("password.length.min"));
            return false;
        }

        if (password.trim().length() > 18) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("password.length.max"));
            return false;
        }

        if (isPiecework == null) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("isPiecework.notnull"));
            return false;
        }

        if (isManager == null) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("isManager.notnull"));
            return false;
        }

        return true;
    }

    /* 校验新建用户信息 */
    public static boolean verifyUserInfo(String userName, String password, String isPiecework, String isManager,
            String isDisable) {
        if ("".equals(userName.trim())) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("userName.notnull"));
            return false;
        }

        if ("".equals(password.trim())) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("password.notnull"));
            return false;
        }

        if (userName.trim().length() > 10) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("userName.length"));
            return false;
        }

        if (password.trim().length() < 6) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("password.length.min"));
            return false;
        }

        if (password.trim().length() > 18) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("password.length.max"));
            return false;
        }

        if (isPiecework == null) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("isPiecework.notnull"));
            return false;
        }

        if (isManager == null) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("isManager.notnull"));
            return false;
        }

        if (isDisable == null) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("isDisable.notnull"));
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        getMaxUserNo();
//        Date now = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss.S");
//        System.out.println(now);
//        System.out.println(now.getTime());
//        System.out.println(new Timestamp(now.getTime()));
//        System.out.println("你好".length());
//        System.out.println(getUser("00000001"));
    }
}
