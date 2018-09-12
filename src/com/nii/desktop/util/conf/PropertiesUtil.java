package com.nii.desktop.util.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.Properties;

import javax.tools.Tool;

/**
 * Created by wzj on 2017/1/1.
 */
public final class PropertiesUtil {
    /**
     * ��־
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * ˽�й��캯��
     */
    private PropertiesUtil() {

    }

    /**
     * ��ȡĬ�ϵ�properties
     * 
     * @return Properties
     */
    public static Properties getDefaultProperties() {
        Properties defaultProperties = new Properties();

        InputStream inStream = PropertiesUtil.class.getResourceAsStream("/resources/conf/config.properties");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "GBK"));
            defaultProperties.load(reader);

        } catch (IOException e) {
            LOGGER.error("��ȡĬ�������ļ�ʧ�ܣ�", e);
        }

        return defaultProperties;
    }

    /**
     * ��ȡĬ�ϵ�properties
     * 
     * @return Properties
     */
    public static Properties getProperties(String filePath) {
        Properties defaultProperties = new Properties();

        InputStream inStream = PropertiesUtil.class.getResourceAsStream(filePath);

        try {
            defaultProperties.load(inStream);
        } catch (IOException e) {
            LOGGER.error("��ȡ�����ļ�ʧ�ܣ�", e);
        }

        return defaultProperties;
    }

    /**
     * get String vale
     * 
     * @param key key
     * @return ���
     */
    public static String getStringValue(String key) {
        Properties defaultProperties = getDefaultProperties();
        return defaultProperties.getProperty(key);
    }

    /**
     * ���ļ�����д������
     * 
     * @param key   key
     * @param value value
     * @return true �ɹ� | false ʧ��
     */
    public static boolean writeKeyValue(String key, String value) {
        Properties defaultProperties = getDefaultProperties();
        defaultProperties.setProperty(key, value);

        try {
            FileOutputStream outputStream = new FileOutputStream("d:/test.properties");
            defaultProperties.store(outputStream, "save");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
