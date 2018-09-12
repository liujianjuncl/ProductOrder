/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nii.desktop.util.conf;

import java.sql.Connection;
import java.sql.DriverManager;
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
   
   public static void main(String[] args) {
      System.out.println(CALSSDRIVER);
      System.out.println(URL);
      System.out.println(USERNAME);
      System.out.println(PASSWORD);
      System.out.println(conn);
   }
   
}
