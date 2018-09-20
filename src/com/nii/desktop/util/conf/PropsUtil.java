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
     * ��־
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /* config.properties·�� */
    private final static String CONFIG_FILE_PATH = ResourceLoader.getPropsResource("config.properties").getPath();

    /* message.properties·�� */
    private final static String MESSAGE_FILE_PATH = ResourceLoader.getPropsResource("message.properties").getPath();

    /**
     * ˽�й��캯��
     */
    private PropsUtil() {

    }

    /**
     * ��ȡconfig.properties
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
            LOGGER.error("��ȡ�����ļ�ʧ�ܣ�", e);
        }
        return configProperties;
    }

    /**
     * ��ȡconfig.properties�е�����ֵ
     */
    public static String getConfigValue(String key) {
        Properties configProperties = getConfigProperties();
        return configProperties.getProperty(key);
    }

    /**
     * ��ȡmessage.properties�е���Ϣ
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
            LOGGER.error("��ȡ�����ļ�ʧ�ܣ�", e);

        }
        return messageProps.getProperty(key);
    }

    /**
     * ����config.properties�е�����
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
     * ����config.properties��db.url
     */
    public static void updateHostAndDBUrl(String key, String value) {
        updateKeyValue(key, value); // ����host

        String dbUrl = "jdbc:sqlserver://" + value + ":1433;DatabaseName=AIS";

        updateKeyValue("db.url", dbUrl); // ����db.url
    }

    public static void main(String[] args) {
        System.out.println("host:" + getConfigValue("host"));
        System.out.println(getConfigValue("db.url"));
        updateHostAndDBUrl("host", "localhost");
        System.out.println(getConfigValue("db.url"));

    }
}
