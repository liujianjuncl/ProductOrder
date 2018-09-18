package com.nii.desktop.util.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nii.desktop.util.ui.ResourceLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import javax.tools.Tool;

/**
 * Created by wzj on 2017/1/1.
 */
public final class PropertiesUtil {
    /**
     * 日志
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * 私有构造函数
     */
    private PropertiesUtil() {

    }

    /**
     * 获取默认的properties
     * 
     * @return Properties
     */
    public static Properties getDefaultProperties() {
        Properties defaultProperties = new Properties();
        FileInputStream fin = null;

        try {
            fin = new FileInputStream(ResourceLoader.getPropertiesResource("config.properties").getFile());
            InputStreamReader reader = new InputStreamReader(fin, "GBK");
            defaultProperties.load(reader);
            
            fin.close();
        } catch (IOException e) {
            LOGGER.error("获取默认配置文件失败！", e);
        }

        return defaultProperties;
    }

    /**
     * get String vale
     * 
     * @param key key
     * @return 结果
     */
    public static String getStringValue(String key) {
        Properties defaultProperties = getDefaultProperties();
        return defaultProperties.getProperty(key);
    }

    /**
     * 获取指定路径的properties
     * 
     * @return Properties
     */
    public static Properties getProperties(String filePath) {
        Properties prop = new Properties();
        FileInputStream fin = null;
        InputStreamReader reader = null;
        try {
            fin = new FileInputStream(ResourceLoader.getPropertiesResource(filePath).getFile());
            reader = new InputStreamReader(fin, "GBK");
            prop.load(reader);
            
            reader.close();
            fin.close();
        } catch (IOException e) {
            LOGGER.error("获取配置文件失败！", e);
        }

        return prop;
    }

    /**
     * 往文件里面写入内容
     * 
     * @param key   key
     * @param value value
     * @return true 成功 | false 失败
     */
    public static boolean writeHostInfo(String key, String value) {
        Properties prop = new Properties();
        FileOutputStream fos = null;

        try {
            System.out.println(ResourceLoader.getPropertiesResource("host.properties").getFile());
            fos = new FileOutputStream(ResourceLoader.getPropertiesResource("host.properties").getPath());
            prop.setProperty(key, value);
            prop.store(fos, "服务器配置");
            
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } 

        return true;
    }

    public static void main(String[] args) {
        Properties prop = getDefaultProperties();
//        System.out.println(prop);
        boolean res = writeHostInfo("host", "127.0.0.1");
        System.out.println(res);
//        System.out.println(ResourceLoader.getPropertiesResource("config.properties").getFile());

    }
}
