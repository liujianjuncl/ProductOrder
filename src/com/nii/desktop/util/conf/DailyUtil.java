package com.nii.desktop.util.conf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nii.desktop.controller.AddDailyController;
import com.nii.desktop.model.Daily;
import com.nii.desktop.model.DailyProcessQty;
import com.nii.desktop.util.ui.AlertUtil;

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
                    + "(case when charindex('*', c.FHeadSelfJ0196) > 0 then null else c.FHeadSelfJ0196 end) as processPrice6, 0 "
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
    public static DailyProcessQty getProcessTotalQty(String billNo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        DailyProcessQty dailyProcessQty = null;

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

                dailyProcessQty = new DailyProcessQty(billNo, null, null, playQty, resProcessTotalQty1,
                        resProcessTotalQty2, resProcessTotalQty3, processTotalQty1, processTotalQty2, processTotalQty3,
                        processTotalQty4, processTotalQty5, processTotalQty6);
            }
        } catch (Exception e) {
            Logger.getLogger(DailyUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }

        return dailyProcessQty;
    }

    // 如果存在改制工序，则不允许输入工序实作数量
    public static String verifyIsNull(int resProQty1, int resProQty2, int resProQty3, int proQty1, int proQty2,
            int proQty3, int proQty4, int proQty5, int proQty6) {

        if (resProQty1 == 0 && resProQty2 == 0 && resProQty3 == 0 && proQty1 == 0 && proQty2 == 0 && proQty3 == 0
                && proQty4 == 0 && proQty5 == 0 && proQty6 == 0) {
            return PropsUtil.getMessage("processQty.isnull");
        } else if ((resProQty1 != 0 || resProQty2 != 0 || resProQty3 != 0)
                && (proQty1 != 0 || proQty2 != 0 || proQty3 != 0 || proQty4 != 0 || proQty5 != 0 || proQty6 != 0)) {
            return PropsUtil.getMessage("resProQty.isnot.null");
        }
        return "OK";
    }

    // 校验改制工序及工序的是做数量逻辑
    public static String verifyProcessQty(int[] resTotalQty, int[] totalQty, int playQty) {
        // 改制工序累计实作数量不能大于生产任务单计划生产数量
        for (int i = 0; i < resTotalQty.length; i++) {
            if (resTotalQty[i] > playQty) {
                return PropsUtil.getMessage("resProcess.name") + (i + 1) + PropsUtil.getMessage("not.greater.than");
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
    public static boolean addDaily(DailyProcessQty dpq, Daily daily) {
        Connection conn = null;
        PreparedStatement stmt = null;

//        addProductDailyTotal("制造-CB车间180813427");

        try {
            // 插入日报数据
            String sql = "insert into dbo.t_product_daily_bill_detail values(?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, daily.getDailyNo());
            stmt.setString(2, daily.getBillNo());
            stmt.setString(3, daily.getMaterialCode());
            stmt.setString(4, daily.getMaterialName());
            stmt.setString(5, daily.getModel());
            stmt.setInt(6, daily.getPlanQty());
            stmt.setDate(7, DateUtil.localDateToSqlDate(daily.getProDate()));
            stmt.setString(8, daily.getResPro1());
            stmt.setDouble(9, daily.getResProPrice1());
            stmt.setInt(10, daily.getResProQty1());
            stmt.setString(11, daily.getResPro2());
            stmt.setDouble(12, daily.getResProPrice2());
            stmt.setInt(13, daily.getResProQty2());
            stmt.setString(14, daily.getResPro3());
            stmt.setDouble(15, daily.getResProPrice3());
            stmt.setInt(16, daily.getResProQty3());
            stmt.setString(17, daily.getPro1());
            stmt.setDouble(18, daily.getProPrice2());
            stmt.setInt(19, daily.getProQty1());
            stmt.setString(20, daily.getPro2());
            stmt.setDouble(21, daily.getProPrice2());
            stmt.setInt(22, daily.getProQty2());
            stmt.setString(23, daily.getPro3());
            stmt.setDouble(24, daily.getProPrice3());
            stmt.setInt(25, daily.getProQty3());
            stmt.setString(26, daily.getPro4());
            stmt.setDouble(27, daily.getProPrice4());
            stmt.setInt(28, daily.getProQty4());
            stmt.setString(29, daily.getPro5());
            stmt.setDouble(30, daily.getProPrice5());
            stmt.setInt(31, daily.getProQty5());
            stmt.setString(32, daily.getPro6());
            stmt.setDouble(33, daily.getProPrice6());
            stmt.setInt(34, daily.getProQty6());
            stmt.setString(35, daily.getCreateUser());
            stmt.setTimestamp(36, daily.getCreateTime());
            stmt.setInt(37, daily.getIsPiecework());
            stmt.setInt(38, daily.getIsDelete());
            stmt.setInt(39, daily.getSequence());

            DailyProcessQty dailyProcessQty = new DailyProcessQty(daily.getBillNo(), daily.getDailyNo(),
                    daily.getProDate(), daily.getPlanQty(), dpq.getResProQty1() + daily.getResProQty1(),
                    dpq.getResProQty2() + daily.getResProQty2(), dpq.getResProQty3() + daily.getResProQty3(),
                    dpq.getProQty1() + daily.getProQty1(), dpq.getProQty2() + daily.getProQty2(),
                    dpq.getProQty3() + daily.getProQty3(), dpq.getProQty4() + daily.getProQty4(),
                    dpq.getProQty5() + daily.getProQty5(), dpq.getProQty6() + daily.getProQty6());

            stmt.executeUpdate();

            // 修改生产日报汇总表数据
            updateDailyTotalQty(dailyProcessQty);

        } catch (Exception e) {
            Logger.getLogger(AddDailyController.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {
            DBUtil.release(conn, stmt);
        }

        return true;
    }

    // 修改日报
    public static boolean modifyDaily(DailyProcessQty dailyProcessQty, Daily oldDaily, LocalDate productDate) {
        Connection conn = null;
        PreparedStatement stmt = null;

        // 获取当前修改日报的实作汇总数量
        DailyProcessQty oldDpq = DailyUtil.getProcessTotalQty(oldDaily.getBillNo());

        try {
            // 修改日报数据
            String sql = "update dbo.t_product_daily_bill_detail set productDate = ?, resProcessQty1 = ?, "
                    + "resProcessQty2 = ?, resProcessQty3 = ?, processQty1 = ?, processQty2 = ?, "
                    + "processQty3 = ?, processQty4 = ?, processQty5 = ?, processQty6 = ?, "
                    + "productDate = ?, modifyUser = ?, modifyTime = ? where dailyNo = ? and isDelete = 0 ";

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setTimestamp(1, new Timestamp(DateUtil.localDateToDate(dailyProcessQty.getProductDate()).getTime()));
            stmt.setInt(2, dailyProcessQty.getResProQty1());
            stmt.setInt(3, dailyProcessQty.getResProQty2());
            stmt.setInt(4, dailyProcessQty.getResProQty3());
            stmt.setInt(5, dailyProcessQty.getProQty1());
            stmt.setInt(6, dailyProcessQty.getProQty2());
            stmt.setInt(7, dailyProcessQty.getProQty3());
            stmt.setInt(8, dailyProcessQty.getProQty4());
            stmt.setInt(9, dailyProcessQty.getProQty5());
            stmt.setInt(10, dailyProcessQty.getProQty6());
            stmt.setDate(11, DateUtil.localDateToSqlDate(productDate));
            stmt.setString(12, SessionUtil.USERS.get("loginUser").getUserNo());
            stmt.setTimestamp(13, new Timestamp(new Date().getTime()));
            stmt.setString(14, dailyProcessQty.getDailyNo());
            stmt.executeUpdate();

            // 修改生产日报汇总表数据：汇总表中原实作数量+本次修改的实作数量-修改前的实作数量
            DailyProcessQty d = new DailyProcessQty(dailyProcessQty.getBillNo(), dailyProcessQty.getDailyNo(),
                    dailyProcessQty.getProductDate(), dailyProcessQty.getPlanQty(),
                    oldDpq.getResProQty1() + dailyProcessQty.getResProQty1() - oldDaily.getResProQty1(),
                    oldDpq.getResProQty2() + dailyProcessQty.getResProQty2() - oldDaily.getResProQty2(),
                    oldDpq.getResProQty3() + dailyProcessQty.getResProQty3() - oldDaily.getResProQty3(),
                    oldDpq.getProQty1() + dailyProcessQty.getProQty1() - oldDaily.getProQty1(),
                    oldDpq.getProQty2() + dailyProcessQty.getProQty2() - oldDaily.getProQty2(),
                    oldDpq.getProQty3() + dailyProcessQty.getProQty3() - oldDaily.getProQty3(),
                    oldDpq.getProQty4() + dailyProcessQty.getProQty4() - oldDaily.getProQty4(),
                    oldDpq.getProQty5() + dailyProcessQty.getProQty5() - oldDaily.getProQty5(),
                    oldDpq.getProQty6() + dailyProcessQty.getProQty6() - oldDaily.getProQty6());

            updateDailyTotalQty(d);

        } catch (Exception e) {
            Logger.getLogger(AddDailyController.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {
            DBUtil.release(conn, stmt);
        }

        return true;
    }

    // 更新累计实作数量汇总表
    public static void updateDailyTotalQty(DailyProcessQty dpq) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // 更新日报汇总表工序实作数量
            String sql = "update dbo.t_product_daily_bill_total set resProcessTotalQty1 = ?, resProcessTotalQty2 = ?, resProcessTotalQty3 = ?, "
                    + "processTotalQty1 = ?, processTotalQty2 = ?, processTotalQty3 = ?, processTotalQty4 = ?, processTotalQty5 = ?, "
                    + "processTotalQty6 = ? " + "where billNo = ? ";

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, dpq.getResProQty1());
            stmt.setInt(2, dpq.getResProQty2());
            stmt.setInt(3, dpq.getResProQty3());
            stmt.setInt(4, dpq.getProQty1());
            stmt.setInt(5, dpq.getProQty2());
            stmt.setInt(6, dpq.getProQty3());
            stmt.setInt(7, dpq.getProQty4());
            stmt.setInt(8, dpq.getProQty5());
            stmt.setInt(9, dpq.getProQty6());
            stmt.setString(10, dpq.getBillNo());

            stmt.executeUpdate();

        } catch (Exception e) {
            Logger.getLogger(AddDailyController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt);
        }
    }

    /* 根据dailyNo获取日报 */
    public static Daily getDailyByNo(String dailyNumber) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String dailyNo = null;
        String billNo = null;
        String materialCode = null;
        String materialName = null;
        String model = null;
        LocalDate productDate = null;
        int planQuantity = 0;

        Daily daily = null;

        String sql = "select dailyNo, billNo, materialCode, materialName, model, planQuantity, productDate "
                + " from dbo.t_product_daily_bill_detail where dailyNo = ? and isDelete = 0";
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, dailyNumber);
            rs = stmt.executeQuery();

            if (rs.next()) {
                dailyNo = rs.getString("dailyNo");
                billNo = rs.getString("billNo");
                materialCode = rs.getString("materialCode");
                materialName = rs.getString("materialName");
                model = rs.getString("model");
                planQuantity = rs.getInt("planQuantity");
                productDate = DateUtil.dateToLocalDate(rs.getDate("productDate"));
                daily = new Daily(dailyNo, billNo, materialCode, materialName, model, planQuantity, productDate);
            }
        } catch (Exception e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }

        return daily;
    }

    /* 根据dailyNo获取日报 */
    public static Daily getDailyByUserNo(String userNo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String dailyNo = null;
        String billNo = null;
        String materialCode = null;
        String materialName = null;
        String model = null;
        LocalDate productDate = null;
        int planQuantity = 0;

        Daily daily = null;

        String sql = "select dailyNo, billNo, materialCode, materialName, model, planQuantity, productDate "
                + " from dbo.t_product_daily_bill_detail where createUser = ? and isDelete = 0";
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, userNo);
            rs = stmt.executeQuery();

            if (rs.next()) {
                dailyNo = rs.getString("dailyNo");
                billNo = rs.getString("billNo");
                materialCode = rs.getString("materialCode");
                materialName = rs.getString("materialName");
                model = rs.getString("model");
                planQuantity = rs.getInt("planQuantity");
                productDate = DateUtil.dateToLocalDate(rs.getDate("productDate"));
                daily = new Daily(dailyNo, billNo, materialCode, materialName, model, planQuantity, productDate);
            }
        } catch (Exception e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }

        return daily;
    }

    /* 根据dailyNo获取日报全部信息 */
    public static Daily getDailyByNoAll(String dailyNo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        Daily daily = null;

        String sql = "select * from dbo.t_product_daily_bill_detail where dailyNo = ? and isDelete = 0";
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, dailyNo);
            rs = stmt.executeQuery();

            if (rs.next()) {
                daily = new Daily(dailyNo, rs.getString("billNo"), rs.getString("materialCode"),
                        rs.getString("materialName"), rs.getString("model"), rs.getInt("planQuantity"),
                        DateUtil.sqlDateToLocalDate(rs.getDate("productDate")), rs.getString("resProcess1"),
                        rs.getDouble("resProcessPrice1"), rs.getInt("resProcessQty1"), rs.getString("resProcess2"),
                        rs.getDouble("resProcessPrice2"), rs.getInt("resProcessQty2"), rs.getString("resProcess3"),
                        rs.getDouble("resProcessPrice3"), rs.getInt("resProcessQty3"), rs.getString("process1"),
                        rs.getDouble("processPrice1"), rs.getInt("processQty1"), rs.getString("process2"),
                        rs.getDouble("processPrice2"), rs.getInt("processQty2"), rs.getString("process3"),
                        rs.getDouble("processPrice3"), rs.getInt("processQty3"), rs.getString("process4"),
                        rs.getDouble("processPrice4"), rs.getInt("processQty4"), rs.getString("process5"),
                        rs.getDouble("processPrice5"), rs.getInt("processQty5"), rs.getString("process6"),
                        rs.getDouble("processPrice6"), rs.getInt("processQty6"), rs.getString("createUser"),
                        rs.getTimestamp("createTime"), rs.getString("modifyUser"), rs.getTimestamp("modifyTime"),
                        rs.getInt("isPiecework"), rs.getInt("isDelete"), rs.getInt("sequence"));
            }
        } catch (Exception e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBUtil.release(conn, stmt, rs);
        }

        return daily;
    }

    public static void main(String[] args) {
//        int maxSeq = getDailyDetailSeq("制造-VP车间180813428");
//        System.out.println(maxSeq);

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
//        System.out.println(sdf.format(new Date()));
//        String str = "201810131204001";
//        System.out.println(str.substring(12));
//        System.out.println(getDailyNo());

        System.out.println(getDailyByNoAll("201810311121001").getBillNo());
    }
}
