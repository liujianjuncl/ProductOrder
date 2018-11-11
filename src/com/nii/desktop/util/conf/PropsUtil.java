package com.nii.desktop.util.conf;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

/**
 * Created by wzj on 2017/1/1.
 */
public final class PropsUtil {

    private final static String SYS_PATH = System.getProperty("user.dir");

    /* config.properties路径 */
    private static String CONFIG_FILE_PATH = SYS_PATH + "\\config.properties";

    /* message.properties路径 */
    private static String MESSAGE_FILE_PATH = SYS_PATH + "\\message.properties";

    /**
     * 私有构造函数
     */
    private PropsUtil() {

    }

    /**
     * 获取config.properties
     */
    public static Properties getConfigProperties() {
        Properties configProperties = new Properties();
        FileInputStream fin = null;
        
        try {
            fin = new FileInputStream(CONFIG_FILE_PATH);
            InputStreamReader reader = new InputStreamReader(fin, "GBK");
            configProperties.load(reader);

            fin.close();
        } catch (IOException e) {
            Logger.getLogger(PropsUtil.class.getName()).log(Level.SEVERE, null, e);
        }
        return configProperties;
    }

    /**
     * 获取config.properties中的属性值
     */
    public static String getConfigValue(String key) {
        Properties configProperties = getConfigProperties();
        return configProperties.getProperty(key);
    }

    /**
     * 获取message.properties中的信息
     */
    public static String getMessage(String key) {
        Properties messageProps = new Properties();
        FileInputStream fin = null;

        try {
            fin = new FileInputStream(MESSAGE_FILE_PATH);
            InputStreamReader reader = new InputStreamReader(fin, "GBK");
            messageProps.load(reader);

            fin.close();
        } catch (IOException e) {
            Logger.getLogger(PropsUtil.class.getName()).log(Level.SEVERE, null, e);

        }
        return messageProps.getProperty(key);
    }

    /**
     * 更新config.properties中的内容
     */
    public static boolean updateKeyValue(String key, String value) {
        Properties prop = getConfigProperties();
        FileOutputStream fos = null;
        OutputStreamWriter writer = null;

        try {
            fos = new FileOutputStream(CONFIG_FILE_PATH);
            writer = new OutputStreamWriter(fos, "GBK");
            prop.setProperty(key, value);
            prop.store(writer, key);

            writer.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 更新config.properties中db.url
     */
    public static void updateHostAndDBUrl(String key, String value) {
        updateKeyValue(key, value); // 更新host
    }

    public static void main(String[] args) {
        System.out.println(SYS_PATH);

        System.out.println(getConfigProperties());

    }
}
