package com.nii.desktop.util.conf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nii.desktop.controller.AddDailyController;
import com.nii.desktop.model.Daily;
import com.nii.desktop.model.DailyProcessTotalQty;

public class DailyUtil {

    // 将生产任务表中的数据同步到生产任务执行汇总表中
    public static void addProductDailyTotal(String billNo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql1 = "select c.FBillNo as billNo, icc.FNumber as materialCode, icc.FName as materialName, icc.FModel as model, "
                    + "c.FQty as planQuantity, item1.FName as resProcess1, c.FHeadSelfJ01104 as resProcessPrice1, 0, "
                    + "item2.FName as resProcess2, c.FHeadSelfJ01106 as resProcessPrice2, 0, "
                    + "item3.FName as resProcess3, c.FHeadSelfJ01108 as resProcessPrice3, 0, "
                    + "(case when charindex('*', c.FHeadSelfJ0185) > 0 then null else c.FHeadSelfJ0185 end) as process1,"
                    + "(case when charindex('*', c.FHeadSelfJ0186) > 0 then null else c.FHeadSelfJ0186 end) as processPrice1, 0, "
                    + "(case when charindex('*', c.FHeadSelfJ0187) > 0 then null else c.FHeadSelfJ0187 end) as process2, "
                    + "(case when charindex('*', c.FHeadSelfJ0188) > 0 then null else c.FHeadSelfJ0188 end) as processPrice2, 0, "
                    + "(case when charindex('*', c.FHeadSelfJ0189) > 0 then null else c.FHeadSelfJ0189 end) as process3,"
                    + "(case when charindex('*', c.FHeadSelfJ0190) > 0 then null else c.FHeadSelfJ0190 end) as processPrice3, 0, "
                    + "(case when charindex('*', c.FHeadSelfJ0191) > 0 then null else c.FHeadSelfJ0191 end) as process4,"
                    + "(case when charindex('*', c.FHeadSelfJ0192) > 0 then null else c.FHeadSelfJ0192 end) as processPrice4, 0, "
                    + "(case when charindex('*', c.FHeadSelfJ0193) > 0 then null else c.FHeadSelfJ0193 end) as process5,"
                    + "(case when charindex('*', c.FHeadSelfJ0194) > 0 then null else c.FHeadSelfJ0194 end) as processPrice5, 0, "
                    + "(case when charindex('*', c.FHeadSelfJ0195) > 0 then null else c.FHeadSelfJ0195 end) as process6,"
                    + "(case when charindex('*', c.FHeadSelfJ0196) > 0 then null else c.FHeadSelfJ0196 end) as processPrice6, 0, "
                    + "(case when charindex('*', c.FHeadSelfJ0197) > 0 then null else c.FHeadSelfJ0197 end) as process7,"
                    + "(case when charindex('*', c.FHeadSelfJ0198) > 0 then null else c.FHeadSelfJ0198 end) as processPrice7, 0, "
                    + "(case when charindex('*', c.FHeadSelfJ0199) > 0 then null else c.FHeadSelfJ0199 end) as process8,"
                    + "(case when charindex('*', c.FHeadSelfJ01100) > 0 then null else c.FHeadSelfJ01100 end) as processPrice8, 0, "
                    + "(case when charindex('*', c.FHeadSelfJ01101) > 0 then null else c.FHeadSelfJ01101 end) as process9,"
                    + "(case when charindex('*', c.FHeadSelfJ01102) > 0 then null else c.FHeadSelfJ01102 end) as processPrice9, 0 "
                    + "from dbo.ICMO c left join dbo.t_ICItemCore icc on c.FItemID = icc.FItemID "
                    + "left join dbo.t_Item item1 on c.FHeadSelfJ01103 = item1.FitemID "
                    + "left join dbo.t_Item item2 on c.FHeadSelfJ01105 = item2.FitemID "
                    + "left join dbo.t_Item item3 on c.FHeadSelfJ01107 = item3.FitemID " + "where c.FBillNo = ? ";

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql1);
            stmt.setString(1, billNo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String sql = "insert into dbo.t_product_daily_bill_total  " + sql1;
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, billNo);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            Logger.getLogger(DailyUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }
    }

    // 获取所有工序累计实作数量值
    public static DailyProcessTotalQty getProcessTotalQty(String billNo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        DailyProcessTotalQty dailyProcessTotalQty = null;

        try {
            String sql = "select * from dbo.t_product_daily_bill_total t where t.billNo = ? ";

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, billNo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int playQty = rs.getInt("planQuantity");
                int resProcessTotalQty1 = rs.getInt("resProcessTotalQty1");
                int resProcessTotalQty2 = rs.getInt("resProcessTotalQty2");
                int resProcessTotalQty3 = rs.getInt("resProcessTotalQty3");
                int processTotalQty1 = rs.getInt("processTotalQty1");
                int processTotalQty2 = rs.getInt("processTotalQty2");
                int processTotalQty3 = rs.getInt("processTotalQty3");
                int processTotalQty4 = rs.getInt("processTotalQty4");
                int processTotalQty5 = rs.getInt("processTotalQty5");
                int processTotalQty6 = rs.getInt("processTotalQty6");
                int processTotalQty7 = rs.getInt("processTotalQty7");
                int processTotalQty8 = rs.getInt("processTotalQty8");
                int processTotalQty9 = rs.getInt("processTotalQty9");

                dailyProcessTotalQty = new DailyProcessTotalQty(billNo, playQty, resProcessTotalQty1,
                        resProcessTotalQty2, resProcessTotalQty3, processTotalQty1, processTotalQty2, processTotalQty3,
                        processTotalQty4, processTotalQty5, processTotalQty6, processTotalQty7, processTotalQty8,
                        processTotalQty9);
            }
        } catch (Exception e) {
            Logger.getLogger(DailyUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }

        return dailyProcessTotalQty;
    }

    // 如果存在改制工序实作数量，则不允许输入工序实作数量
    public static String verifyIsNull(String resProQty1, String resProQty2, String resProQty3, String proQty1,
            String proQty2, String proQty3, String proQty4, String proQty5, String proQty6, String proQty7,
            String proQty8, String proQty9) {

        if ("".equals(resProQty1) && "".equals(resProQty2) && "".equals(resProQty3) && "".equals(proQty1)
                && "".equals(proQty2) && "".equals(proQty3) && "".equals(proQty4) && "".equals(proQty5)
                && "".equals(proQty6) && "".equals(proQty7) && "".equals(proQty8) && "".equals(proQty9)) {
            return PropsUtil.getMessage("processQty.isnull");
        } else if ((!"".equals(resProQty1) || !"".equals(resProQty2) || !"".equals(resProQty3)) && (!"".equals(proQty1)
                || !"".equals(proQty2) || !"".equals(proQty3) || !"".equals(proQty4) || !"".equals(proQty5)
                || !"".equals(proQty6) || !"".equals(proQty7) || !"".equals(proQty8) || !"".equals(proQty9))) {
            return PropsUtil.getMessage("resProQty.isnot.null");
        }
        return "OK";
    }

    public static String verifyProcess(int[] resTotalQty, int[] totalQty, int playQty) {
        // 前一个改制工序累计实作数量必须大于或等于后一个改制工序累计实作数量
        for (int i = 0; i < resTotalQty.length; i++) {
            for (int j = i + 1; j < resTotalQty.length; j++) {
                if (resTotalQty[i] < resTotalQty[j]) {
                    return PropsUtil.getMessage("resProcessQty.asc") + "（" + PropsUtil.getMessage("resProcess.name")
                            + (i + 1) + PropsUtil.getMessage("total.qty") + " < "
                            + PropsUtil.getMessage("resProcess.name") + (j + 1) + PropsUtil.getMessage("total.qty")
                            + "）";
                }
            }
        }

        // 改制工序累计实作数量不能大于生产任务单计划生产数量
        for (int i = 0; i < resTotalQty.length; i++) {
            if (resTotalQty[i] > playQty) {
                return PropsUtil.getMessage("resProcess.name") + (i + 1) + PropsUtil.getMessage("not.greater.than");
            }
        }

        // 前一个工序累计实作数量必须大于或等于后一个工序累计实作数量
        for (int i = 0; i < totalQty.length; i++) {
            for (int j = i + 1; j < totalQty.length; j++) {
                if (totalQty[i] < totalQty[j]) {
                    return PropsUtil.getMessage("processQty.asc") + "（" + PropsUtil.getMessage("process.name") + (i + 1)
                            + PropsUtil.getMessage("total.qty") + " < " + PropsUtil.getMessage("process.name") + (j + 1)
                            + PropsUtil.getMessage("total.qty") + "）";
                }
            }
        }

        // 工序累计实作数量不能大于生产任务单计划生产数量
        for (int i = 0; i < totalQty.length; i++) {
            if (totalQty[i] > playQty) {
                return PropsUtil.getMessage("process.name") + (i + 1) + PropsUtil.getMessage("not.greater.than");
            }
        }

        return "OK";
    }

    // 根据生产任务单编号获取该生产任务单中的日报序号
    public static int getDailyDetailSeq(String billNo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        int maxSeq = 0;

        try {
            String sql = "select MAX(sequence) as sequence from dbo.t_product_daily_bill_detail where billNo = ? ";

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, billNo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                maxSeq = rs.getInt("sequence");
            }

        } catch (Exception e) {
            Logger.getLogger(DailyUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }

        return maxSeq + 1;
    }

    // 获取生产日报编号
    public static String getDailyNo() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String dateStr = sdf.format(new Date());
        String dailyNo = dateStr + "000";

        try {
            String sql = "select max(dailyNo) as dailyNo from dbo.t_product_daily_bill_detail ";

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String temp = rs.getString("dailyNo");
                if (temp != null) {
                    dailyNo = temp;
                }
            }

            int seq = Integer.parseInt(dailyNo.substring(12)) + 1;
            if (seq < 10) {
                dailyNo = dateStr + "00" + seq;
            } else if (seq >= 10 && seq < 100) {
                dailyNo = dateStr + "0" + seq;
            } else {
                dailyNo = dateStr + seq;
            }

        } catch (Exception e) {
            Logger.getLogger(DailyUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }

        return dailyNo;
    }

    // 添加日报
    public static boolean addDaily(Daily daily) {
        Connection conn = null;
        PreparedStatement stmt = null;

//        addProductDailyTotal("制造-CB车间180813427");

        try {
            // 插入日报数据
            String sql = "insert into dbo.t_product_daily_bill_detail values(?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, daily.getDailyNo());
            stmt.setString(2, daily.getBillNo());
            stmt.setString(3, daily.getMaterialCode());
            stmt.setString(4, daily.getMaterialName());
            stmt.setString(5, daily.getModel());
            stmt.setInt(6, daily.getPlanQuantity());
            stmt.setString(7, daily.getResProcess1());
            stmt.setDouble(8, daily.getResProcessPrice1());
            stmt.setInt(9, daily.getResProcessQuantity1());
            stmt.setString(10, daily.getResProcess2());
            stmt.setDouble(11, daily.getResProcessPrice2());
            stmt.setInt(12, daily.getResProcessQuantity2());
            stmt.setString(13, daily.getResProcess3());
            stmt.setDouble(14, daily.getResProcessPrice3());
            stmt.setInt(15, daily.getResProcessQuantity3());
            stmt.setString(16, daily.getProcess1());
            stmt.setDouble(17, daily.getProcessPrice2());
            stmt.setInt(18, daily.getProcessQuantity1());
            stmt.setString(19, daily.getProcess2());
            stmt.setDouble(20, daily.getProcessPrice2());
            stmt.setInt(21, daily.getProcessQuantity2());
            stmt.setString(22, daily.getProcess3());
            stmt.setDouble(23, daily.getProcessPrice3());
            stmt.setInt(24, daily.getProcessQuantity3());
            stmt.setString(25, daily.getProcess4());
            stmt.setDouble(26, daily.getProcessPrice4());
            stmt.setInt(27, daily.getProcessQuantity4());
            stmt.setString(28, daily.getProcess5());
            stmt.setDouble(29, daily.getProcessPrice5());
            stmt.setInt(30, daily.getProcessQuantity5());
            stmt.setString(31, daily.getProcess6());
            stmt.setDouble(32, daily.getProcessPrice6());
            stmt.setInt(33, daily.getProcessQuantity6());
            stmt.setString(34, daily.getProcess7());
            stmt.setDouble(35, daily.getProcessPrice7());
            stmt.setInt(36, daily.getProcessQuantity7());
            stmt.setString(37, daily.getProcess8());
            stmt.setDouble(38, daily.getProcessPrice8());
            stmt.setInt(39, daily.getProcessQuantity8());
            stmt.setString(40, daily.getProcess9());
            stmt.setDouble(41, daily.getProcessPrice9());
            stmt.setInt(42, daily.getProcessQuantity9());
            stmt.setString(43, daily.getCreateUser());
            stmt.setTimestamp(44, daily.getCreateTime());
            stmt.setInt(45, daily.getIsPiecework());
            stmt.setInt(46, daily.getIsDelete());
            stmt.setInt(47, daily.getSequence());

            stmt.executeUpdate();

            // 修改生产日报汇总表数据
            updateDailyTotalQty(daily);

        } catch (Exception e) {
            Logger.getLogger(AddDailyController.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {
            DBUtil.release(conn, stmt);
        }

        return true;
    }

    // 更新累计实作数量汇总表
    public static void updateDailyTotalQty(Daily daily) {
        DailyProcessTotalQty d = getProcessTotalQty(daily.getBillNo());

        int resProTotalQty1 = d.getResProcessTotalQty1() + daily.getResProcessQuantity1();
        int resProTotalQty2 = d.getResProcessTotalQty2() + daily.getResProcessQuantity2();
        int resProTotalQty3 = d.getResProcessTotalQty3() + daily.getResProcessQuantity3();
        int proTotalQty1 = d.getProcessTotalQty1() + daily.getProcessQuantity1();
        int proTotalQty2 = d.getProcessTotalQty2() + daily.getProcessQuantity2();
        int proTotalQty3 = d.getProcessTotalQty3() + daily.getProcessQuantity3();
        int proTotalQty4 = d.getProcessTotalQty4() + daily.getProcessQuantity4();
        int proTotalQty5 = d.getProcessTotalQty5() + daily.getProcessQuantity5();
        int proTotalQty6 = d.getProcessTotalQty6() + daily.getProcessQuantity6();
        int proTotalQty7 = d.getProcessTotalQty7() + daily.getProcessQuantity7();
        int proTotalQty8 = d.getProcessTotalQty8() + daily.getProcessQuantity8();
        int proTotalQty9 = d.getProcessTotalQty9() + daily.getProcessQuantity9();

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // 更新日报汇总表工序实作数量
            String sql = "update dbo.t_product_daily_bill_total set resProcessTotalQty1 = ?, resProcessTotalQty2 = ?, resProcessTotalQty3 = ?, "
                    + "processTotalQty1 = ?, processTotalQty2 = ?, processTotalQty3 = ?, processTotalQty4 = ?, processTotalQty5 = ?, "
                    + "processTotalQty6 = ?, processTotalQty7 = ?, processTotalQty8 = ?, processTotalQty9 = ? "
                    + "where billNo = ? ";

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, resProTotalQty1);
            stmt.setInt(2, resProTotalQty2);
            stmt.setInt(3, resProTotalQty3);
            stmt.setInt(4, proTotalQty1);
            stmt.setInt(5, proTotalQty2);
            stmt.setInt(6, proTotalQty3);
            stmt.setInt(7, proTotalQty4);
            stmt.setInt(8, proTotalQty5);
            stmt.setInt(9, proTotalQty6);
            stmt.setInt(10, proTotalQty7);
            stmt.setInt(11, proTotalQty8);
            stmt.setInt(12, proTotalQty9);
            stmt.setString(13, daily.getBillNo());

            stmt.executeUpdate();

        } catch (Exception e) {
            Logger.getLogger(AddDailyController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt);
        }
    }

    public static void main(String[] args) {
//        int maxSeq = getDailyDetailSeq("制造-VP车间180813428");
//        System.out.println(maxSeq);

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
//        System.out.println(sdf.format(new Date()));
//        String str = "201810131204001";
//        System.out.println(str.substring(12));
//        System.out.println(getDailyNo());

        System.out.println(getProcessTotalQty("123"));
    }
}
