/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nii.desktop.util.conf;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Forever
 */
public class PropertiesUtil {
   
   private static final String FILEPATH = "/resources/conf/user.properties";

   public static String getProperty(String key) {
      Properties prop = new Properties();
      String value = null;
   
      try {
         FileInputStream in = new FileInputStream(new File(FILEPATH));
         prop.load(in);
         value = prop.getProperty(key);
         in.close();
      } catch (Exception ex) {
         Logger.getLogger(PropertiesUtil.class.getName()).log(Level.SEVERE, null, ex);
      } 
      
      return value;
   }   
}
