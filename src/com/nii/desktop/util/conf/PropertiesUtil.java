package com.nii.desktop.util.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nii.desktop.util.ui.ResourceLoader;

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

        try {
            FileInputStream fin = new FileInputStream(
                    ResourceLoader.getPropertiesResource("config.properties").getFile());
            InputStreamReader reader = new InputStreamReader(fin, "GBK");
            defaultProperties.load(reader);

        } catch (IOException e) {
            LOGGER.error("��ȡĬ�������ļ�ʧ�ܣ�", e);
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
     * ��ȡָ��·����properties
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
        } catch (IOException e) {
            LOGGER.error("��ȡ�����ļ�ʧ�ܣ�", e);
        } finally {
            try {
                reader.close();
                fin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return prop;
    }

    /**
     * ���ļ�����д������
     * 
     * @param key   key
     * @param value value
     * @return true �ɹ� | false ʧ��
     */
    public static boolean writeKeyValue(String key, String value) {
        Properties prop = new Properties();
        prop.setProperty(key, value);

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(ResourceLoader.getPropertiesResource("host.properties").getFile());
            prop.store(outputStream, "save");
            prop.list(System.out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Properties prop = getDefaultProperties();
        System.out.println(prop);
        boolean res = writeKeyValue("host", "127.0.0.1");
        System.out.println(res);
//        System.out.println(ResourceLoader.getPropertiesResource("config.properties").getFile());

    }
}
