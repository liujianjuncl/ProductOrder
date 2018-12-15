package com.nii.desktop.util.conf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nii.desktop.model.Work;
import com.nii.desktop.model.WorkDetail;
import com.nii.desktop.util.ui.AlertUtil;

import javafx.scene.control.CheckBox;

public class WorkUtil {

    /* У���½���ҵĿ¼��Ϣ */
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

    // ��ȡ��������ձ����
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

    // ��ȡ�����ҵ
    public static Work getWorkByNo(String workNo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        Work work = null;

        try {
            String sql = "select * from dbo.t_product_daily_work where workNo = ? ";

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, workNo);
            rs = stmt.executeQuery();
            while (rs.next()) {
                work = new Work(rs.getString("workNo"), rs.getString("workName"), rs.getString("unit"),
                        rs.getDouble("unitPrice"), rs.getString("status"));
            }

        } catch (Exception e) {
            Logger.getLogger(DailyUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }

        return work;
    }

    // ��ȡ�����ҵ��ϸ���
    public static String getWorkDetailNo() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String dateStr = sdf.format(new Date());
        String workNo = dateStr + "000";

        try {
            String sql = "select max(workDetailNo) as workNo from dbo.t_product_daily_work_detail ";

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String temp = rs.getString("workNo");
                if (temp != null) {
                    workNo = temp;
                }
            }

            int seq = Integer.parseInt(workNo.substring(12)) + 1;
            if (seq < 10) {
                workNo = dateStr + "00" + seq;
            } else if (seq >= 10 && seq < 100) {
                workNo = dateStr + "0" + seq;
            } else {
                workNo = dateStr + seq;
            }

        } catch (Exception e) {
            Logger.getLogger(DailyUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }

        return workNo;
    }

    // ��ȡ�����ҵ��ϸ
    public static WorkDetail getWorkDetailByNo(String workDetailNo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        WorkDetail workDetail = null;

        try {
            String sql = "select * from dbo.t_product_daily_work_detail where isDelete = 0 and workdetailNo = ? ";

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, workDetailNo);
            rs = stmt.executeQuery();
            while (rs.next()) {
                workDetail = new WorkDetail(rs.getString("workDetailNo"), rs.getTimestamp("workDate"),
                        rs.getInt("status") == 1 ? "�����" : "δ���", rs.getString("workNo"), rs.getString("workName"),
                        rs.getString("unit"), rs.getDouble("unitPrice"), rs.getInt("workNum"), rs.getDouble("money"),
                        UserUtil.getUser(rs.getString("createUser")).getUserName(), rs.getTimestamp("createTime"),
                        UserUtil.getUser(rs.getString("modifyUser")) == null ? null
                                : UserUtil.getUser(rs.getString("modifyUser")).getUserName(),
                        rs.getTimestamp("modifyTime"),
                        UserUtil.getUser(rs.getString("auditor")) == null ? null
                                : UserUtil.getUser(rs.getString("auditor")).getUserName(),
                        rs.getTimestamp("auditorTime"));
            }

        } catch (Exception e) {
            Logger.getLogger(DailyUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }

        return workDetail;
    }

    public static void main(String[] args) {
        System.out.println(getWorkNo());
        System.out.println(getWorkDetailNo());
    }

}
