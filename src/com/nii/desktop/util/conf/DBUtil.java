/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nii.desktop.util.conf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Forever
 */
public class DBUtil {
	
   private static final Properties properties = PropertiesUtil.getDefaultProperties();
   
   private static final String CALSSDRIVER = properties.getProperty("db.driver.classname");
   
   private static final String URL = properties.getProperty("db.url");
   
   private static final String USERNAME = properties.getProperty("db.username");
   
   private static final String PASSWORD = properties.getProperty("db.password");
   
   private static Connection conn = null;
   
   static {
      try {
         Class.forName(CALSSDRIVER);
         conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);   
      } catch (Exception ex) {
         Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
      }
      
   }
   
   public static Connection getConnection() {
      return conn;
   }
   
   public static void release(Object obj) {
       if(obj instanceof Connection) {
           try {
            ((Connection) obj).close();
        } catch (SQLException e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        }
       }
       
       if(obj instanceof PreparedStatement) {
           try {
            ((PreparedStatement) obj).close();
        } catch (SQLException e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        }
       }
       
       if(obj instanceof ResultSet) {
           try {
            ((ResultSet) obj).close();
        } catch (SQLException e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, e);
        }
       }
   }
   
   public static void release(Object obj1, Object obj2) {
       release(obj1);
       release(obj2);
   }
   
   public static void release(Object obj1, Object obj2, Object obj3) {
       release(obj1);
       release(obj2);
       release(obj3);
   }
   
   public static void main(String[] args) {
       System.out.println(getConnection());
       String str = "0000001";
       System.out.println(Integer.parseInt(str));
   }
   
}
