package com.nii.desktop.util.conf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nii.desktop.util.ui.AlertUtil;

public class WorkUtil {

    /* 校验新建作业目录信息 */
    public static boolean verifyUserInfo(String workName, String unit, String unitPrice, String status) {
        if ("".equals(workName.trim())) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("workName.notnull"));
            return false;
        }

        if ("".equals(unitPrice.trim())) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("unitPrice.notnull"));
            return false;
        }

        if (unit == null) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("unit.notnull"));
            return false;
        }

        if (status == null) {
            AlertUtil.alertInfoLater(PropsUtil.getMessage("status.notnull"));
            return false;
        }

        return true;
    }

    // 获取生产日报编号
    public static String getWorkNo() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String workNo = "000";

        try {
            String sql = "select max(workNo) as workNo from dbo.t_product_daily_work ";

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String temp = rs.getString("workNo");
                if (temp != null) {
                    workNo = temp;
                }
            }

            int seq = Integer.parseInt(workNo) + 1;
            if (seq < 10) {
                workNo = "00" + seq;
            } else if (seq >= 10 && seq < 100) {
                workNo = "0" + seq;
            } else {
                workNo = "" + seq;
            }

        } catch (Exception e) {
            Logger.getLogger(DailyUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }

        return workNo;
    }
}
