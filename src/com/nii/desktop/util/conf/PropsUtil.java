package com.nii.desktop.util.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nii.desktop.util.ui.ResourceLoader;

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
    /**
     * 日志
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /* config.properties路径 */
    private final static String CONFIG_FILE_PATH = ResourceLoader.getPropsResource("config.properties").getPath();

    /* message.properties路径 */
    private final static String MESSAGE_FILE_PATH = ResourceLoader.getPropsResource("message.properties").getPath();

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
            LOGGER.error("获取配置文件失败！", e);
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
            LOGGER.error("获取配置文件失败！", e);

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

        String dbUrl = "jdbc:sqlserver://" + value + ":1433;DatabaseName=AIS";

        updateKeyValue("db.url", dbUrl); // 更新db.url
    }

    public static void main(String[] args) {
        System.out.println("host:" + getConfigValue("host"));
        System.out.println(getConfigValue("db.url"));
        updateHostAndDBUrl("host", "localhost");
        System.out.println(getConfigValue("db.url"));

    }
}
